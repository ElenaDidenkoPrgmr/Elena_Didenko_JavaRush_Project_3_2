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
    <div class="row">
        <div class="col-sm-12">
            <p>Вам нужно написать браузерную игру. </p>

        </div>

    </div>

    <div class="container">
        <div class="row">
            <div style="height: 150px;" class="col-md-4 offset-md-4 text-center">
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
