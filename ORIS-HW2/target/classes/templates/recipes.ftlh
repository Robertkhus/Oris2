<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список рецептов</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Список рецептов</h2>

    <!-- Кнопка "Добавить рецепт" -->
    <div class="text-end mb-3">
        <a href="/recipes/create" class="btn btn-success">Добавить рецепт</a>
    </div>

    <#if recipes?has_content>
        <div class="row row-cols-1 row-cols-md-2 g-4">
            <#list recipes as recipe>
                <div class="col">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">${recipe.name!''}</h5>
                            <p class="card-text"><strong>Описание:</strong> ${recipe.description!''}</p>
                            <p class="card-text"><strong>Инструкции:</strong> ${recipe.instructions!''}</p>

                            <#if recipe.ingredients?has_content>
                                <p><strong>Ингредиенты:</strong>
                                <ul>
                                    <#list recipe.ingredients as ingredient>
                                        <li>${ingredient.name!''}</li>
                                    </#list>
                                </ul>
                                </p>
                            </#if>

                            <#if recipe.categories?has_content>
                                <p><strong>Категории:</strong>
                                <ul>
                                    <#list recipe.categories as category>
                                        <li>${category.name!''}</li>
                                    </#list>
                                </ul>
                                </p>
                            </#if>

                            <p><strong>Автор:</strong> ${recipe.author.username!''}</p>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    <#else>
        <p class="text-center">Нет доступных рецептов.</p>
    </#if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
