<!DOCTYPE html>
<html lang="UTF-8">
<head>
<title>Javarush project</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>
<div class="p-4 mb-2 text-dark bg-light">
<h2 class="text-light text-bg-primary">Квест на знание языка Java</h2>
<br>
<a href="https://javarush-project-quest-2.herokuapp.com" class="link-primary">Деплой проекта на heroku</a>

<h2 class="text-primary">Краткое описание</h2>
<p> Веб-приложение Java. Это игра, которая состоит из заданий-квестов на знание Java. 
Перед каждым кветом реализован диалог с Хранителем комнаты. 
Цель игры: пройти все уровни. Следующий уровень разблокируется после успешного 
прохождения одного квеста текущего уровня.</p>
    <ul>
        <li>За прохождение уровня начисляются баллы</li>
        <li>Реализована статистика пользователя</li>
        <li> После прохождения игры есть возможность сделать рестарт</li>
    </ul>

<h2 class="text-primary">Сборка и запуск проекта</h2>
    <ul> 
        <li>mvn clean package</li>
        <li> java -jar target\embeddedTomcatSample-jar-with-dependencies.jar</li>
    </ul>

<h2 class="text-primary">Проект создан с использованием:</h2>
    <ul> 
        <li>Maven</li>
        <li> JUnit 4 и Mockito</li>
        <li> log4j</li>
        <li> embedded Tomcat</li>
        <li> Servlet</li>
        <li> HTML, Bootstrap,CSS, JSP, JSTL</li>
    </ul>
</div>

</body>
</html>