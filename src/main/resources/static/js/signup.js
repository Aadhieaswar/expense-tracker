import { createUserWithEmailAndPassword } from 'https://www.gstatic.com/firebasejs/11.1.0/firebase-auth.js'

const auth = window.firebaseAuth;

function validateSignupForm(form) {
    Array.from(form.elements).map((el) => {
        if (el.tagName === "INPUT" && !el.checkValidity()) 
            el.reportValidity();
    });

    const formData = new FormData(form);

    const email = formData.get('email');
    const password = formData.get('password');
    const confirmPassword = formData.get('confirm_password');

    if (!email || !password || !confirmPassword) {
        window.addAlert('Please fill out all the fields to complete registration!', 'danger');
        return [null, null];
    }

    if (password !== confirmPassword) {
        window.addAlert('Both password fields must match!', 'danger');
        return [null, null];
    }

    return [email, password];
}

async function signup() {
    const signupForm = document.getElementById('signup-form');

    const [email, password] = validateSignupForm(signupForm);

    if (!email || !password) return;

    try {
        const userCredential = await createUserWithEmailAndPassword(auth, email, password);
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
    document.querySelector("#signup-submission").addEventListener('click', () => signup());
});