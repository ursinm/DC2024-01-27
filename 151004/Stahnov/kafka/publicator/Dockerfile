FROM openjdk:17-jdk-alpine

WORKDIR /app

# Копирование JAR-файла в контейнер
COPY build/libs/publicator-0.0.1-SNAPSHOT.jar publicator.jar

# Команда для запуска JAR-файла
CMD ["java", "-jar", "publicator.jar"]