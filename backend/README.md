# Nova Architecture Backend

Spring Boot бекенд для сайту архітектурного бюро Nova Architecture.

## Вимоги

- Java 17+
- Maven 3.8+

## Запуск

### Розробка

```bash
# Клонування та перехід в директорію
cd backend

# Встановлення залежностей
mvn clean install

# Запуск у режимі розробки
mvn spring-boot:run
```

Сервер буде доступний на `http://localhost:8080`

### Продакшн

```bash
# Збірка JAR
mvn clean package -DskipTests

# Запуск
java -jar target/nova-architecture-backend-1.0.0.jar
```

## Конфігурація

### Змінні середовища

| Змінна | Опис | Приклад |
|--------|------|---------|
| `MAIL_USERNAME` | Email для відправки | `noreply@gmail.com` |
| `MAIL_PASSWORD` | App Password | `xxxx xxxx xxxx xxxx` |
| `ADMIN_EMAIL` | Email адміністратора | `info@nova-arch.ua` |
| `FROM_EMAIL` | Email відправника | `noreply@nova-arch.ua` |

### application.properties

```properties
server.port=8080
spring.mail.host=smtp.gmail.com
spring.mail.port=587
```

## Структура

```
src/main/java/ua/novaarchitecture/
├── NovaArchitectureApplication.java  # Точка входу
├── controller/
│   └── ContactController.java        # REST API контролер
├── service/
│   ├── ContactService.java           # Бізнес-логіка
│   └── EmailService.java             # Відправка email
├── model/
│   ├── ContactRequest.java           # DTO запиту
│   └── ContactResponse.java          # DTO відповіді
├── config/
│   ├── WebConfig.java                # CORS налаштування
│   └── AsyncConfig.java              # Асинхронна обробка
└── exception/
    └── GlobalExceptionHandler.java   # Обробка помилок
```

## API Endpoints

### POST /api/contact

Обробка форми зворотного зв'язку.

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
    "name": "Ім'я",
    "email": "email@example.com",
    "phone": "+380501234567",
    "message": "Текст повідомлення"
}
```

**Responses:**

`200 OK` - Успіх
```json
{
    "success": true,
    "message": "Дякуємо! Ваше повідомлення надіслано.",
    "timestamp": "2024-12-13T14:30:00Z"
}
```

`400 Bad Request` - Помилка валідації
```json
{
    "success": false,
    "message": "Помилка валідації",
    "errors": {
        "name": "Ім'я має містити від 2 до 100 символів",
        "email": "Введіть коректну email адресу"
    }
}
```

### GET /api/health

Перевірка стану сервера.

**Response:** `200 OK` - "OK"

## Валідація

| Поле | Правила |
|------|---------|
| name | Обов'язкове, 2-100 символів |
| email | Обов'язкове, валідний формат |
| phone | Необов'язкове, формат +380XXXXXXXXX |
| message | Обов'язкове, 10-1000 символів |

## Email Templates

Шаблони email знаходяться в `src/main/resources/templates/email/`:

- `admin-notification.html` - Сповіщення адміністратору
- `user-confirmation.html` - Підтвердження користувачу

## CORS

Дозволені origins для розробки:
- http://localhost:3000
- http://localhost:5500
- http://127.0.0.1:5500

Налаштування в `application.properties`:
```properties
app.cors.allowed-origins=http://localhost:3000,http://localhost:5500
```
