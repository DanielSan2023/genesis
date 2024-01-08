# Genesis

This app was created with Spring.io - tips on working with the code [can be found here](https://start.spring.io/)).
Feel free to contact us for further questions.

## Development

Update your local database connection in `application.yml` or create your own `application-local.yml` file to override
settings for development.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options".

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

After starting the application it is accessible under `localhost:8080`.

## MySQL

Create Database "genesis"...
```sql
CREATE DATABASE `genesis` 
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE 
utf8mb4_0900_ai_ci */ 
/*!80016 DEFAULT ENCRYPTION='N' */;
```

Create Table "user_info"...
```sql
CREATE TABLE user_info (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255),
surname VARCHAR(255),
person_id VARCHAR(12) UNIQUE,
uuid VARCHAR(255) UNIQUE
);
```

Create some "users"...
```sql
INSERT INTO user_info (name, surname, person_id, uuid)
VALUES
('Alain', 'Morisette', '123144789987', '4b72e0e6-ee1c-4494-8942-bfa47b444830'),
('james', 'brant', '1237865f4321', '317544d2-30cc-4b39-832a-152b91085e10'),
('Alain', 'Morisette', '1235765f8721', 'edcd6bbc-eece-4a06-936f-0f331d7d715b');
```




