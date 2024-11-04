FROM amazoncorretto:17
ADD target/*.jar recargapay-wallet-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "recargapay-wallet-api.jar"]
