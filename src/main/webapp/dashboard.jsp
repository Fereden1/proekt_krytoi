<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%-- Только для вошедших пользователей --%>
<c:if test="${empty sessionScope.userId}">
    <c:redirect url="/login"/>
</c:if>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Кабинет — Дневник расходов</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/auth.css">
</head>
<body>

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <main class="auth">
        <div class="auth-card">
            <h1 class="auth-title">Здравствуйте, <c:out value="${sessionScope.userName}"/>!</h1>
            <p class="auth-lead">
                Здесь появятся ваши операции и статистика за неделю, месяц и год.
                Этот раздел ещё в разработке.
            </p>
            <a href="${ctx}/logout" class="btn btn-outline auth-submit">Выйти</a>
        </div>
    </main>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
