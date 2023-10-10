# Daily Helper API

![Daily Helper Logo](https://github.com/ptrvsrg/ptrvsrg/assets/90527574/e099ed8c-623f-48e1-b3f5-2a4e7d24c7c4)

<p align="center">
   <a href="https://github.com/ptrvsrg/daily-helper-api/graphs/contributors">
        <img alt="GitHub contributors" src="https://img.shields.io/github/contributors/ptrvsrg/daily-helper-api?label=Contributors&labelColor=222222&color=77D4FC"/>
   </a>
   <a href="https://github.com/ptrvsrg/daily-helper-api/forks">
        <img alt="GitHub forks" src="https://img.shields.io/github/forks/ptrvsrg/daily-helper-api?label=Forks&labelColor=222222&color=77D4FC"/>
   </a>
   <a href="https://github.com/ptrvsrg/daily-helper-api/stargazers">
        <img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/ptrvsrg/daily-helper-api?label=Stars&labelColor=222222&color=77D4FC"/>
   </a>
   <a href="https://github.com/ptrvsrg/daily-helper-api/issues">
        <img alt="GitHub issues" src="https://img.shields.io/github/issues/ptrvsrg/daily-helper-api?label=Issues&labelColor=222222&color=77D4FC"/>
   </a>
   <a href="https://github.com/ptrvsrg/daily-helper-api/pulls">
        <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/ptrvsrg/daily-helper-api?label=Pull%20Requests&labelColor=222222&color=77D4FC"/>
   </a>
</p>

Добро пожаловать в репозиторий Daily Helper! Этот проект представляет собой REST API приложение,
разработанное для управления задачами в рамках различных проектов. Приложение позволяет создавать,
просматривать, обновлять и удалять проекты и их задачи.

## Технологии

- Java 17
- Spring Boot 3

- SQL
- Spring Data JPA
- MariaDB
- Liquibase

- Spring Security
- JWT

- Spring Mail
- Thymeleaf

- Spring Validation

- OpenAPI

- Docker Compose

## Установка и настройка

### Вручную 

1. Убедитесь, что у вас установлены Java 17 и Apache Maven.
2. Клонируйте репозиторий на свою локальную машину:

   ```bash
      git clone https://github.com/ptrvsrg/daily-helper-api.git
   ```

3. Настройте переменные среды базы данных:

   + DAILY_HELPER_DB_AUTHORITY - хост и порт базы данных
   + DAILY_HELPER_DB_SCHEMA_NAME - название схемы
   + DAILY_HELPER_DB_URL - URL схемы (<хост>:<порт>/<название-схемы>)
   + DAILY_HELPER_DB_USERNAME - имя пользователя схемы
   + DAILY_HELPER_DB_PASSWORD - пароль пользователя схемы

4. Настройте переменные среды почты:

   + DAILY_HELPER_MAIL_PROTOCOL - протокол SMTP сервера
   + DAILY_HELPER_MAIL_HOST - хост SMTP сервера
   + DAILY_HELPER_MAIL_PORT - порт SMTP сервера
   + DAILY_HELPER_MAIL_USERNAME - имя пользователя SMTP сервера
   + DAILY_HELPER_MAIL_PASSWORD - пароль пользователя SMTP сервера

5. Настройте переменные среды токенов:

   + DAILY_HELPER_JWT_SECRET - секретное слово для JWT токенов

6. Запустите приложение с помощью команды:

   ```bash
    mvn spring-boot:run
   ```
   
### Docker Compose

1. Клонируйте репозиторий на свою локальную машину:

   ```bash
      git clone https://github.com/ptrvsrg/daily-helper-api.git
   ```

2. Добавьте значения переменных среды в файл [sample.env](./sample.env) и переименуйте его в .env
3. Запустите контейнеры с помощью команды:

   ```bash
    sudo docker compose up -d
   ```

## Документация API

Документация API предоставляется с использованием файла YAML для OpenAPI. Данный файл описывает
доступные эндпоинты, параметры запросов и ожидаемые ответы.

Открыть документацию можно с использованием:

+ [Swagger Editor](https://editor.swagger.io/)
+ [OpenAPI Specifications](https://plugins.jetbrains.com/plugin/14394-openapi-specifications)

## Вклад в проект

Если вы хотите внести свой вклад в проект, вы можете следовать этим шагам:

1. Создайте форк этого репозитория.
2. Внесите необходимые изменения.
3. Создайте pull request, описывая ваши изменения.
