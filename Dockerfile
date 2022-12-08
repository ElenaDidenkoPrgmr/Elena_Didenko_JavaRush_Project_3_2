FROM maven:3.8.3-openjdk-17

WORKDIR /opt

COPY . .

RUN mvn clean package

EXPOSE 8080

CMD ["java", "-jar" , "./target/embeddedTomcatQuest-jar-with-dependencies.jar"]
