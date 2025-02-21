// Handle login form submission
document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const rememberMe = document.getElementById('rememberMe').checked;

    const userLoginDTO = {
        username: username,
        password: password,
        rememberMe: rememberMe
    };

    fetch('http://localhost:8080/v1/users/login', {
        method: 'POST', // Use POST for login
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userLoginDTO)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Invalid username or password');
            }
            return response.json();
        })
        .then(data => {
            if (data.access_token) {
                // Store TokenDTO in localStorage
                localStorage.setItem('authToken', data.access_token);
                localStorage.setItem('refreshToken', data.refresh_token);
                localStorage.setItem('tokenType', data.token_type);
                localStorage.setItem('tokenExpire', data.expire);

                // Store additional user information if needed (like username)
                localStorage.setItem('username', username);  // Store username for displaying later

                // Redirect to restaurant page
                window.location.href = 'restaurant.html';
            } else {
                alert('Invalid login credentials');
            }
        })
        .catch(error => {
            console.error('Error during login:', error);
            alert(error.message || 'An error occurred. Please try again later.');
        });
});
