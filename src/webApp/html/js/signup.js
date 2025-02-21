document.getElementById('signupForm').addEventListener('submit', function (event) {
    event.preventDefault();

    // Gather form data
    const formData = new FormData(event.target);
    const userRegisterDTO = {
        firstName: formData.get('firstName'),
        lastName: formData.get('lastName'),
        patronymic: formData.get('patronymic'),
        username: formData.get('username'),
        password: formData.get('password')
    };

    // Make the API request
    fetch('http://localhost:8080/v1/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userRegisterDTO)
    })
        .then(response => {
            if (response.ok) {
                alert('Registration successful!');
                window.location.href = 'login.html'; // Redirect to login page
            } else {
                alert('Registration failed. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again.');
        });
});
