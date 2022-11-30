<%@ page import="com.javarush.repository.RoomRepository" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.javarush.entity.Room" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/function-contains.tld" prefix="fn" %>
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
<div class="container-fluid p-3 bg-primary text-white text-center">
    <h1>Желаем удачи, ${user.getName()}!</h1>
</div>
<div class="container mt-5">
    <div class="row">
        <div class="col-sm-6 border">
            <h3>Карта игры</h3>
            <table class="table">
                <tbody>
                <c:forEach var="i" begin="<%=Room.START_LEVEL%>" end="<%=Room.FINISH_LEVEL%>">
                    <tr>
                        <td><p>Уровень ${i}</p>
                            <p>${i * 10} баллов</p></td>
                        <td>
                            <div class="col-md-12 text-center p-2">
                                <c:forEach var="room" items="${applicationScope['rooms'].getRooms()}">
                                    <c:if test="${room.getLevel() == i}">
                                        <c:choose>
                                            <c:when test="${  fn:contains(user.getEndedQuest(), room.getId() ) }">
                                                <form style="display: inline-block"
                                                      action="${pageContext.request.contextPath}/room" method="post">
                                                    <input type="hidden" name="nextRoom" value="${room.getId()}">
                                                    <button type="submit" class="btn btn-primary"
                                                            disabled>${room.getName()} </button>
                                                </form>
                                            </c:when>

                                            <c:when test="${room.getId() == user.getCurrentRoomId()}">
                                                <form style="display: inline-block"
                                                      action="${pageContext.request.contextPath}/room" method="post">

                                                    <input type="hidden" name="nextRoom" value="${room.getId()}">
                                                    <button type="submit"
                                                            class="btn btn-success">${room.getName()} </button>

                                                </form>
                                            </c:when>

                                            <c:when test="${room.getLevel() > user.getLevel()}">
                                                <form style="display: inline-block"
                                                      action="${pageContext.request.contextPath}/room" method="post">

                                                    <input type="hidden" name="nextRoom" value="${room.getId()}">
                                                    <button type="submit" class="btn btn-primary"
                                                            disabled>${room.getName()} </button>

                                                </form>
                                            </c:when>

                                            <c:otherwise>
                                                <form style="display: inline-block"
                                                      action="${pageContext.request.contextPath}/room" method="post">

                                                    <input type="hidden" name="nextRoom" value="${room.getId()}">
                                                    <button type="submit"
                                                            class="btn btn-primary">${room.getName()} </button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-6 border">
            <h3 style="text-align:center;">Познакомтесь с хранителями комнаты</h3>
            <div class="d-flex justify-content-center">
                <h3 style="background-color:green; color:white;"> ${currentRoom.getName()}:</h3>
            </div>
            <div class="d-flex justify-content-around">

                <c:forEach var="npc" items="${npcs}">
                    <div class="card" style="width:200px">
                        <img class="card-img-top" src="${pageContext.request.contextPath}${npc.getAvatar()}"
                             alt="Card image"
                             style="width:100%">
                        <div class="card-body">
                            <h4 class="card-title">${npc.getName()}</h4>
                            <p class="card-text">${npc.getDescription()}</p>

                            <form action="${pageContext.request.contextPath}/dialog" method="post">
                                <button type="submit" name="npcId" value="${npc.getId()}" class="btn btn-primary">Начать
                                    диалог
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<div class="container mt-3">
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
        Статистика
    </button>
</div>

<%@include file="statistics.jsp" %>
</body>
</html>
