  // Обработчик отправки формы
document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault(); // Отменяем стандартное поведение формы

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/authorization/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            throw new Error('Login failed. Please try again.');
        }

        // Сохраняем имя пользователя в localStorage
        localStorage.setItem('username', username);

        // Переход на страницу меню
        window.location.href = '/menu.html';  // Укажите URL вашей страницы меню
    } catch (error) {
        // Отображаем сообщение об ошибке
        document.getElementById('message').textContent = error.message;

        // Очищаем поля формы для повторного ввода
        document.getElementById('username').value = '';
        document.getElementById('password').value = '';
    }
});

// Функция для доступа к имени пользователя из любой другой функции
function getUsername() {
    return localStorage.getItem('username'); // Получаем имя пользователя из localStorage
}
