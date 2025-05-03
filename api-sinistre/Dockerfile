# Étape 1 : Utiliser une image Java 17
FROM eclipse-temurin:17-jdk

# Étape 2 : Copier le fichier jar compilé
COPY target/*.jar app.jar

# Étape 3 : Lancer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Port exposé
EXPOSE 80
