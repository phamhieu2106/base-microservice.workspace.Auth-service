FROM openjdk:17-jdk-slim

# Copy file JAR từ thư mục com.base.build/libs/
COPY ./com.henry.build/libs/auth-query-1.0.0.jar /app/auth-query.jar

# Mở port cho service
EXPOSE 8003

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app/auth-query.jar"]
