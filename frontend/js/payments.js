document.getElementById('fetchPaymentsButton').addEventListener('click', async () => {
    const username = getUsername(); // Используем метод для получения имени пользователя
    const paymentsContainer = document.getElementById('paymentsContainer');
    const loadingElement = document.getElementById('loading');

    // Очищаем контейнер с платежами перед новым запросом
    paymentsContainer.innerHTML = '';

    if (!username) { // Если имя пользователя не найдено
        paymentsContainer.innerHTML = '<p>No username found. Please log in.</p>';
        return;
    }

    // Отображаем "Loading..." пока идет запрос
    loadingElement.style.display = 'block';

    try {
        const response = await fetch(`http://localhost:8080/payments/all/payments/${username}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('Error fetching payments');
        }

        const payments = await response.json();

        // Останавливаем отображение "Loading..."
        loadingElement.style.display = 'none';

        if (payments.length === 0) {
            paymentsContainer.innerHTML = '<p>No payments found for this user.</p>';
            return;
        }

        // Отображаем список платежей
        payments.forEach(payment => {
            const paymentElement = document.createElement('div');
            paymentElement.classList.add('payment');

            paymentElement.innerHTML = `
                <h3>Payment ID: ${payment.paymentID}</h3>
                <p><strong>Sender:</strong> ${payment.senderName} (ID: ${payment.senderID})</p>
                <p><strong>Sender Invoice:</strong> ${payment.senderInvoice}</p>
                <p><strong>Recipient Username:</strong> ${payment.recipientUsername}</p>
                <p><strong>Recipient Invoice:</strong> ${payment.recipientInvoice}</p>
                <p><strong>Amount:</strong> ${payment.money} USD</p>
                <p><strong>Date:</strong> ${payment.date}</p>
            `;

            paymentsContainer.appendChild(paymentElement);
        });
    } catch (error) {
        loadingElement.style.display = 'none';
        paymentsContainer.innerHTML = `<p>Error: ${error.message}</p>`;
    }
});

// Функция для получения имени пользователя из локального хранилища
function getUsername() {
    return localStorage.getItem('username');
}

function toMenu() {
    window.location.href = 'menu.html';
}

