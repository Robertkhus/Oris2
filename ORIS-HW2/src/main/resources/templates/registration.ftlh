<!DOCTYPE html>
<#import "spring.ftl" as spring/>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <!-- Подключаем Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Регистрация</h3>
                </div>
                <div class="card-body">
                    <@spring.bind "registrationForm"/>
                    <form action="registration" method="post" <#if spring.status.errors?exists>class="was-validated"</#if>>


                        <!-- Поле для имени пользователя -->
                        <div class="mb-3">
                            <label for="username" class="form-label">Имя пользователя</label>
                            <@spring.formInput "registrationForm.username" "class=\"form-control\" id=\"username\" name=\"username\" required" "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для электронной почты -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Электронная почта</label>
                            <@spring.formInput "registrationForm.email" "class=\"form-control\" id=\"email\" name=\"email\" required" "text"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>

                        <!-- Поле для пароля -->
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <@spring.formInput "registrationForm.password" "class=\"form-control\" id=\"password\" name=\"password\" required" "password"/>
                            <@spring.showErrors "<br>" "invalid-feedback"/>
                        </div>


                        <!-- Кнопка отправки формы -->
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Подключаем Bootstrap JS (необходим для некоторых компонентов) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>