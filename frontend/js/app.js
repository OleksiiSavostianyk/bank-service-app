// Check user authentication on each page load
function checkAuthentication() {
    fetch('http://localhost:8080/check/authentication')  // Adjust this endpoint to match your backend route for auth checks
        .then(response => {
            if (response.status === 401) {
                // If the user is not authenticated, redirect them to the login page
                window.location.href = '/login.html';
            }
        })
        .catch(error => {
            console.error('Error checking authentication:', error);
        });
}

// Call checkAuthentication when the page loads
document.addEventListener('DOMContentLoaded', () => {
    checkAuthentication();
});

// Your existing logic
function transferFunds() {
    alert("Transfer Funds button clicked!");
}

function accountInfo() {
    window.location.href = "http://localhost:8080/payments/get/response";
}

function viewTransactions() {
    alert("View Transactions button clicked!");
}

function viewBalance() {
    alert("View Balance button clicked!");
}

function accounts() {
    alert("Accounts button clicked!");
}

function settings() {
    alert("Settings button clicked!");
}

function login() {
    window.location.href = "http://localhost:8080/payments/get/response";
}

function signUp() {
    window.location.href = 'register.html';
}
