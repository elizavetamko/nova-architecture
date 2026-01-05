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
7. [Облікові дані адміністратора](#облікові-дані-адміністратора)
8. [API-документація](#api-документація)
9. [База даних](#база-даних)
10. [Система автентифікації](#система-автентифікації)
11. [Адміністративна панель](#адміністративна-панель)
12. [Конфігурація](#конфігурація)
13. [Усунення проблем](#усунення-проблем)
14. [Збірка для продакшену](#збірка-для-продакшену)

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

## URL адреса

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

## Облікові дані адміністратора

### За замовчуванням

| Параметр | Значення |
|----------|----------|
| Логін | `admin` |
| Пароль | `admin123` |

**ВАЖЛИВО:** Змініть пароль після першого входу у продакшен-середовищі!

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
Ответ:
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
| PUT | /api/admin/messages/{id}/read | Позначити як прочитане |
| DELETE | /api/admin/messages/{id} | Видалити повідомлення |

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

## Конфігурація

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
