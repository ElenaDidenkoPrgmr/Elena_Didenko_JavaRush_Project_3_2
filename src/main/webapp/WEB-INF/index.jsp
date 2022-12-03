<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.javarush.eldidenko.servlet.WebConstants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Java game</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
</head>
<body>

<div class="container-fluid p-3 bg-primary text-white text-center">
    <h1>Добро пожаловать в Java-квест!</h1>
</div>

<div class="row container">
    <div class="col-md-3 pt-2 offset-1">
        <img src="${pageContext.request.contextPath}\img\index.png" class="rounded" alt="Quest">
    </div>
    <div class=" col-md-8  pt-4">
        <p>Java-игра. Состоит из заданий-квестов на знание Java. Цель игры: пройти все уровни. Следующий уровень
            разблокируется
            после успешного прохождения одного квеста текущего уровня. Желаю удачи!
        </p>
        <div class="row">
           <div class="col-md-6 text-center ">
               <br><br>
                <h5>Готовы начать?</h5>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="form-group p-2">
                        <label for="usr">Введите имя:</label>
                        <input type="text" class="form-control" id="usr" name=${WebConstants.USERNAME.toString()}>
                    </div>
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary btn-block">Начать квест</button>
                    </div>
                </form>
            </div>
            <div class="col-md-5 offset-1">
                <img src="${pageContext.request.contextPath}\img\index2.png" class="rounded" alt="Quest">
            </div>
        </div>
    </div>
</div>
</body>
</html>
