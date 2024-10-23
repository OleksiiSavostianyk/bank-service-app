function checkAuthentication() {
    return fetch('http://localhost:8080/check/authentication')
        .then(response => {
            if (response.status === 401) {
                // Если пользователь не аутентифицирован, перенаправить на страницу входа
                window.location.href = 'http://localhost:8080/payments/get/response';
                return false; // Пользователь не аутентифицирован
            }
            return true; // Пользователь аутентифицирован
        })
        .catch(error => {
            console.error('Ошибка проверки аутентификации:', error);
            return false; // В случае ошибки считать пользователя не аутентифицированным
        });
}

// Вспомогательная функция для выполнения действия, если пользователь аутентифицирован
async function performActionIfAuthenticated(action) {
    const isAuthenticated = await checkAuthentication();
    if (isAuthenticated) {
        action(); // Выполнить действие, если пользователь аутентифицирован
    }
}

// Пример обновленных функций
function sendMoney() {
    window.location.href = "transfer.html";
}

function accountInfo() {
    window.location.href = "accountInfo.html";
}

function viewTransactions() {
    window.location.href = "allTransactions.html";
}

function viewBalance() {
   window.location.href = "balance.html";
}

function settings() {
   window.location.href = "settings.html";
}

function userTransactions() {
    window.location.href = "usersForPay.html";
}

function login() {
    window.location.href = 'authorization.html';

}

function signUp() {
    window.location.href = 'register.html';
}

// Обновляем обработчики событий кнопок
document.querySelector('#sendMoney').addEventListener('click', () => performActionIfAuthenticated(sendMoney));
document.querySelector('#accountInfoButton').addEventListener('click', () => performActionIfAuthenticated(accountInfo));
document.querySelector('#viewTransactionsButton').addEventListener('click', () => performActionIfAuthenticated(viewTransactions));
document.querySelector('#viewBalanceButton').addEventListener('click', () => performActionIfAuthenticated(viewBalance));
document.querySelector('#accountsButton').addEventListener('click', () => performActionIfAuthenticated(settings));
document.querySelector('#settingsButton').addEventListener('click', () => performActionIfAuthenticated(userTransactions));
document.querySelector('#loginButton').addEventListener('click', () => performActionIfAuthenticated(login));
document.querySelector('#signUpButton').addEventListener('click', () => performActionIfAuthenticated(signUp));

// Вызываем проверку аутентификации при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    checkAuthentication();
});
