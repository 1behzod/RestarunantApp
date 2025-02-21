// Check if user is logged in (authToken should be available in localStorage)
if (!localStorage.getItem('authToken')) {
    window.location.href = 'login.html'; // Redirect to login if not authenticated
}

// Get TokenDTO details from localStorage
const authToken = localStorage.getItem('authToken');
const refreshToken = localStorage.getItem('refreshToken');
const tokenType = localStorage.getItem('tokenType');
const tokenExpire = localStorage.getItem('tokenExpire');

// Display user info (e.g., token details and username)
document.getElementById('usernameDisplay').innerText = 'Username: ' + localStorage.getItem('username');  // Display stored username
document.getElementById('accessTokenDisplay').innerText = 'Access Token: ' + authToken;
document.getElementById('refreshTokenDisplay').innerText = 'Refresh Token: ' + refreshToken;
document.getElementById('tokenTypeDisplay').innerText = 'Token Type: ' + tokenType;
document.getElementById('tokenExpireDisplay').innerText = 'Token Expiry: ' + new Date(tokenExpire).toLocaleString();

// Logout functionality
document.getElementById('logoutButton').addEventListener('click', function () {
    localStorage.removeItem('authToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('tokenType');
    localStorage.removeItem('tokenExpire');
    localStorage.removeItem('username');
    window.location.href = 'login.html';  // Redirect to login page
});
