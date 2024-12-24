FROM openjdk:17-jdk-slim

# Copy file JAR từ thư mục com.henry.build/libs/
COPY ./com.henry.build/libs/auth-command-1.0.0.jar /app/auth-command.jar

# Mở port cho service
EXPOSE 8001

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/auth-command.jar"]
