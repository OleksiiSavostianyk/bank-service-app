document.getElementById('transferButton').addEventListener('click', async () => {
    const recipientInput = document.getElementById('recipient').value.trim(); // Убираем лишние пробелы
    const amountInput = document.getElementById('amount').value.trim();

    // Получаем имя отправителя
    const senderName = getUsername();

    // Создаем объект платежа на основе пользовательского ввода
    let payment = {
        senderName: senderName,
        money: parseFloat(amountInput),
    };

    // Проверяем, является ли recipientInput только цифрами
    const isNumeric = /^\d+$/.test(recipientInput); // Проверка на наличие только цифр

    if (isNumeric) {
        // Если recipientInput состоит только из цифр, добавляем как номер счета
        payment.recipientInvoice = recipientInput; // Здесь recipientInvoice будет строкой
    } else {
        // Если recipientInput не является числом, добавляем как имя пользователя
        payment.recipientUsername = recipientInput;
    }

    try {
        const response = await fetch('http://localhost:8080/payments/transfer/money', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payment),
        });

        const result = await response.text();
        if (response.ok) {
            // Сообщение об успехе
            window.location.href = 'balance.html';
        } else {
            throw new Error(result); // Генерация ошибки, если ответ не OK
        }
    } catch (error) {
        document.getElementById('message').textContent = `Error: ${error.message}`; // Сообщение об ошибке
        document.getElementById('message').style.color = "red"; // Цвет сообщения об ошибке
    }
});

// Обработчик события для кнопки "Вернуться в меню"
document.getElementById('backButton').addEventListener('click', () => {
    window.location.href = 'menu.html'; // Поменяйте 'menu.html' на адрес вашей страницы меню
});

// Функция для получения имени пользователя из локального хранилища
function getUsername() {
    return localStorage.getItem('username');
}
