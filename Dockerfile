FROM maven:3.6.3-jdk-11
COPY ./ ./
RUN mvn clean install -T1C
CMD ["java","-jar","target/test-app.jar"]