<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Java game</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.6.1.min.js"></script>
    <style>.disableDiv {
        pointer-events: none;
        opacity: 0.4;
    }
    </style>
    <script>
        $(document).ready(function () {
            $("#myModalResultQuest").modal('show');
        });
    </script>

</head>

<body>
<div>
    <%@include file="room.jsp" %>
</div>

<div class="modal" id="myModalResultQuest">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Результаты квеста</h4>
                <button type="button" class="btn-close " data-bs-dismiss="modal"></button>
            </div>
            <c:choose>
                <c:when test="${resultQuest == true}">
                    <div class="modal-body">
                        <h2>Правильно!</h2>
                        <p>Ты заработал ${currentRoom.getLevel() * 10} баллов</p>
                        <p>Твой уровень: ${user.getLevel()}</p>
                        <p>Твои баллы: ${user.getPoint()}</p>
                    </div>
                </c:when>
                <c:when test="${resultQuest == false}">
                    <div class="modal-body">
                        <h2>Ошибка(</h2>
                        <p>Ты не заработал ${currentRoom.getLevel() * 10} баллов</p>
                        <p>Твой уровень: ${user.getLevel()}</p>
                        <p>Твои баллы: ${user.getPoint()}</p>
                    </div>
                </c:when>
            </c:choose>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</body>
