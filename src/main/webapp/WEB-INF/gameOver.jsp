<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<div class="container-fluid p-5 bg-primary text-white text-center">
    <h1>Игра закончена!</h1>
</div>

<div class="container mt-5">

    <div class="container">
        <div class="row">
            <div style="height: 150px;" class="col-md-4 offset-md-4 text-center">
                <h4>Индивидуальная статистика:</h4>
                <pre>User name: ${user.getName()}<br>IP address: ${pageContext.request.getRemoteAddr()}<br>Number of games played: ${user.getTotalGame()+1}<br>Current level: ${user.getLevel()}<br>Колличество баллов: ${user.getPoint()}</pre>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary btn-block">Играть заново!</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
