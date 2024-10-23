function handleSuccessfulRegistration(){
    window.location.href = "menu.html";

}

// Функция для получения имени пользователя (пример)

function getUsername() {
    return localStorage.getItem('username'); // Получаем имя пользователя из localStorage
}
// Функция для загрузки информации об аккаунте
function fetchAccountInfo() {
    let usernameGlobal = getUsername(); // Получаем имя пользователя

    console.log(`Username retrieved: ${usernameGlobal}`);

    // Выполняем запрос на получение информации об аккаунте
    fetch(`http://localhost:8080/account/info/${usernameGlobal}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch account information. Please log in again.');
            }
            return response.json();
        })
        .then(data => {
            displayAccountInfo(data); // Вызов функции для отображения информации
        })
        .catch(error => {
            console.error('Error fetching account information:', error);
            const accountInfoDiv = document.getElementById('account-info');
            accountInfoDiv.innerHTML = '<p style="color: red;">Failed to load account information. Please try again later.</p>';
        });
}

// Функция для отображения информации об аккаунте на странице
function displayAccountInfo(data) {
    const accountInfoDiv = document.getElementById('account-info');

    // Конструкция HTML только для непустых полей
    const accountInfoHtml = `
        ${data.id ? `<p><strong>ID:</strong> ${data.id}</p>` : ''}
        ${data.accountName ? `<p><strong>Account Name:</strong> ${data.accountName}</p>` : ''}
        ${data.firstName ? `<p><strong>First Name:</strong> ${data.firstName}</p>` : ''}
        ${data.lastName ? `<p><strong>Last Name:</strong> ${data.lastName}</p>` : ''}
        ${data.email ? `<p><strong>Email:</strong> ${data.email}</p>` : ''}
        ${data.invoice ? `<p><strong>Invoice:</strong> ${data.invoice}</p>` : ''}
        ${data.role ? `<p><strong>Role:</strong> ${data.role}</p>` : ''}
        ${data.balance ? `<p><strong>Balance:</strong> $${data.balance.toFixed(2)}</p>` : ''}
    `;

    accountInfoDiv.innerHTML = accountInfoHtml;
}

// Функция для возврата на предыдущую страницу
function goBack() {
    window.location.href = "menu.html"; // Перенаправление на страницу меню
}
