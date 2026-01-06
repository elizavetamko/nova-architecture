# Nova Architecture

Корпоративний сайт-візитівка архітектурного бюро з адміністративною панеллю та системою автентифікації.

---

## Зміст

1. [Структура проєкту](#структура-проєкту)
2. [Технології](#технології)
3. [Вимоги](#вимоги)
4. [Швидкий старт](#швидкий-старт)
5. [Детальна інструкція із запуску](#детальна-інструкція-із-запуску)
6. [URL адреси](#url-адреси)
7. [Тестові дані та облікові записи](#тестові-дані-та-облікові-записи)
8. [API-документація](#api-документація)
9. [База даних](#база-даних)
10. [Система автентифікації](#система-автентифікації)
11. [Адміністративна панель](#адміністративна-панель)
12. [Конфігурація](#конфігурація)
13. [Усунення проблем](#усунення-проблем)
14. [Збірка для продакшену](#збірка-для-продакшену)
15. [Скріншоти](#скріншоти)
16. [Дизайн-система](#дизацн-система)

---

## Структура проєкту

```
Nova/
├── backend/                          # Spring Boot backend (Java 17)
│   ├── src/
│   │   └── main/
│   │       ├── java/ua/novaarchitecture/
│   │       │   ├── controller/       # REST API контролери
│   │       │   │   ├── AuthController.java       # Автентифікація
│   │       │   │   ├── PublicController.java     # Публічні ендпоінти
│   │       │   │   ├── AdminController.java      # Адміністративні ендпоінти
│   │       │   │   └── FileController.java       # Завантаження файлів
│   │       │   ├── entity/           # JPA-сутності
│   │       │   │   ├── Project.java              # Проєкти портфоліо
│   │       │   │   ├── TeamMember.java           # Члени команди
│   │       │   │   ├── ServiceItem.java          # Послуги
│   │       │   │   ├── ContactMessage.java       # Повідомлення
│   │       │   │   └── AdminUser.java            # Адміністратори
│   │       │   ├── repository/       # Репозиторії Spring Data
│   │       │   ├── service/          # Бізнес-логіка
│   │       │   ├── security/         # JWT-автентифікація
│   │       │   │   ├── JwtService.java           # Генерація / валідація токенів
│   │       │   │   ├── JwtAuthenticationFilter.java
│   │       │   │   ├── CustomUserDetailsService.java
│   │       │   │   └── SecurityConfig.java       # Конфігурація Spring Security
│   │       │   ├── model/            # DTO-класи
│   │       │   └── config/           # Конфігурація застосунку
│   │       └── resources/
│   │           ├── application.properties        # Налаштування
│   │           └── templates/email/              # Email-шаблони
│   ├── data/                         # Файли бази даних H2
│   └── pom.xml                       # Залежності Maven
│
├── frontend/                         # Статичний фронтенд
│   ├── css/
│   │   ├── main.css                  # Основні стилі
│   │   ├── components.css            # Компоненти
│   │   ├── responsive.css            # Адаптивність
│   │   └── animations.css            # Анімації
│   ├── js/
│   │   ├── main.js                   # Основна логіка
│   │   ├── navigation.js             # Навігація
│   │   ├── parallax.js               # Паралакс-ефекти
│   │   ├── scroll-animations.js      # Анімації під час прокручування
│   │   ├── form.js                   # Форма зворотного зв’язку
│   │   └── portfolio-filter.js       # Фільтр портфоліо
│   ├── images/
│   │   ├── hero/                     # Фонові зображення
│   │   ├── portfolio/                # Зображення проєктів
│   │   ├── team/                     # Фото команди
│   │   └── services/                 # Зображення послуг
│   ├── index.html                    # Головна сторінка
│   ├── about.html                    # Про компанію
│   ├── services.html                 # Послуги
│   ├── portfolio.html                # Портфоліо
│   ├── contacts.html                 # Контакти
│   ├── login.html                    # Контакти
│   └── admin.html                    # Адміністративна панель
│
├── start.bat                         # Скрипт запуску (Windows)
└── README.md                         # Цей файл
```

---

## Технології

### Backend
| Технологія | Версія | Призначення |
|------------|--------|-------------|
| Java | 17+ | Мова програмування |
| Spring Boot | 3.2.0 | Фреймворк застосунку |
| Spring Security | 6.2.0 | Автентифікація та авторизація |
| Spring Data JPA | 3.2.0 | Робота з базою даних |
| H2 Database | 2.2.x | Вбудована SQL база даних |
| JWT (jjwt) | 0.12.3 | Автентифікація на основі JSON Web Token |
| Lombok | 1.18.30 | Генерація шаблонного (boilerplate) коду |
| Maven | 3.6+ | Збірка проєкту |

### Frontend
| Технологія | Призначення |
|------------|-------------|
| HTML5 | Розмітка |
| CSS3 | Стилізація (Grid, Flexbox, Custom Properties) |
| Vanilla JavaScript | Інтерактивність (ES6+) |
| Google Fonts | Типографіка (Space Grotesk, IBM Plex Sans) |

---

## Вимоги

### Обов’язкові
- **Java Development Kit (JDK) 17 або вище**
  - Завантажити: https://adoptium.net/
  - Перевірка: `java -version`

- **Apache Maven 3.6 або вище**
  - Завантажити: https://maven.apache.org/download.cgi
  - Перевірка: `mvn -version`

### Для запуску фронтенду (один із варіантів)
- **Python 3.x** (вбудований HTTP-сервер)
  - Перевірка: `python --version`
- АБО будь-який інший HTTP-сервер (nginx, Apache, VS Code Live Server)

---

## Швидкий старт

### Windows (автоматичний запуск)

1. Відкрийте папку проєкту у провіднику
2. Двічі клацніть на `start.bat`
3. Дочекайтеся запуску обох серверів
4. Відкрийте у браузері: http://localhost:8000

### Ручний запуск

```bash
# Термінал 1: Backend
cd backend
mvn spring-boot:run

# Термінал 2: Frontend
cd frontend
python -m http.server 8000

```

---

## Детальна інструкція із запуску

### Крок 1: Встановлення Java

1. Завантажте JDK 17+ з https://adoptium.net/
2. Запустіть інсталятор
3. Додайте `JAVA_HOME` до змінних середовища:
   - Відкрийте «Властивості системи» → «Змінні середовища»
   - Створіть `JAVA_HOME` = шлях до JDK  
     (наприклад: `C:\Program Files\Eclipse Adoptium\jdk-17.0.9.9-hotspot`)
   - Додайте `%JAVA_HOME%\bin` до змінної `PATH`
4. Перевірте встановлення:
   ```
   java -version
   ```
   Має відображатися версія 17 або вище.

### Крок 2: Встановлення Maven

1. Завантажте Maven з https://maven.apache.org/download.cgi
2. Розпакуйте архів у зручне місце  
(наприклад: `C:\apache-maven-3.9.6`)
3. Додайте до змінних середовища:
- Створіть `M2_HOME` = шлях до Maven
- Додайте `%M2_HOME%\bin` до змінної `PATH`
4. Перевірте встановлення:
   ```
   mvn -version
   ```

### Крок 3: Встановлення Python (опціонально)

1. Завантажте Python з https://www.python.org/downloads/
2. Під час встановлення відмітьте пункт «Add Python to PATH»
3. Перевірте:
   ```
   python --version
   ```

### Крок 4: Запуск Backend

1. Відкрийте командний рядок (cmd) або PowerShell
2. Перейдіть до папки `backend`:
   ```
   cd X:\2025_2\work_sl\86212\Nova_Architecture\Nova\backend
   ```
3. Перший запуск (завантаження залежностей):
   ```
   mvn clean install
   ```
   Це може зайняти 5–10 хвилин під час першого запуску.

4. Запуск сервера:
   ```
   mvn spring-boot:run
   ```

5. Дочекайтеся повідомлення:
   ```
   Started NovaArchitectureApplication in X seconds
   ```

6. Backend готов! Працює за адресою:  http://localhost:8080

### Крок 5: Запуск Frontend

1. Відкрийте НОВЕ вікно командного рядка
2. Перейдіть до папки `frontend`:
   ```
   cd X:\2025_2\work_sl\86212\Nova_Architecture\Nova\frontend
   ```
3. Запустіть HTTP-сервер:
   ```
   python -m http.server 8000
   ```
4. Frontend готовий! Відкрийте у браузері: ://localhost:8000

---

## URL адреси

### Публічний сайт
| URL | Опис |
|-----|------|
| http://localhost:8000 | Головна сторінка |
| http://localhost:8000/about.html | Про компанію |
| http://localhost:8000/services.html | Послуги |
| http://localhost:8000/portfolio.html | Портфоліо проєктів |
| http://localhost:8000/contacts.html | Контактна форма |

### Адміністрування
| URL | Опис |
|-----|------|
| http://localhost:8000/login.html | Сторінка входу |
| http://localhost:8000/admin.html | Адміністративна панель |

### Розробка / налагодження
| URL | Опис |
|-----|------|
| http://localhost:8080/h2-console | Консоль бази даних H2 |
| http://localhost:8080/api/health | Перевірка стану сервера |

---

## Тестові дані та облікові записи

### За замовчуванням обліковий запис адміністратора

| Параметр | Значення |
|----------|----------|
| Логін | `admin` |
| Пароль | `admin123` |

**ВАЖЛИВО:** Змініть пароль після першого входу у продакшен-середовищі!

### Тестові дані в базі
-	Проєкти портфоліо: Тестові проєкти в категоріях "commercial", "residential", "cultural", "interior" (знаходяться в базі H2, доступні через /api/public/projects).
-	Члени команди: Тестові записи з іменами, ролями та біо (через /api/public/team).
-	Послуги: Тестові послуги з описами (через /api/public/services).
-	Повідомлення: Тестові контактні повідомлення (через адмін-панель або /api/admin/messages).
-	База даних H2: Доступ через консоль з URL jdbc:h2:file:./data/novadb, логін admin, пароль admin. Тестові дані генеруються автоматично при першому запуску.

### Вхід до системи

1. Відкрийте http://localhost:8000/login.html
2. Введіть логін: `admin`
3. Введіть пароль: `admin123`
4. Натисніть **«Увійти»**
5. Вас буде перенаправлено до адміністративної панелі


---

## API-документація

### Публічні ендпоінти (без авторизації)

#### Отримати проєкти портфоліо
```http
GET /api/public/projects
```
Відповідь:
```json
[
  {
    "id": 1,
    "title": "Бізнес-центр \"Horizon\"",
    "description": "Сучасний бізнес-центр класу А...",
    "category": "commercial",
    "categoryLabel": "Комерційна",
    "year": "2024",
    "imagePath": "images/portfolio/project-1.jpg",
    "location": "Київ",
    "area": "45 000 м²",
    "featured": true
  }
]
```

#### #### Отримати обрані проєкти
```http
GET /api/public/projects/featured
```

#### Отримати команду
```http
GET /api/public/team
```

#### Отримати послуги
```http
GET /api/public/services
```

#### Відправити контактну форму
```http
POST /api/contact
Content-Type: application/json

{
  "name": "Іван Петренко",
  "email": "ivan@example.com",
  "phone": "+380501234567",
  "subject": "Консультація",
  "message": "Цікавить проєктування офісу..."
}
```

### Автентифікація

#### Вхід в систему
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```
Відповідь:
```json
{
  "success": true,
  "token": "eyJhbGciOiJIUzM4NCJ9...",
  "username": "admin",
  "fullName": "Адміністратор",
  "role": "ADMIN",
  "message": "Login successful"
}
```

#### Перевірка токена
```http
POST /api/auth/validate
Authorization: Bearer <token>
```
Ответ:
```json
{
  "valid": true
}
```

#### Отримати поточного користувача
```http
GET /api/auth/me
Authorization: Bearer <token>
```

### Захищені ендпоінти (потребують JWT-токена)

Усі запити повинні містити заголовок:
```
Authorization: Bearer <jwt_token>
```

#### Проєкти
| Метод | URL | Опис |
|-------|-----|------|
| GET | /api/admin/projects | Отримати всі проєкти |
| POST | /api/admin/projects | Створити проєкт |
| PUT | /api/admin/projects/{id} | Оновити проєкт |
| DELETE | /api/admin/projects/{id} | Видалити проєкт |

#### Команда
| Метод | URL | Опис |
|-------|-----|------|
| GET | /api/admin/team | Отримати всіх членів команди |
| POST | /api/admin/team | Додати члена команди |
| PUT | /api/admin/team/{id} | Оновити дані |
| DELETE | /api/admin/team/{id} | Видалити |

#### Послуги
| Метод | URL | Опис |
|-------|-----|------|
| GET | /api/admin/services | Отримати всі послуги |
| POST | /api/admin/services | Створити послугу |
| PUT | /api/admin/services/{id} | Оновити послугу |
| DELETE | /api/admin/services/{id} | Видалити послугу |

#### Повідомлення
| Метод | URL | Опис |
|-------|-----|------|
| GET | /api/admin/messages | Отримати всі повідомлення |
| GET | /api/admin/messages/unread | Отримати непрочитані повідомлення |
| PATCH | /api/admin/messages/{id}/read | Позначити як прочитане |
| DELETE | /api/admin/messages/{id} | Видалити повідомлення |

#### Завантаження файлів
| Метод | URL | Опис |
|-------|-----|------|
| POST | /api/files/upload | Завантажити файл (multipart/form-data) |
| GET | /api/files/{folder}/{filename} | Отримати файл |
| DELETE | /api/files/{folder}/{filename} | Видалити файл |


#### Статистика
```http
GET /api/admin/stats
Authorization: Bearer <token>
```
Відповідь:
```json
{
  "totalProjects": 6,
  "activeProjects": 6,
  "totalTeamMembers": 3,
  "totalMessages": 5,
  "unreadMessages": 2
}
```

---

## База даних

### H2 Database

Проєкт використовує вбудовану базу даних H2. Дані зберігаються у файл:
```
backend/data/novadb.mv.db
```

### Доступ до консолі H2

1. Відкрийте http://localhost:8080/h2-console
2. Введіть параметри:
   - **JDBC URL:** `jdbc:h2:file:./data/novadb`
   - **User Name:** `admin`
   - **Password:** `admin`
3. Натисніть **«Connect»**

### Схема бази даних

#### Таблиця PROJECTS
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGINT | Первинний ключ |
| title | VARCHAR(200) | Назва проєкту |
| description | TEXT | Опис |
| category | VARCHAR(50) | Категорія (commercial, residential, cultural, interior) |
| category_label | VARCHAR(100) | Мітка категорії для відображення |
| project_year | VARCHAR(4) | Рік проєкту |
| image_path | VARCHAR(500) | Шлях до зображення |
| location | VARCHAR(200) | Локація |
| area | VARCHAR(50) | Площа |
| featured | BOOLEAN | Обраний проєкт |
| active | BOOLEAN | Активний (відображається на сайті) |
| sort_order | INT | Порядок сортування |

#### Таблиця TEAM_MEMBERS
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGINT | Первинний ключ |
| name | VARCHAR(200) | Ім’я |
| role | VARCHAR(200) | Посада |
| bio | TEXT | Біографія |
| image_path | VARCHAR(500) | Шлях до фото |
| email | VARCHAR(200) | Email |
| active | BOOLEAN | Активний |
| sort_order | INT | Порядок сортування |

#### Таблиця SERVICE_ITEMS
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGINT | Первинний ключ |
| title | VARCHAR(200) | Назва послуги |
| description | TEXT | Опис |
| features | TEXT | Особливості (JSON-масив) |
| image_path | VARCHAR(500) | Шлях до зображення |
| active | BOOLEAN | Активний |
| sort_order | INT | Порядок сортування |

#### Таблиця CONTACT_MESSAGES
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGINT | Первинний ключ |
| name | VARCHAR(200) | Ім’я відправника |
| email | VARCHAR(200) | Email |
| phone | VARCHAR(50) | Телефон |
| subject | VARCHAR(200) | Тема |
| message | TEXT | Повідомлення |
| is_read | BOOLEAN | Прочитано |
| created_at | TIMESTAMP | Дата створення |

#### Таблиця ADMIN_USERS
| Поле | Тип | Опис |
|------|-----|------|
| id | BIGINT | Первинний ключ |
| username | VARCHAR(50) | Логін (унікальний) |
| password | VARCHAR(200) | Пароль (BCrypt-хеш) |
| full_name | VARCHAR(200) | Повне ім’я |
| email | VARCHAR(200) | Email |
| role | VARCHAR(50) | Роль (ADMIN) |
| active | BOOLEAN | Активний |
| last_login | TIMESTAMP | Останній вхід |
| created_at | TIMESTAMP | Дата створення |
| updated_at | TIMESTAMP | Дата оновлення |


### Скидання бази даних

Для повного скидання даних:
1. Зупиніть backend-сервер
2. Видаліть папку `backend/data/`
3. Запустіть backend знову — база даних буде створена повторно з тестовими даними


---

## Система автентифікації

### JWT (JSON Web Token)

Проєкт використовує JWT для автентифікації:

1. **Алгоритм:** HS384
2. **Термін дії токена:** 24 години
3. **Зберігання:** localStorage браузера

### Процес автентификації

```
┌──────────┐         ┌──────────┐         ┌──────────┐
│  Браузер │         │ Frontend │         │ Backend  │
└────┬─────┘         └────┬─────┘         └────┬─────┘
     │                    │                    │
     │ 1. Ввод логін/пароль                    │
     ├───────────────────>│                    │
     │                    │ 2. POST /api/auth/login
     │                    ├───────────────────>│
     │                    │                    │
     │                    │ 3. JWT Token       │
     │                    │<───────────────────┤
     │                    │                    │
     │ 4. Збереження в localStorage            │
     │<───────────────────┤                    │
     │                    │                    │
     │ 5. Запит к захищенному API             │
     │                    │ Authorization: Bearer <token>
     │                    ├───────────────────>│
     │                    │                    │
     │                    │ 6. Дані          │
     │                    │<───────────────────┤
     │                    │                    │
```

### Безпека

- Паролі хешуються за допомогою BCrypt
- JWT підписується секретним ключем
- Захищені ендпоінти перевіряють токен під час кожного запиту
- Невалідний токен повертає **401 Unauthorized**

---

## Адміністративна панель

### Розділи

1. **Дашборд** — статистика та огляд
2. **Проєкти** — керування портфоліо
3. **Команда** — члени команди
4. **Послуги** — список послуг
5. **Повідомлення** — вхідні заявки

### Функції

- Перегляд, створення, редагування та видалення записів
- Завантаження зображень
- Позначення повідомлень як прочитаних
- Сортування та фільтрація

### Вихід із системи

Натисніть кнопку **«Вихід»** у боковому меню. Токен буде видалено з `localStorage`.


---

## Конфігурація та змінні середовища

### Файл налаштувань

`backend/src/main/resources/application.properties`

```properties
# Порт сервера
server.port=8080

# База даних H2
spring.datasource.url=jdbc:h2:file:./data/novadb
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update

# Консоль H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT секретний ключ (ЗМІНИТИ У ПРОДАКШЕНІ!)
jwt.secret=mySecretKeyForJWTTokenGenerationWhichShouldBeVeryLongAndSecure123456789

# Email режим симуляції
email.simulation=true

# CORS дозволені origins
app.cors.allowed-origins=http://localhost:3000,http://localhost:5500,http://127.0.0.1:5500,http://localhost:8000,http://127.0.0.1:8000

Змінні середовища (для продакшена)
| Змінна | Опис | Приклад |
|------|-----|------|
| JWT_SECRET | Секретний ключ для JWT | myVeryLongSecretKey1234567890 |
| SPRING_DATASOURCE_URL | URL бази даних | jdbc:postgresql://localhost:5432/novadb |
| SPRING_DATASOURCE_USERNAME | Логін БД | postgres |
| SPRING_DATASOURCE_PASSWORD | Пароль БД | postgres |
| SPRING_DATASOURCE_USERNAME | Логін БД | password |
| APP_CORS_ALLOWED_ORIGINS | Дозволені origins | https://nova-arch.ua |
| MAIL_USERNAME | Email для надсилання | noreply@nova-arch.ua |
| MAIL_PASSWORD | Пароль email | app_password |
| ADMIN_EMAIL | Email адміністратора | info@nova-arch.ua |

Використання змінних:
```bash
export JWT_SECRET=myVeryLongSecretKey1234567890
java -jar nova-architecture-backend-1.0.0.jar
```

### Важливі налаштування для продакшену

1. **Змініть JWT секретний ключ** на випадковий рядок довжиною щонайменше 64 символи
2. **Вимкніть консоль H2** (`spring.h2.console.enabled=false`)
3. **Використовуйте зовнішню базу даних** (PostgreSQL, MySQL)
4. **Налаштуйте CORS** для вашого домену
5. **Увімкніть HTTPS**


---

## Усунення проблем

### Backend не запускається

**Проблема:** «java not found» або «mvn not found»  
**Рішення:**
- Перевірте встановлення Java: `java -version`
- Перевірте встановлення Maven: `mvn -version`
- Додайте відповідні шляхи до змінної середовища `PATH`

**Проблема:** «Port 8080 already in use»  
**Рішення:**
- Закрийте застосунок, який використовує порт 8080
- Або змініть порт у файлі `application.properties`: `server.port=8081`

**Проблема:** Помилки компіляції  
**Рішення:**
```bash
cd backend
mvn clean install -DskipTests
mvn spring-boot:run
```

### Frontend не завантажується

**Проблема:** «Connection refused» під час звернення до API  
**Рішення:**
- Переконайтеся, що backend запущений
- Перевірте http://localhost:8080/api/health

**Проблема:** CORS-помилки в консолі браузера  
**Рішення:**
- Backend має працювати на порту 8080
- Frontend має працювати на порту 8000

---

### Не працює авторизація

**Проблема:** «Невірний логін або пароль»  
**Рішення:**
- Використовуйте логін: `admin`, пароль: `admin123`
- Перевірте, що backend запущений
- Перевірте логи backend на наявність помилок

**Проблема:** Одразу відбувається редирект на сторінку входу  
**Рішення:**
- Очистіть `localStorage` у браузері  
  (F12 → Application → Local Storage → Clear)
- Увійдіть до системи повторно

**Проблема:** «Token expired»  
**Рішення:**
- Увійдіть до системи знову (токен дійсний 24 години)

---

### База даних

**Проблема:** «Table not found» або помилки схеми  
**Рішення:**
1. Зупиніть backend
2. Видаліть папку `backend/data/`
3. Запустіть backend повторно

---

## Збірка для продакшену

### Збірка JAR-файлу

```bash
cd backend
mvn clean package -DskipTests
```

JAR файл буде створено в `backend/target/nova-architecture-backend-1.0.0.jar`

### Запуск JAR

```bash
java -jar nova-architecture-backend-1.0.0.jar
```

### Із зовнішньою конфігурацією

```bash
java -jar nova-architecture-backend-1.0.0.jar --spring.config.location=./application-prod.properties
```

### Змінні середовища

```bash
set JWT_SECRET=your-very-long-secret-key-here
set DATABASE_URL=jdbc:postgresql://localhost:5432/novadb
java -jar nova-architecture-backend-1.0.0.jar
```

---

## Скріншоти
<img width="1063" height="508" alt="image" src="https://github.com/user-attachments/assets/1c3de865-11df-47f7-8a3a-425bede89f78" />
Рисунок 1 – Головний екран. Банер

<img width="698" height="282" alt="image" src="https://github.com/user-attachments/assets/ddcb1a17-6e4f-4aa8-8cf3-567502c1d8b7" />
Рисунок 2 – Головний екран. Блок "Архітектура, що визначає майбутне"

<img width="523" height="287" alt="image" src="https://github.com/user-attachments/assets/d2c8195b-3404-42dc-85e0-f229918bd6c8" />
Рисунок 3 – Головний екран. Блок "Вибрані проєкти"

<img width="645" height="278" alt="image" src="https://github.com/user-attachments/assets/15023eb0-5a38-4501-b32f-9a42a76907ac" />
Рисунок 4 – Головний екран. Блок "Наші послуги"

<img width="506" height="178" alt="image" src="https://github.com/user-attachments/assets/9ae42745-5933-4559-90a8-093584d81189" />
Рисунок 5 – Головний екран. Блок "Готові втілити вашу ідею?"

<img width="847" height="293" alt="image" src="https://github.com/user-attachments/assets/013cb967-2307-4a58-905d-4f3d8bc511cc" />
Рисунок 6 – Сторінка "Про нас". Банер "Ми створюємо архітектуру, що змінює простір"

<img width="675" height="389" alt="image" src="https://github.com/user-attachments/assets/8a907b01-4ad4-494e-8163-d6ee6ddcf6ff" />
Рисунок 7 – Сторінка "Про нас". Блок "Витончений бруталізм, як мистецтво"

<img width="716" height="305" alt="image" src="https://github.com/user-attachments/assets/857f573f-5c72-4342-94d8-ae9779e792e1" />
Рисунок 8 – Сторінка "Про нас". Блок "Принципи, якими ми керуємось"

<img width="432" height="507" alt="image" src="https://github.com/user-attachments/assets/9ab484f6-686e-4054-b6c8-4d86890f9fbb" />
Рисунок 9 – Сторінка "Про нас". Блок "Шлях розвитку"

<img width="494" height="492" alt="image" src="https://github.com/user-attachments/assets/4ae81736-d8c4-4d93-b050-aae400063979" />
Рисунок 10 – Сторінка "Про нас". Блок "Люди, які створюють Nova"

<img width="647" height="222" alt="image" src="https://github.com/user-attachments/assets/763d3f01-4779-49ad-94b3-a12540347267" />
Рисунок 11 – Сторінка "Про нас". Блок "Приєднуйся до нашої історії"

<img width="526" height="461" alt="image" src="https://github.com/user-attachments/assets/f939d4ed-67ce-4047-b7a2-7fd0149ed926" />
Рисунок 12 – Сторінка "Портфоліо реалізованих проєктів". Блок з картками проєктів

<img width="839" height="345" alt="image" src="https://github.com/user-attachments/assets/4e8b8b56-b39e-4e3d-8842-31a22baea17a" />
Рисунок 13 – Сторінка "Портфоліо реалізованих проєктів". Нижній блок 

<img width="670" height="501" alt="image" src="https://github.com/user-attachments/assets/7ac36461-5685-4aa2-9671-fb5b03482437" />
Рисунок 14 – Сторінка "Послуги". Блок "Архітектурне проєктування"

<img width="554" height="205" alt="image" src="https://github.com/user-attachments/assets/dd02d178-776a-49cf-afa3-90729f6e7a15" />
Рисунок 15 – Сторінка "Послуги". Блок "Дизайн інтер'єрів"

<img width="523" height="221" alt="image" src="https://github.com/user-attachments/assets/3ad5bf9a-71bb-4ffa-854b-8bb4239f60cf" />
Рисунок 16 – Сторінка "Послуги". Блок "3D візуалізація"

<img width="886" height="305" alt="image" src="https://github.com/user-attachments/assets/ffc2ac24-48c1-4621-8173-5d7bdfadab7c" />
Рисунок 17 – Сторінка "Послуги". Блок "Етапи співпраці"

<img width="842" height="430" alt="image" src="https://github.com/user-attachments/assets/0fb17de9-e706-4240-b23c-59ce4841140e" />
Рисунок 18 – Сторінка "Послуги". Блок "Обговоримо ваш проєкт"

<img width="723" height="285" alt="image" src="https://github.com/user-attachments/assets/4dee5300-c0b0-4a3a-9884-5fe148f25d13" />
Рисунок 19 – Сторінка "Контакти" 

<img width="852" height="145" alt="image" src="https://github.com/user-attachments/assets/6ef795eb-6651-420d-84ea-15e3495c4386" />
Рисунок 20 – Адмінпанель. Дашборд

<img width="867" height="165" alt="image" src="https://github.com/user-attachments/assets/1d515370-d352-40cc-a6ab-3a86112c9ae2" />
Рисунок 21 – Адмінпанель. Проєкти

<img width="176" height="231" alt="image" src="https://github.com/user-attachments/assets/9a729000-5c88-478f-a63a-6a4f97b33fad" />
Рисунок 22 – Форма додавання проєкту

<img width="907" height="141" alt="image" src="https://github.com/user-attachments/assets/4edaf179-2491-4597-9c9a-a801097ed9dd" />
Рисунок 23 – Адмінпанель. Повідомлення

<img width="286" height="256" alt="image" src="https://github.com/user-attachments/assets/908607bf-fb32-47ea-954a-b6c7d6f20f5a" />
Рисунок 24 – Модальне вікно "Повідомлення"

<img width="1066" height="172" alt="image" src="https://github.com/user-attachments/assets/a98b7c68-66f4-4348-9791-29ba51c123c3" />
Рисунок 25 – Адмінпанель. Команда

<img width="373" height="108" alt="image" src="https://github.com/user-attachments/assets/f06beb5f-d0b4-4504-8732-f90040ab94f0" />
Рисунок 26 – Повідомлення з запитом на видалення члена команди

<img width="230" height="283" alt="image" src="https://github.com/user-attachments/assets/b60017e2-925e-4e61-b397-259c6e655019" />
Рисунок 27 – Форма додавання члена команди

<img width="969" height="152" alt="image" src="https://github.com/user-attachments/assets/16d3f49d-6caa-441b-a8b1-5eb00fc794d4" />
Рисунок 28 – Адмінпанель. Послуги

<img width="216" height="195" alt="image" src="https://github.com/user-attachments/assets/0cbbbe31-a56a-4465-b432-bf97191ac396" />
Рисунок 29 – Форма редагування послуги

<img width="218" height="200" alt="image" src="https://github.com/user-attachments/assets/f8756e01-94cc-49fd-ab21-2c3c217a166a" />
Рисунок 30 – Форма додавання послуги

---

## Дизайн-система

### Кольори

| Колір | HEX | Використання |
|------|-----|--------------|
| Вугільний чорний | #1A1A1A | Основний фон темних секцій |
| Теплий білий | #F5F5F0 | Фон світлих секцій, текст на темному |
| Бетонний сірий | #8A8A7A | Другорядний текст |
| Архітектурне золото | #D4AF37 | CTA-кнопки, акценти |
| Графітовий | #2B2B2B | Hover-стани |


### Шрифти

- **Space Grotesk** - заголовки
- **IBM Plex Sans** - основний текст

---

## Ліцензія

(c) 2025 Nova Architecture. Усі права захищені.
