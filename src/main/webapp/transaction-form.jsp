<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Новая операция — Дневник расходов</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/auth.css">
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="auth">
    <div class="auth-card">
        <h1 class="auth-title">Новая операция</h1>
        <p class="auth-lead">Укажите тип, сумму, категорию и дату.</p>

        <c:if test="${not empty error}">
            <p class="msg msg-error" role="alert"><c:out value="${error}"/></p>
        </c:if>

        <form class="auth-form" method="post" action="${ctx}/transactions/add">
            <div class="field">
                <label for="type">Тип</label>
                <select id="type" name="type" required>
                    <option value="expense" ${type == 'expense' ? 'selected' : ''}>Расход</option>
                    <option value="income" ${type == 'income' ? 'selected' : ''}>Доход</option>
                </select>
            </div>

            <div class="field">
                <label for="amount">Сумма</label>
                <input id="amount" name="amount" type="number" step="0.01" min="0.01" required
                       value="<c:out value='${amount}'/>">
            </div>

            <div class="field">
                <label for="categoryId">Категория</label>
                <select id="categoryId" name="categoryId">
                    <option value="">Без категории</option>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.id}" ${categoryId == cat.id.toString() ? 'selected' : ''}>
                            <c:out value="${cat.name}"/> (<c:out value="${cat.type}"/>)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="field">
                <label for="operationDate">Дата</label>
                <input id="operationDate" name="operationDate" type="date" required
                       value="<c:out value='${operationDate}'/>">
            </div>

            <button class="btn btn-primary auth-submit" type="submit">Сохранить</button>
        </form>

        <p class="auth-alt"><a href="${ctx}/index">Назад в кабинет</a></p>
    </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
