<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Регистрация — Дневник расходов</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/auth.css">
</head>
<body>

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <main class="auth">
        <div class="auth-card">
            <h1 class="auth-title">Регистрация</h1>
            <p class="auth-lead">Логин, имя и пароль. Больше ничего не нужно.</p>

            <%-- Ошибка от RegisterServlet --%>
            <c:if test="${not empty error}">
                <p class="msg msg-error" role="alert"><c:out value="${error}"/></p>
            </c:if>

            <form class="auth-form" method="post" action="${ctx}/register">
                <div class="field">
                    <label for="login">Логин</label>
                    <input id="login" name="login" type="text" required minlength="3"
                           autocomplete="username" autofocus
                           value="<c:out value='${param.login}'/>">
                    <span class="hint">Не короче 3 символов</span>
                </div>

                <div class="field">
                    <label for="name">Имя</label>
                    <input id="name" name="name" type="text" required
                           autocomplete="name"
                           value="<c:out value='${param.name}'/>">
                </div>

                <div class="field">
                    <label for="password">Пароль</label>
                    <input id="password" name="password" type="password" required minlength="6"
                           autocomplete="new-password">
                    <span class="hint">Не короче 6 символов</span>
                </div>

                <div class="field">
                    <label for="confirmPassword">Повторите пароль</label>
                    <input id="confirmPassword" name="confirmPassword" type="password" required minlength="6"
                           autocomplete="new-password">
                </div>

                <button class="btn btn-primary auth-submit" type="submit">Создать аккаунт</button>
            </form>

            <p class="auth-alt">Уже есть аккаунт? <a href="${ctx}/login">Войти</a></p>
        </div>
    </main>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
