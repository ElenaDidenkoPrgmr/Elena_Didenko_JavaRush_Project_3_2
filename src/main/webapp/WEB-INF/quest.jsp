<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Java game</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
    <style>.disableDiv {
        pointer-events: none;
        opacity: 0.4;
    }</style>

    <script type = "text/javascript">
        function jump(h){
            var url = location.href;               //Save down the URL without hash.
            location.href = "#"+h;                 //Go to the target element.
            history.replaceState(null,null,url);   //Don't like hashes. Changing it back.
        }
    </script>
</head>
<body onload="jump('anchor-quest')">

<div class="disableDiv">
    <%@include file="room.jsp" %>
</div>

<div  class=" h-100 d-flex align-items-center justify-content-center">
    <div class="card" id="anchor-quest">
        <div class="card-header">Вопрос от  ${npcInfo.getName()} по теме ${currentRoom.getName()}:</div>
        <div class="card-body"> <pre> ${questInfo.getText()}</pre></div>
        <div class="card-footer">Варианты ответа:<form action="${pageContext.request.contextPath}/quest" method="post">
            <c:forEach var="answer" items="${questInfo.getAnswers()}">
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="questAnswerId" id="answers"
                           value="${answer.getId()}">
                    <label class="form-check-label" for="answers">${answer.getText()}</label>
                </div>
            </c:forEach>
            <input class="btn btn-primary" type="submit" value="Ответить">
        </form>
        </div>
    </div>
</div>
</body>
