<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.javarush.eldidenko.servlet_quest.servlet.WebConstants" %>
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

    <script type="text/javascript">
        function jump(h) {
            var url = location.href;               //Save down the URL without hash.
            location.href = "#" + h;                 //Go to the target element.
            history.replaceState(null, null, url);   //Don't like hashes. Changing it back.
        }
    </script>
</head>

<body onload="jump('anchor-dialog')">
<div class="disableDiv">
    <%@include file="room.jsp" %>
</div>

<div class=" h-100 d-flex align-items-center justify-content-center">
    <div class="col-4 border p-4" id="anchor-dialog">
        <h6>Диалог с хранителем комнаты</h6>
        <div class="row">
            <div class="col-2 how-img">
                <img src="${pageContext.request.contextPath}${npcInfo.getAvatar()}" class="rounded-circle" alt="Avatar"
                     width="50"
                     height="50">
            </div>

            <div class="col-10"> <h5>- ${dialog.getText()} </h5></div>
        </div><br>

        <form action="${pageContext.request.contextPath}/dialog" method="post">
            <c:forEach var="answer" items="${dialog.getAnswers()}">
                <div class="form-check">
                    <c:choose>
                        <c:when test="${answer.getNextDialogId() != null}">
                            <input class="form-check-input" type="radio" name=${WebConstants.NEXT_QUESTION.toString()} id="answers"
                                   value="${answer.getNextDialogId()}">
                            <label class="form-check-label" for="answers">${answer.getText()}</label>
                            <c:set var="buttonlabel" scope="page" value="Ответить"/>

                        </c:when>

                        <c:when test="${answer.getQuestId() != null}">
                            <input class="form-check-input" type="radio" name=${WebConstants.QUEST_ID.toString()} id="answers"
                                   value="${answer.getQuestId()}">
                            <label class="form-check-label" for="answers">${answer.getText()}</label>
                            <c:set var="buttonlabel" scope="page" value="Начать квест"/>
                        </c:when>
                    </c:choose>
                </div>
            </c:forEach>

            <button type="submit" class="btn btn-primary btn-block">${buttonlabel}</button>
        </form>
    </div>
</div>
</body>
