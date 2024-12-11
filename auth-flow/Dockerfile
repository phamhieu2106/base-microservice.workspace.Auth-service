FROM openjdk:17-jdk-slim

# Copy file JAR từ thư mục build/libs/
COPY ./build/libs/auth-flow-1.0.0.jar /app/auth-flow.jar

# Mở port cho service
EXPOSE 8002

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/auth-flow.jar"]
