window.addEventListener('DOMContentLoaded', () => {
    const loginButton = document.getElementById('logout-btn');

    const token = localStorage.getItem('token') || null;

    if (!token) {
        loginButton.style.display = "none";
    }
});