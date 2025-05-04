# Usa una imagen oficial de Java para ejecutar la app
FROM openjdk:17-jdk-slim

# Crea un directorio en el contenedor
WORKDIR /app

# Copia el jar compilado al contenedor
COPY target/stater-0.0.1-SNAPSHOT.jar app.jar

# Expón el puerto que usará la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
