<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Вход — Дневник расходов</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/auth.css">
</head>
<body>

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <main class="auth">
        <div class="auth-card">
            <h1 class="auth-title">Вход</h1>
            <p class="auth-lead">Войдите, чтобы увидеть свои доходы и расходы.</p>

            <%-- Сообщение после успешной регистрации (RegisterServlet кладёт "flash" в сессию) --%>
            <c:if test="${not empty sessionScope.flash}">
                <p class="msg msg-ok" role="status"><c:out value="${sessionScope.flash}"/></p>
                <c:remove var="flash" scope="session"/>
            </c:if>

            <%-- Ошибка от LoginServlet --%>
            <c:if test="${not empty error}">
                <p class="msg msg-error" role="alert"><c:out value="${error}"/></p>
            </c:if>

            <form class="auth-form" method="post" action="${ctx}/login">
                <div class="field">
                    <label for="login">Логин</label>
                    <input id="login" name="login" type="text" required minlength="3"
                           autocomplete="username" autofocus
                           value="<c:out value='${param.login}'/>">
                </div>

                <div class="field">
                    <label for="password">Пароль</label>
                    <input id="password" name="password" type="password" required
                           autocomplete="current-password">
                </div>

                <button class="btn btn-primary auth-submit" type="submit">Войти</button>
            </form>

            <p class="auth-alt">Нет аккаунта? <a href="${ctx}/register">Зарегистрируйтесь</a></p>
        </div>
    </main>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
