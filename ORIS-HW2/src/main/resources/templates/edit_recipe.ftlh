<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактировать рецепт</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Редактировать рецепт</h2>

    <form action="/admin/recipes/edit/${recipe.id}" method="post" class="needs-validation" novalidate>
        <!-- Название рецепта -->
        <div class="mb-3">
            <label for="name" class="form-label">Название рецепта</label>
            <input type="text" class="form-control" id="name" name="name" value="${recipe.name!''}" required>
            <div class="invalid-feedback">
                Пожалуйста, введите название рецепта.
            </div>
        </div>

        <!-- Описание -->
        <div class="mb-3">
            <label for="description" class="form-label">Описание</label>
            <textarea class="form-control" id="description" name="description" rows="4" required>${recipe.description!''}</textarea>
            <div class="invalid-feedback">
                Пожалуйста, введите описание рецепта.
            </div>
        </div>

        <!-- Инструкции -->
        <div class="mb-3">
            <label for="instructions" class="form-label">Инструкции</label>
            <textarea class="form-control" id="instructions" name="instructions" rows="6" required>${recipe.instructions!''}</textarea>
            <div class="invalid-feedback">
                Пожалуйста, введите инструкции для рецепта.
            </div>
        </div>

        <!-- Ингредиенты -->
        <div class="mb-3">
            <div class="d-flex justify-content-between align-items-center">
                <label for="ingredients" class="form-label">Ингредиенты</label>
                <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addIngredientModal">
                    Добавить ингредиент
                </button>
            </div>
            <select class="form-select" id="ingredients" name="ingredientIds" multiple>
                <#if ingredients?has_content>
                    <#list ingredients as ingredient>
                        <option value="${ingredient.id}" <#if recipe.ingredients?seq_contains(ingredient)>selected</#if>>${ingredient.name}</option>
                    </#list>
                <#else>
                    <option disabled>Нет доступных ингредиентов</option>
                </#if>
            </select>
        </div>

        <!-- Категории -->
        <div class="mb-3">
            <label for="categories" class="form-label">Категории</label>
            <select class="form-select" id="categories" name="categoryIds" multiple>
                <#if categories?has_content>
                    <#list categories as category>
                        <option value="${category.id}" <#if recipe.categories?seq_contains(category)>selected</#if>>${category.name}</option>
                    </#list>
                <#else>
                    <option disabled>Нет доступных категорий</option>
                </#if>
            </select>
        </div>

        <!-- Кнопки -->
        <div class="text-end">
            <a href="/admin/recipes" class="btn btn-secondary">Отмена</a>
            <button type="submit" class="btn btn-success">Сохранить изменения</button>
        </div>
    </form>

    <!-- Модальное окно для добавления ингредиента -->
    <div class="modal fade" id="addIngredientModal" tabindex="-1" aria-labelledby="addIngredientModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addIngredientModalLabel">Добавить новый ингредиент</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                </div>
                <div class="modal-body">
                    <form id="addIngredientForm">
                        <div class="mb-3">
                            <label for="ingredientName" class="form-label">Название ингредиента</label>
                            <input type="text" class="form-control" id="ingredientName" name="name" required>
                            <div class="invalid-feedback">
                                Пожалуйста, введите название ингредиента.
                            </div>
                        </div>
                        <div id="ingredientError" class="text-danger" style="display: none;"></div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary" id="saveIngredientBtn">Сохранить</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Валидация формы на стороне клиента
    (function () {
        'use strict';
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    })();

    // Обработка добавления нового ингредиента
    document.getElementById('saveIngredientBtn').addEventListener('click', async () => {
        const form = document.getElementById('addIngredientForm');
        const ingredientNameInput = document.getElementById('ingredientName');
        const errorDiv = document.getElementById('ingredientError');

        // Проверка валидности поля
        if (!form.checkValidity()) {
            form.classList.add('was-validated');
            return;
        }

        const ingredientName = ingredientNameInput.value.trim();
        errorDiv.style.display = 'none';

        try {
            // Отправка AJAX-запроса для создания ингредиента
            const response = await fetch('/ingredients/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name: ingredientName }),
            });

            if (!response.ok) {
                const errorData = await response.json().catch(() => ({}));
                const errorMessage = errorData.message || `Ошибка ${response.status}: Не удалось добавить ингредиент`;
                throw new Error(errorMessage);
            }

            const newIngredient = await response.json();

            // Добавление нового ингредиента в список <select>
            const select = document.getElementById('ingredients');
            const option = new Option(newIngredient.name, newIngredient.id);
            select.appendChild(option);
            select.value = newIngredient.id; // Выбираем новый ингредиент

            // Закрытие модального окна
            const modal = bootstrap.Modal.getInstance(document.getElementById('addIngredientModal'));
            modal.hide();

            // Сброс формы
            form.reset();
            form.classList.remove('was-validated');
        } catch (error) {
            errorDiv.textContent = error.message || 'Ошибка при добавлении ингредиента';
            errorDiv.style.display = 'block';
        }
    });
</script>
</body>
</html>