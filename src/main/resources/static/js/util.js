const isTokenExpired = (token) => {
    if (!token) return true;

    try {
        const base64Payload = token.split('.')[1];
        const decoded = JSON.parse(atob(base64Payload));
        const expirationTime = decoded.exp * 1000;
        return Date.now() >= expirationTime;
    } catch (err) {
        console.error('Error parsing token!', err);
        return true;
    }
}

window.isTokenExpired = isTokenExpired;