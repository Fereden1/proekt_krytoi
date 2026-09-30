<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Дневник расходов — учёт доходов и расходов по категориям</title>
    <meta name="description" content="Записывайте доходы и расходы и смотрите статистику по категориям за неделю, месяц и год.">
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<main>

    <section class="hero">
        <div class="container hero-inner">
            <div class="hero-text">
                <h1 class="fade-in" style="--i:0">Видно, куда уходят ваши деньги</h1>
                <p class="hero-desc fade-in" style="--i:1">
                    Записывайте доходы и расходы по категориям и смотрите баланс за неделю, месяц и год.
                </p>
                <div class="hero-actions fade-in" style="--i:2">
                    <a href="${ctx}/register" class="btn btn-primary">Начать бесплатно</a>
                    <a href="${ctx}/login" class="btn btn-outline">Уже есть аккаунт</a>
                </div>
                <p class="hero-note fade-in" style="--i:3">Для регистрации нужны только логин, имя и пароль.</p>
            </div>

            <div class="ledger fade-in" style="--i:2" aria-labelledby="ledgerTitle">
                <div class="ledger-head">
                    <div>
                        <div class="ledger-title" id="ledgerTitle">Пример статистики</div>
                        <div class="ledger-sub" id="ledgerPeriodLabel">Октябрь</div>
                    </div>
                    <div class="seg" role="group" aria-label="Период">
                        <button type="button" data-period="week" aria-pressed="false">Неделя</button>
                        <button type="button" data-period="month" aria-pressed="true">Месяц</button>
                        <button type="button" data-period="year" aria-pressed="false">Год</button>
                    </div>
                </div>

                <div class="ledger-totals">
                    <div>
                        <div class="total-label">Доход</div>
                        <div class="total-value income" id="totalIncome">0 ₽</div>
                    </div>
                    <div>
                        <div class="total-label">Расход</div>
                        <div class="total-value expense" id="totalExpense">0 ₽</div>
                    </div>
                    <div>
                        <div class="total-label">Баланс</div>
                        <div class="total-value" id="totalBalance">0 ₽</div>
                    </div>
                </div>
                <div class="cat-heading">Расходы по категориям</div>
                <ul class="cat-list" id="catList" aria-live="polite">
                    <li class="cat-item"><div class="cat-row"><span class="cat-name"></span><span class="cat-sum"></span></div><div class="track"><div class="fill"></div></div></li>
                    <li class="cat-item"><div class="cat-row"><span class="cat-name"></span><span class="cat-sum"></span></div><div class="track"><div class="fill"></div></div></li>
                    <li class="cat-item"><div class="cat-row"><span class="cat-name"></span><span class="cat-sum"></span></div><div class="track"><div class="fill"></div></div></li>
                    <li class="cat-item"><div class="cat-row"><span class="cat-name"></span><span class="cat-sum"></span></div><div class="track"><div class="fill"></div></div></li>
                    <li class="cat-item"><div class="cat-row"><span class="cat-name"></span><span class="cat-sum"></span></div><div class="track"><div class="fill"></div></div></li>
                </ul>

                <p class="ledger-foot">Данные в примере вымышленные. Ваша статистика появится после первых записей.</p>
            </div>
        </div>
    </section>

    <section class="section" id="features">
        <div class="container split">
            <h2 class="section-title">Учёт без таблиц и лишних настроек</h2>
            <ul class="feature-list">
                <li>
                    <span class="icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 20h4L19 9l-4-4L4 16v4z"/></svg>
                    </span>
                    <div>
                        <h3>Быстрая запись</h3>
                        <p>Сумма, категория, дата и комментарий. Операцию можно изменить или удалить в любой момент.</p>
                    </div>
                </li>
                <li>
                    <span class="icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="4" y="4" width="7" height="7" rx="1.5"/><rect x="13" y="4" width="7" height="7" rx="1.5"/><rect x="4" y="13" width="7" height="7" rx="1.5"/><rect x="13" y="13" width="7" height="7" rx="1.5"/></svg>
                    </span>
                    <div>
                        <h3>Готовые категории</h3>
                        <p>Продукты, жильё, транспорт, зарплата и другие уже есть. Если чего-то не хватает, добавьте свою.</p>
                    </div>
                </li>
                <li>
                    <span class="icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M5 20V10M12 20V4M19 20v-7"/></svg>
                    </span>
                    <div>
                        <h3>Статистика за период</h3>
                        <p>Доход, расход и баланс за неделю, месяц или год, а также доля каждой категории в расходах.</p>
                    </div>
                </li>
            </ul>
        </div>
    </section>

    <section class="section section-alt" id="how">
        <div class="container">
            <h2 class="section-title">Три шага до первой статистики</h2>
            <ol class="steps">
                <li class="step">
                    <span class="step-num" aria-hidden="true">1</span>
                    <h3>Создайте аккаунт</h3>
                    <p>Нужны логин, имя и пароль. Данные видите только вы.</p>
                </li>
                <li class="step">
                    <span class="step-num" aria-hidden="true">2</span>
                    <h3>Записывайте операции</h3>
                    <p>Добавляйте доходы и расходы, когда они случаются.</p>
                </li>
                <li class="step">
                    <span class="step-num" aria-hidden="true">3</span>
                    <h3>Смотрите статистику</h3>
                    <p>Выберите период и узнайте, на что уходит больше всего.</p>
                </li>
            </ol>
        </div>
    </section>

    <section class="section" id="categories">
        <div class="container">
            <h2 class="section-title">Категории уже готовы</h2>
            <p class="section-lead">Базовый набор есть у каждого пользователя. Изменить его нельзя, зато можно добавить свои категории рядом.</p>
            <div class="cats">
                <div class="cat-col">
                    <h3>Расходы</h3>
                    <ul class="chips expense-chips">
                        <li>Продукты</li>
                        <li>Жильё</li>
                        <li>Транспорт</li>
                        <li>Здоровье</li>
                        <li>Развлечения</li>
                        <li>Одежда</li>
                        <li>Связь</li>
                        <li>Образование</li>
                        <li>Прочее</li>
                    </ul>
                </div>
                <div class="cat-col">
                    <h3>Доходы</h3>
                    <ul class="chips income-chips">
                        <li>Зарплата</li>
                        <li>Подработка</li>
                        <li>Подарки</li>
                        <li>Проценты</li>
                        <li>Прочее</li>
                    </ul>
                </div>
            </div>
        </div>
    </section>

    <section class="cta">
        <div class="container cta-inner">
            <div>
                <h2>Начните с первой записи</h2>
                <p>Регистрация занимает меньше минуты.</p>
            </div>
            <a href="${ctx}/register" class="btn btn-mark">Создать аккаунт</a>
        </div>
    </section>

</main>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

<script src="${ctx}/js/main.js"></script>
</body>
</html>
