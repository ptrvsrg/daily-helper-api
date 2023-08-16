# Daily Helper API

![Daily Helper Logo](https://github.com/ptrvsrg/ptrvsrg/assets/90527574/e099ed8c-623f-48e1-b3f5-2a4e7d24c7c4)

Добро пожаловать в репозиторий Daily Helper! Этот проект представляет собой REST API приложение,
разработанное для управления задачами в рамках различных проектов. Приложение позволяет создавать,
просматривать, обновлять и удалять проекты и их задачи.

## Технологии

- Java 17
- Spring Boot 3
- Spring Data JPA
- MariaDB
- Liquibase
- Spring Security
- JWT токены
- Подтверждение через почту с использованием Spring Mail
- Рендер писем с использованием Thymeleaf
- Валидация данных с помощью Spring Validation
- Документация OpenAPI

## Установка и настройка

1. Убедитесь, что у вас установлены Java 17 и Apache Maven.
2. Клонируйте репозиторий на свой локальный компьютер:

   ```bash
      git clone https://github.com/ptrvsrg/daily-helper-api.git
   ```

3. Настройте параметры базы данных, указав соответствующие настройки для MariaDB и Liquibase:

   ```properties
    # application.properties

    spring.datasource.url=jdbc:mariadb://${DAILY_HELPER_DB_URL}
    spring.datasource.username=${DAILY_HELPER_DB_USERNAME}
    spring.datasource.password=${DAILY_HELPER_DB_PASSWORD}

    spring.liquibase.url=jdbc:mariadb://${DAILY_HELPER_DB_URL}
    spring.liquibase.user=${DAILY_HELPER_DB_USERNAME}
    spring.liquibase.password=${DAILY_HELPER_DB_PASSWORD}
   ```

4. Настройте параметры потчы:

   ```properties
    # application.properties

    spring.mail.protocol=smtps
    spring.mail.host=smtp.yandex.com
    spring.mail.port=465
    spring.mail.username=${DAILY_HELPER_MAIL_USERNAME}
    spring.mail.password=${DAILY_HELPER_MAIL_PASSWORD}
   ```

5. При желании измените параметры токенов:

   ```properties
    # application.properties

    jwt.access-token.secret=${DAILY_HELPER_JWT_SECRET}
    jwt.access-token.expired-time=3600000
    jwt.refresh-token.expired-time=604800000
   
    verification.token.expired-time=3600000
   ```

6. Запустите приложение с помощью команды:

   ```bash
    mvn spring-boot:run
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
3. Создайте pull request, описывая ваши изменения и цель.
