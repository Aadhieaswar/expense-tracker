function validateExpenseForm(form) {
    let success = true;
    Array.from(form.elements).map((el) => {
        if (el.tagName === "INPUT" && !el.checkValidity()) {
            el.reportValidity();
            success = false;
        }
    });

    if (!success) {
        window.addAlert(`Please fill out all the fields for the expense!`, 'danger')
        return null
    };

    const formData = new FormData(form);

    const category = formData.get('categoryId');
    if (category === "0") {
        window.addAlert('Please select a category for the expense!', 'warning');
        return null;
    }

    return {
        item: formData.get('item'),
        price: formData.get('price'),
        description: formData.get('description'),
        categoryId: category,
    };
}

window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('add-expense-btn').addEventListener('click', async () => {
        const token = localStorage.getItem('token') || null;

        if (!token) {
            window.location.href = '/';
        }

        let postData = validateExpenseForm(document.getElementById('expense-form'));

        if (!postData) return;

        postData = {
            ...postData,
            date: (new Date()).toISOString()
        }

        fetch('/api/expenses', {
            method: 'POST',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        })
        .then((response) => {
            alert(response);
            window.addAlert(`Successfully added expense!`, 'success')
            window.location.href = '/';
        })
        .catch(err => window.addAlert(err));
    });
});