
function getUsername() {
    return localStorage.getItem('username');
}

document.addEventListener('DOMContentLoaded', fetchAccountBalance);

function fetchAccountBalance() {
    const username = getUsername();

    fetch(`http://localhost:8080/account/info/${username}`, {
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
            displayBalance(data.balance);
        })
        .catch(error => {
            console.error('Error fetching account information:', error);
            const balanceInfoDiv = document.getElementById('balance-info');
            balanceInfoDiv.innerHTML = '<p style="color: red;">Failed to load balance information. Please try again later.</p>';
        });
}

// Функция для отображения баланса
function displayBalance(balance) {
    const balanceInfoDiv = document.getElementById('balance-info');

    // Проверяем, есть ли данные о балансе, и выводим их
    if (balance !== undefined) {
        balanceInfoDiv.innerHTML = `<p><strong>Balance:</strong> $${balance.toFixed(2)}</p>`;
    } else {
        balanceInfoDiv.innerHTML = '<p style="color: red;">No balance information available.</p>';
    }
}
