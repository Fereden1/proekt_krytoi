(function () {
    const data = {
        week: {
            label: "Эта неделя",
            income: 18500,
            expense: 9200,
            cats: [
                ["Продукты", 3400],
                ["Транспорт", 1800],
                ["Жильё", 2500],
                ["Развлечения", 900],
                ["Прочее", 600]
            ]
        },
        month: {
            label: "Октябрь",
            income: 82000,
            expense: 54100,
            cats: [
                ["Жильё", 22000],
                ["Продукты", 14500],
                ["Транспорт", 6800],
                ["Здоровье", 4300],
                ["Развлечения", 6500]
            ]
        },
        year: {
            label: "2026",
            income: 940000,
            expense: 612000,
            cats: [
                ["Жильё", 264000],
                ["Продукты", 168000],
                ["Транспорт", 72000],
                ["Одежда", 41000],
                ["Прочее", 67000]
            ]
        }
    };

    const money = (n) => new Intl.NumberFormat("ru-RU").format(n) + " ₽";

    function render(period) {
        const d = data[period];
        const label = document.getElementById("ledgerPeriodLabel");
        const income = document.getElementById("totalIncome");
        const expense = document.getElementById("totalExpense");
        const balance = document.getElementById("totalBalance");
        const items = document.querySelectorAll("#catList .cat-item");
        if (!d || !income) return;

        label.textContent = d.label;
        income.textContent = money(d.income);
        expense.textContent = money(d.expense);
        balance.textContent = money(d.income - d.expense);

        const max = Math.max(...d.cats.map((c) => c[1]));
        items.forEach((li, i) => {
            const row = d.cats[i];
            if (!row) return;
            li.querySelector(".cat-name").textContent = row[0];
            li.querySelector(".cat-sum").textContent = money(row[1]);
            li.querySelector(".fill").style.width = (row[1] / max * 100) + "%";
        });
    }

    document.querySelectorAll(".seg [data-period]").forEach((btn) => {
        btn.addEventListener("click", () => {
            document.querySelectorAll(".seg [data-period]").forEach((b) => b.setAttribute("aria-pressed", "false"));
            btn.setAttribute("aria-pressed", "true");
            render(btn.dataset.period);
        });
    });

    render("month");
})();
