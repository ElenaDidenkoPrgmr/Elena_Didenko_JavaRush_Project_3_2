<!DOCTYPE html>
<html lang="UTF-8">
<head>
<title>Javarush project</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body class="mb-2 bg-light text-primary">
<div class="container">
<h2 class="text-light text-bg-primary">Квест на знание языка Java</h2>
<br>
<a href="https://javarush-project-quest-2.herokuapp.com" class="link-primary">Деплой проекта на heroku</a>

<h2 class="text-primary">Краткое описание</h2>
<container class = "md-5"> <p> Веб-приложение Java. Это игра, которая состоит из заданий-квестов на знание Java. 
Перед каждым кветом реализован диалог с Хранителем комнаты. 
Цель игры: пройти все уровни. Следующий уровень разблокируется после успешного 
прохождения одного квеста текущего уровня.</p></container>

* За прохождение уровня начисляются баллы
* Реализована статистика пользователя
* После прохождения игры есть возможность сделать рестарт
<h2 class="text-primary">Сборка и запуск проекта</h2>
* mvn clean package
* java -jar target\embeddedTomcatSample-jar-with-dependencies.jar
<h2 class="text-primary">Проект создан с использованием:</h2>
* Maven
* JUnit 4 и Mockito
* log4j
* embedded Tomcat
* Servlet
* HTML, Bootstrap,CSS, JSP, JSTL
</div>

</body>
</html>