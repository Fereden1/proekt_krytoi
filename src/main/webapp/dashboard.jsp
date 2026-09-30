<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Кабинет — Дневник расходов</title>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <link rel="stylesheet" href="${ctx}/css/auth.css">
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main class="cabinet">
    <div class="container">
        <div class="cabinet-head">
            <div>
                <h1 class="auth-title">Здравствуйте, <c:out value="${sessionScope.userName}"/>!</h1>
                <p class="auth-lead">Доход, расход и список операций.</p>
            </div>
            <a href="${ctx}/transactions/add" class="btn btn-primary">Добавить операцию</a>
        </div>

        <div class="ledger-totals cabinet-totals">
            <div>
                <div class="total-label">Доход</div>
                <div class="total-value income"><c:out value="${totalIncome}"/> ₽</div>
            </div>
            <div>
                <div class="total-label">Расход</div>
                <div class="total-value expense"><c:out value="${totalExpense}"/> ₽</div>
            </div>
            <div>
                <div class="total-label">Баланс</div>
                <div class="total-value"><c:out value="${balance}"/> ₽</div>
            </div>
        </div>

        <div class="seg filter-bar" role="group" aria-label="Фильтр">
            <a class="btn btn-sm ${empty filterType ? 'btn-primary' : 'btn-outline'}" href="${ctx}/index">Все</a>
            <a class="btn btn-sm ${filterType == 'income' ? 'btn-primary' : 'btn-outline'}" href="${ctx}/index?type=income">Доходы</a>
            <a class="btn btn-sm ${filterType == 'expense' ? 'btn-primary' : 'btn-outline'}" href="${ctx}/index?type=expense">Расходы</a>
        </div>

        <c:choose>
            <c:when test="${empty transactions}">
                <p class="auth-lead">Пока нет операций. Добавьте первую запись.</p>
            </c:when>
            <c:otherwise>
                <table class="ops-table">
                    <thead>
                    <tr>
                        <th>Дата</th>
                        <th>Тип</th>
                        <th>Категория</th>
                        <th>Сумма</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="t" items="${transactions}">
                        <tr>
                            <td><c:out value="${t.operationDate}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${t.type == 'income'}">Доход</c:when>
                                    <c:otherwise>Расход</c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${t.categoryName}"/></td>
                            <td class="${t.type}"><c:out value="${t.amount}"/> ₽</td>
                            <td>
                                <form method="post" action="${ctx}/transactions/delete" onsubmit="return confirm('Удалить операцию?');">
                                    <input type="hidden" name="id" value="${t.id}">
                                    <button class="btn btn-outline btn-sm" type="submit">Удалить</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
