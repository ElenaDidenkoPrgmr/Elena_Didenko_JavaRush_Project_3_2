<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Статистика</h4>
                <button type="button" class="btn-close " data-bs-dismiss="modal"></button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                <h4>Индивидуальная статистика:</h4>
                <pre>
                    User name: ${user.getName()}
                    IP address: ${pageContext.request.getRemoteAddr()}
                    Number of games played: ${user.getTotalGame()}
                    Current level: ${user.getLevel()}
                    Колличество баллов: ${user.getPoint()}
                </pre>
            </div>
            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
