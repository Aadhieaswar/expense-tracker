window.addEventListener('DOMContentLoaded', () => {
    const noAuthUser = document.getElementById('not-authenticated-user');
    const authUser = document.getElementById('authenticated-user');
    const expensesComponent = document.getElementById('user-expenses');
    const yearMonthSelect = document.getElementById('month-year-select');

    // tabs
    const expensesListTab = document.getElementById('expenses-list-btn');
    const expensesFormTab = document.getElementById('add-expense-form-btn');

    // tab components
    const expensesListTable = document.getElementById('expenses-table');
    const addExpenseForm = document.getElementById('add-expense-form-container');

    noAuthUser.style.display = "none";
    authUser.style.display = "none";

    let tabMap = {};

    // HIDE ALL TABS OTHER THAN THE EXPENSES LIST
    addExpenseForm.style.display = "none"; 

    const token = localStorage.getItem("token") || null;

    if (!token) {
        noAuthUser.style.display = "block";
        authUser.style.display = "none";
    } else {
        authUser.style.display = "block";
        noAuthUser.style.display = "none";

        // UPDATE ONLY THIS WHEN ADDING NEW TABS!
        tabMap = {
            'expenseList': {
                tab: expensesListTab,
                ele: expensesListTable,
                callback: () => {
                    // reload expenses data from backend
                    getUserExpenses(expensesComponent, token);
                }
            },
            'addExpenseForm': {
                tab: expensesFormTab,
                ele: addExpenseForm
            },
        }

        getUserExpenses(expensesComponent, token);

        (async function() {
            fetch('/api/categories', {
                method: 'GET',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json'
                }
            })
                .then((res) => res.json())
                .then((data) => {
                    const categorySelect = document.getElementById('category-select');
                    data.map((cat) => {
                        categorySelect.innerHTML += `<option value="${cat.id}">${cat.name}</option>`;
                    });
                })
                .catch((err) => window.addAlert(err));
        })();

        (async function() {
            fetch('/api/expenses/months-years', {
                method: 'GET',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json'
                }
            })
                .then((res) => {
                    const date = new Date();
                    yearMonthSelect.innerHTML += `<option value="${date.getMonth() + 1}-${date.getFullYear()}">${date.toLocaleString('default', { month: 'short' })}, ${date.getFullYear()} - CURRENT</option>`;

                    if (res.ok) {
                        return res.json();
                    }
                })
                .then((data) => {
                    data.map((row) => {
                        const monthString = new Intl.DateTimeFormat('en-US', { month: 'short' }).format(new Date(0, row[1] - 1));
                        yearMonthSelect.innerHTML += `<option value="${row[1]}-${row[0]}">${monthString}, ${row[0]}</option>`;
                    });
                })
                .catch((err) => window.addAlert(err));
        })();

        yearMonthSelect.addEventListener('change', () => {
            getUserExpenses(expensesComponent, token, yearMonthSelect.value.split('-')[0], yearMonthSelect.value.split('-')[1]);
        });

    }

    for (let key in tabMap) {
        tabMap[key].tab.addEventListener('click', () => {
            tabMap[key].ele.style.display = "block";
            tabMap[key].tab.classList.add("active");
            tabMap[key].callback?.();

            for (let j in tabMap) {
                if (j !== key) {
                    tabMap[j].tab.classList.remove("active");
                    tabMap[j].ele.style.display = "none";
                }
            }
        });
    }
});

async function getUserExpenses(component, token, month=null, year=null) {
    const expenseRowComponent = (idx, item, desc, category, expense, date) =>  
        (`<tr>
            <th scope="row">${idx + 1}</th>
            <td>${item}</td>
            <td>${desc}</td>
            <td>${category ? category.name : 'N/A'}</td>
            <td>\$${expense}</td>
            <td>${(new Date(date)).toISOString().split('T')[0]}</td>
        </tr>
        `);

    const date = new Date();
    fetch(`/api/expenses/${month ?? date.getMonth() + 1}/${year ?? date.getFullYear()}`, {
        method: 'GET',
        headers: {
            'Authorization': token,
            'Content-Type': 'application/json'
        }
    })
    .then((res) => res.json())
    .then((data) => {
        if (data) {
            component.innerHTML = "";
            data.map((exp, idx) => {
                component.innerHTML += expenseRowComponent(idx, exp.item, exp.description, exp.category, exp.price, exp.date);
            });
        }
    })
    .catch(err => window.addAlert(err));
};

async function getUserExpensesSummary(component, token, month=null, year=null) {
    const summaryRow = (count, total) =>  
        (`<tr>
            <td>${count}</td>
            <td>$${total}</td>
        </tr>
        `);

    const date = new Date();
    fetch(`/api/expenses/summary/${month ?? date.getMonth() + 1}/${year ?? date.getFullYear()}`, {
        method: 'GET',
        headers: {
            'Authorization': token,
            'Content-Type': 'application/json'
        }
    })
    .then((res) => res.json())
    .then((data) => {
        if (data) {
            component.innerHTML = summaryRow(data[0], data[1]);
        }
    })
    .catch(err => window.addAlert(err));
};
