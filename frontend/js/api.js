function goToLogin() {
    document.getElementById("registrationForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Предотвращаем стандартное поведение формы

        // Собираем данные формы в переменные
        const firstName = document.querySelector("input[name='firstName']").value;
        const lastName = document.querySelector("input[name='lastName']").value;
        const username = document.querySelector("input[name='username']").value;
        const email = document.querySelector("input[name='email']").value;
        const password = document.querySelector("input[name='password']").value;
        const confirmPassword = document.querySelector("input[name='confirmPassword']").value;

        // Проверка совпадения паролей
        if (password !== confirmPassword) {
            alert("Passwords do not match!"); // Уведомление об ошибке
            return; // Прерываем выполнение, если пароли не совпадают
        }

        // Собираем данные в объект JSON
        const data = {
            firstName: firstName,
            lastName: lastName,
            accountName: username,
            email: email,
            password: password
        };

        // Отправляем данные на сервер
        fetch("http://localhost:8080/authorization/registration", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (response.ok) {
                    // Успешный ответ
                    console.log("Registration successful!");
                    // Можно перенаправить пользователя или отобразить сообщение
                } else {
                    // Обработка ошибок
                    console.error("Registration failed:", response.statusText);
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });
}
