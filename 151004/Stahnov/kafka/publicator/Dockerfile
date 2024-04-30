FROM openjdk:17-jdk-alpine

WORKDIR /app

# Копирование JAR-файла в контейнер
COPY build/libs/*.jar publicator.jar

# Команда для запуска JAR-файла
CMD ["java", "-jar", "publicator.jar"]