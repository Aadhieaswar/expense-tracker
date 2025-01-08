import { initializeApp } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-app.js";
import { getAuth } from 'https://www.gstatic.com/firebasejs/11.1.0/firebase-auth.js'

// firebase config
const firebaseConfig = {
    apiKey: "AIzaSyBTXf3fcy8TVnvQ5cCQ5U5fRTa9JPmcL-8",
    authDomain: "spring-expense-tracker.firebaseapp.com",
    projectId: "spring-expense-tracker",
    storageBucket: "spring-expense-tracker.firebasestorage.app",
    messagingSenderId: "801626645080",
    appId: "1:801626645080:web:0b6d85832d2dbcc38ba02d",
    measurementId: "G-K9P0PBHDXS"
};
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

window.firebaseApp = app;
window.firebaseAuth = auth;

const token = localStorage.getItem('token') || null;

if (token && window.isTokenExpired(token)) {
    localStorage.removeItem('token');
    window.location.href = "/";
}
