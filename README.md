# Nova Architecture

Корпоративный сайт-визитка архитектурного бюро с админ-панелью и системой аутентификации.

---

## Содержание

1. [Структура проекта](#структура-проекта)
2. [Технологии](#технологии)
3. [Требования](#требования)
4. [Быстрый старт](#быстрый-старт)
5. [Детальная инструкция по запуску](#детальная-инструкция-по-запуску)
6. [URL адреса](#url-адреса)
7. [Учетные данные администратора](#учетные-данные-администратора)
8. [API документация](#api-документация)
9. [База данных](#база-данных)
10. [Система аутентификации](#система-аутентификации)
11. [Админ-панель](#админ-панель)
12. [Конфигурация](#конфигурация)
13. [Решение проблем](#решение-проблем)
14. [Сборка для продакшена](#сборка-для-продакшена)

---

## Структура проекта

```
Nova/
├── backend/                          # Spring Boot backend (Java 17)
│   ├── src/
│   │   └── main/
│   │       ├── java/ua/novaarchitecture/
│   │       │   ├── controller/       # REST API контроллеры
│   │       │   │   ├── AuthController.java       # Аутентификация
│   │       │   │   ├── PublicController.java     # Публичные эндпоинты
│   │       │   │   ├── AdminController.java      # Админ эндпоинты
│   │       │   │   └── FileController.java       # Загрузка файлов
│   │       │   ├── entity/           # JPA сущности
│   │       │   │   ├── Project.java              # Проекты портфолио
│   │       │   │   ├── TeamMember.java           # Члены команды
│   │       │   │   ├── ServiceItem.java          # Услуги
│   │       │   │   ├── ContactMessage.java       # Сообщения
│   │       │   │   └── AdminUser.java            # Администраторы
│   │       │   ├── repository/       # Spring Data репозитории
│   │       │   ├── service/          # Бизнес-логика
│   │       │   ├── security/         # JWT аутентификация
│   │       │   │   ├── JwtService.java           # Генерация/валидация токенов
│   │       │   │   ├── JwtAuthenticationFilter.java
│   │       │   │   ├── CustomUserDetailsService.java
│   │       │   │   └── SecurityConfig.java       # Конфигурация Spring Security
│   │       │   ├── model/            # DTO классы
│   │       │   └── config/           # Конфигурация приложения
│   │       └── resources/
│   │           ├── application.properties        # Настройки
│   │           └── templates/email/              # Email шаблоны
│   ├── data/                         # Файлы базы данных H2
│   └── pom.xml                       # Maven зависимости
│
├── frontend/                         # Статический фронтенд
│   ├── css/
│   │   ├── main.css                  # Основные стили
│   │   ├── components.css            # Компоненты
│   │   ├── responsive.css            # Адаптивность
│   │   └── animations.css            # Анимации
│   ├── js/
│   │   ├── main.js                   # Основная логика
│   │   ├── navigation.js             # Навигация
│   │   ├── parallax.js               # Параллакс эффекты
│   │   ├── scroll-animations.js      # Анимации при скролле
│   │   ├── form.js                   # Форма контактов
│   │   └── portfolio-filter.js       # Фильтр портфолио
│   ├── images/
│   │   ├── hero/                     # Фоновые изображения
│   │   ├── portfolio/                # Изображения проектов
│   │   ├── team/                     # Фото команды
│   │   └── services/                 # Изображения услуг
│   ├── index.html                    # Главная страница
│   ├── about.html                    # О компании
│   ├── services.html                 # Услуги
│   ├── portfolio.html                # Портфолио
│   ├── contacts.html                 # Контакты
│   ├── login.html                    # Страница входа администратора
│   └── admin.html                    # Админ-панель
│
├── start.bat                         # Скрипт запуска (Windows)
└── README.md                         # Этот файл
```

---

## Технологии

### Backend
| Технология | Версия | Назначение |
|------------|--------|------------|
| Java | 17+ | Язык программирования |
| Spring Boot | 3.2.0 | Фреймворк приложения |
| Spring Security | 6.2.0 | Аутентификация и авторизация |
| Spring Data JPA | 3.2.0 | Работа с базой данных |
| H2 Database | 2.2.x | Встроенная SQL база данных |
| JWT (jjwt) | 0.12.3 | JSON Web Token аутентификация |
| Lombok | 1.18.30 | Генерация boilerplate кода |
| Maven | 3.6+ | Сборка проекта |

### Frontend
| Технология | Назначение |
|------------|------------|
| HTML5 | Разметка |
| CSS3 | Стилизация (Grid, Flexbox, Custom Properties) |
| Vanilla JavaScript | Интерактивность (ES6+) |
| Google Fonts | Типографика (Space Grotesk, IBM Plex Sans) |

---

## Требования

### Обязательные
- **Java Development Kit (JDK) 17 или выше**
  - Скачать: https://adoptium.net/
  - Проверка: `java -version`

- **Apache Maven 3.6 или выше**
  - Скачать: https://maven.apache.org/download.cgi
  - Проверка: `mvn -version`

### Для запуска фронтенда (один из вариантов)
- **Python 3.x** (встроенный HTTP сервер)
  - Проверка: `python --version`
- ИЛИ любой другой HTTP сервер (nginx, Apache, VS Code Live Server)

---

## Быстрый старт

### Windows (автоматический запуск)

1. Откройте папку проекта в проводнике
2. Дважды кликните на `start.bat`
3. Дождитесь запуска обоих серверов
4. Откройте в браузере: http://localhost:8000

### Ручной запуск

```bash
# Терминал 1: Backend
cd backend
mvn spring-boot:run

# Терминал 2: Frontend
cd frontend
python -m http.server 8000
```

---

## Детальная инструкция по запуску

### Шаг 1: Установка Java

1. Скачайте JDK 17+ с https://adoptium.net/
2. Запустите установщик
3. Добавьте JAVA_HOME в переменные среды:
   - Откройте "Свойства системы" -> "Переменные среды"
   - Создайте JAVA_HOME = путь к JDK (например: C:\Program Files\Eclipse Adoptium\jdk-17.0.9.9-hotspot)
   - Добавьте %JAVA_HOME%\bin в PATH
4. Проверьте установку:
   ```
   java -version
   ```
   Должно показать версию 17 или выше.

### Шаг 2: Установка Maven

1. Скачайте Maven с https://maven.apache.org/download.cgi
2. Распакуйте архив в удобное место (например: C:\apache-maven-3.9.6)
3. Добавьте в переменные среды:
   - Создайте M2_HOME = путь к Maven
   - Добавьте %M2_HOME%\bin в PATH
4. Проверьте установку:
   ```
   mvn -version
   ```

### Шаг 3: Установка Python (опционально)

1. Скачайте Python с https://www.python.org/downloads/
2. При установке отметьте "Add Python to PATH"
3. Проверьте:
   ```
   python --version
   ```

### Шаг 4: Запуск Backend

1. Откройте командную строку (cmd) или PowerShell
2. Перейдите в папку backend:
   ```
   cd X:\2025_2\work_sl\86212\Nova_Architecture\Nova\backend
   ```
3. Первый запуск (скачивание зависимостей):
   ```
   mvn clean install
   ```
   Это может занять 5-10 минут при первом запуске.

4. Запуск сервера:
   ```
   mvn spring-boot:run
   ```

5. Дождитесь сообщения:
   ```
   Started NovaArchitectureApplication in X seconds
   ```

6. Backend готов! Работает на http://localhost:8080

### Шаг 5: Запуск Frontend

1. Откройте НОВОЕ окно командной строки
2. Перейдите в папку frontend:
   ```
   cd X:\2025_2\work_sl\86212\Nova_Architecture\Nova\frontend
   ```
3. Запустите HTTP сервер:
   ```
   python -m http.server 8000
   ```
4. Frontend готов! Откройте http://localhost:8000

---

## URL адреса

### Публичный сайт
| URL | Описание |
|-----|----------|
| http://localhost:8000 | Главная страница |
| http://localhost:8000/about.html | О компании |
| http://localhost:8000/services.html | Услуги |
| http://localhost:8000/portfolio.html | Портфолио проектов |
| http://localhost:8000/contacts.html | Контактная форма |

### Администрирование
| URL | Описание |
|-----|----------|
| http://localhost:8000/login.html | Страница входа |
| http://localhost:8000/admin.html | Админ-панель |

### Разработка/Отладка
| URL | Описание |
|-----|----------|
| http://localhost:8080/h2-console | Консоль базы данных H2 |
| http://localhost:8080/api/health | Проверка состояния сервера |

---

## Учетные данные администратора

### По умолчанию

| Параметр | Значение |
|----------|----------|
| Логин | `admin` |
| Пароль | `admin123` |

**ВАЖНО:** Смените пароль после первого входа в продакшен среде!

### Вход в систему

1. Откройте http://localhost:8000/login.html
2. Введите логин: `admin`
3. Введите пароль: `admin123`
4. Нажмите "Увійти"
5. Вы будете перенаправлены на админ-панель

---

## API документация

### Публичные эндпоинты (без авторизации)

#### Получить проекты портфолио
```http
GET /api/public/projects
```
Ответ:
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

#### Получить избранные проекты
```http
GET /api/public/projects/featured
```

#### Получить команду
```http
GET /api/public/team
```

#### Получить услуги
```http
GET /api/public/services
```

#### Отправить контактную форму
```http
POST /api/contact
Content-Type: application/json

{
  "name": "Иван Петренко",
  "email": "ivan@example.com",
  "phone": "+380501234567",
  "subject": "Консультация",
  "message": "Интересует проектирование офиса..."
}
```

### Аутентификация

#### Вход в систему
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

#### Проверка токена
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

#### Получить текущего пользователя
```http
GET /api/auth/me
Authorization: Bearer <token>
```

### Защищенные эндпоинты (требуют JWT токен)

Все запросы должны содержать заголовок:
```
Authorization: Bearer <jwt_token>
```

#### Проекты
| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/admin/projects | Получить все проекты |
| POST | /api/admin/projects | Создать проект |
| PUT | /api/admin/projects/{id} | Обновить проект |
| DELETE | /api/admin/projects/{id} | Удалить проект |

#### Команда
| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/admin/team | Получить всех членов команды |
| POST | /api/admin/team | Добавить члена команды |
| PUT | /api/admin/team/{id} | Обновить данные |
| DELETE | /api/admin/team/{id} | Удалить |

#### Услуги
| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/admin/services | Получить все услуги |
| POST | /api/admin/services | Создать услугу |
| PUT | /api/admin/services/{id} | Обновить услугу |
| DELETE | /api/admin/services/{id} | Удалить услугу |

#### Сообщения
| Метод | URL | Описание |
|-------|-----|----------|
| GET | /api/admin/messages | Получить все сообщения |
| PUT | /api/admin/messages/{id}/read | Отметить как прочитанное |
| DELETE | /api/admin/messages/{id} | Удалить сообщение |

#### Статистика
```http
GET /api/admin/stats
Authorization: Bearer <token>
```
Ответ:
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

## База данных

### H2 Database

Проект использует встроенную базу данных H2. Данные сохраняются в файл:
```
backend/data/novadb.mv.db
```

### Доступ к консоли H2

1. Откройте http://localhost:8080/h2-console
2. Введите параметры:
   - **JDBC URL:** `jdbc:h2:file:./data/novadb`
   - **User Name:** `admin`
   - **Password:** `admin`
3. Нажмите "Connect"

### Схема базы данных

#### Таблица PROJECTS
| Поле | Тип | Описание |
|------|-----|----------|
| id | BIGINT | Первичный ключ |
| title | VARCHAR(200) | Название проекта |
| description | TEXT | Описание |
| category | VARCHAR(50) | Категория (commercial, residential, cultural, interior) |
| category_label | VARCHAR(100) | Метка категории для отображения |
| project_year | VARCHAR(4) | Год проекта |
| image_path | VARCHAR(500) | Путь к изображению |
| location | VARCHAR(200) | Локация |
| area | VARCHAR(50) | Площадь |
| featured | BOOLEAN | Избранный проект |
| active | BOOLEAN | Активный (отображается на сайте) |
| sort_order | INT | Порядок сортировки |

#### Таблица TEAM_MEMBERS
| Поле | Тип | Описание |
|------|-----|----------|
| id | BIGINT | Первичный ключ |
| name | VARCHAR(200) | Имя |
| role | VARCHAR(200) | Должность |
| bio | TEXT | Биография |
| image_path | VARCHAR(500) | Путь к фото |
| email | VARCHAR(200) | Email |
| active | BOOLEAN | Активный |
| sort_order | INT | Порядок сортировки |

#### Таблица SERVICE_ITEMS
| Поле | Тип | Описание |
|------|-----|----------|
| id | BIGINT | Первичный ключ |
| title | VARCHAR(200) | Название услуги |
| description | TEXT | Описание |
| features | TEXT | Особенности (JSON массив) |
| image_path | VARCHAR(500) | Путь к изображению |
| active | BOOLEAN | Активный |
| sort_order | INT | Порядок сортировки |

#### Таблица CONTACT_MESSAGES
| Поле | Тип | Описание |
|------|-----|----------|
| id | BIGINT | Первичный ключ |
| name | VARCHAR(200) | Имя отправителя |
| email | VARCHAR(200) | Email |
| phone | VARCHAR(50) | Телефон |
| subject | VARCHAR(200) | Тема |
| message | TEXT | Сообщение |
| is_read | BOOLEAN | Прочитано |
| created_at | TIMESTAMP | Дата создания |

#### Таблица ADMIN_USERS
| Поле | Тип | Описание |
|------|-----|----------|
| id | BIGINT | Первичный ключ |
| username | VARCHAR(50) | Логин (уникальный) |
| password | VARCHAR(200) | Пароль (BCrypt хеш) |
| full_name | VARCHAR(200) | Полное имя |
| email | VARCHAR(200) | Email |
| role | VARCHAR(50) | Роль (ADMIN) |
| active | BOOLEAN | Активный |
| last_login | TIMESTAMP | Последний вход |
| created_at | TIMESTAMP | Дата создания |
| updated_at | TIMESTAMP | Дата обновления |

### Сброс базы данных

Для полного сброса данных:
1. Остановите backend сервер
2. Удалите папку `backend/data/`
3. Запустите backend снова - база создастся заново с тестовыми данными

---

## Система аутентификации

### JWT (JSON Web Token)

Проект использует JWT для аутентификации:

1. **Алгоритм:** HS384
2. **Время жизни токена:** 24 часа
3. **Хранение:** localStorage браузера

### Процесс аутентификации

```
┌──────────┐         ┌──────────┐         ┌──────────┐
│  Браузер │         │ Frontend │         │ Backend  │
└────┬─────┘         └────┬─────┘         └────┬─────┘
     │                    │                    │
     │ 1. Ввод логин/пароль                    │
     ├───────────────────>│                    │
     │                    │ 2. POST /api/auth/login
     │                    ├───────────────────>│
     │                    │                    │
     │                    │ 3. JWT Token       │
     │                    │<───────────────────┤
     │                    │                    │
     │ 4. Сохранение в localStorage            │
     │<───────────────────┤                    │
     │                    │                    │
     │ 5. Запрос к защищенному API             │
     │                    │ Authorization: Bearer <token>
     │                    ├───────────────────>│
     │                    │                    │
     │                    │ 6. Данные          │
     │                    │<───────────────────┤
     │                    │                    │
```

### Безопасность

- Пароли хешируются с помощью BCrypt
- JWT подписывается секретным ключом
- Защищенные эндпоинты проверяют токен на каждый запрос
- Невалидный токен возвращает 401 Unauthorized

---

## Админ-панель

### Разделы

1. **Дашборд** - статистика и обзор
2. **Проекты** - управление портфолио
3. **Команда** - члены команды
4. **Услуги** - список услуг
5. **Сообщения** - входящие заявки

### Функции

- Просмотр, создание, редактирование, удаление записей
- Загрузка изображений
- Отметка сообщений как прочитанных
- Сортировка и фильтрация

### Выход из системы

Нажмите кнопку "Вихід" в боковом меню. Токен будет удален из localStorage.

---

## Конфигурация

### Файл настроек

`backend/src/main/resources/application.properties`

```properties
# Порт сервера
server.port=8080

# База данных H2
spring.datasource.url=jdbc:h2:file:./data/novadb
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update

# Консоль H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JWT секретный ключ (СМЕНИТЬ В ПРОДАКШЕНЕ!)
jwt.secret=mySecretKeyForJWTTokenGenerationWhichShouldBeVeryLongAndSecure123456789

# Email режим симуляции
email.simulation=true
```

### Важные настройки для продакшена

1. **Смените JWT секретный ключ** на случайную строку минимум 64 символа
2. **Отключите H2 консоль** (`spring.h2.console.enabled=false`)
3. **Используйте внешнюю БД** (PostgreSQL, MySQL)
4. **Настройте CORS** для вашего домена
5. **Включите HTTPS**

---

## Решение проблем

### Backend не запускается

**Проблема:** "java not found" или "mvn not found"
**Решение:**
- Проверьте установку Java: `java -version`
- Проверьте установку Maven: `mvn -version`
- Добавьте пути в переменные среды PATH

**Проблема:** "Port 8080 already in use"
**Решение:**
- Закройте приложение, использующее порт 8080
- Или измените порт в application.properties: `server.port=8081`

**Проблема:** Ошибки компиляции
**Решение:**
```bash
cd backend
mvn clean install -DskipTests
mvn spring-boot:run
```

### Frontend не загружается

**Проблема:** "Connection refused" при обращении к API
**Решение:**
- Убедитесь, что backend запущен
- Проверьте http://localhost:8080/api/health

**Проблема:** CORS ошибки в консоли браузера
**Решение:**
- Backend должен быть на порту 8080
- Frontend должен быть на порту 8000

### Не работает авторизация

**Проблема:** "Невірний логін або пароль"
**Решение:**
- Используйте логин: `admin`, пароль: `admin123`
- Проверьте что backend запущен
- Проверьте логи backend на наличие ошибок

**Проблема:** Сразу редиректит на страницу входа
**Решение:**
- Очистите localStorage в браузере (F12 -> Application -> Local Storage -> Clear)
- Войдите заново

**Проблема:** "Token expired"
**Решение:**
- Войдите в систему заново (токен действует 24 часа)

### База данных

**Проблема:** "Table not found" или ошибки схемы
**Решение:**
1. Остановите backend
2. Удалите папку `backend/data/`
3. Запустите backend заново

---

## Сборка для продакшена

### Сборка JAR файла

```bash
cd backend
mvn clean package -DskipTests
```

JAR файл будет создан в `backend/target/nova-architecture-backend-1.0.0.jar`

### Запуск JAR

```bash
java -jar nova-architecture-backend-1.0.0.jar
```

### С внешней конфигурацией

```bash
java -jar nova-architecture-backend-1.0.0.jar --spring.config.location=./application-prod.properties
```

### Переменные окружения

```bash
set JWT_SECRET=your-very-long-secret-key-here
set DATABASE_URL=jdbc:postgresql://localhost:5432/novadb
java -jar nova-architecture-backend-1.0.0.jar
```

---

## Дизайн-система

### Цвета

| Цвет | HEX | Использование |
|------|-----|---------------|
| Угольный черный | #1A1A1A | Основной фон темных секций |
| Теплый белый | #F5F5F0 | Фон светлых секций, текст на темном |
| Бетонный серый | #8A8A7A | Второстепенный текст |
| Архитектурное золото | #D4AF37 | CTA кнопки, акценты |
| Графитовый | #2B2B2B | Hover-состояния |

### Шрифты

- **Space Grotesk** - заголовки
- **IBM Plex Sans** - основной текст

---

## Лицензия

(c) 2024 Nova Architecture. Все права защищены.
