import { signInWithEmailAndPassword } from 'https://www.gstatic.com/firebasejs/11.1.0/firebase-auth.js'

const auth = window.firebaseAuth;

function validateLoginForm(form) {
    Array.from(form.elements).map((el) => {
        if (el.tagName === "INPUT" && !el.checkValidity()) 
            el.reportValidity();
    });

    const formData = new FormData(form);

    const email = formData.get('email');
    const password = formData.get('password');

    if (!email || !password) {
        window.addAlert('Please fill out all the fields to complete login!', 'danger');
        return [null, null];
    }

    return [email, password];
}

async function login() {
    const loginForm = document.getElementById('login-form');

    const [email, password] = validateLoginForm(loginForm);

    if (!email || !password) return;

    try {
        const userCredential = await signInWithEmailAndPassword(auth, email, password);
        const token = await userCredential.user.getIdToken();
        // Send the token to the server
        fetch('/auth/authenticate', {
            method: 'POST',
            headers: {
                'Authorization': token,
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                localStorage.setItem("token", token);
                window.location.href = '/';
            } else {
                window.addAlert('Authentication failed', 'danger');
            }
        });
    } catch (error) {
        window.addAlert(error.message, 'danger');
    }
}

window.addEventListener('DOMContentLoaded', () => {
    document.querySelector("#login-submission").addEventListener('click', () => login());
});