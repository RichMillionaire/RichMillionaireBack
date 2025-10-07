RichMillionaire - Projet Spring Boot

⚡ Démarrage du projet

1. Lancer le serveur par défaut
mvn spring-boot:run

2. Lancer avec un profil spécifique (`local`)
mvn spring-boot:run -Dspring-boot.run.profiles=local

> Cela permet d’utiliser le fichier application-local.properties pour la configuration locale.

🛠 Configuration locale (PostgreSQL)

Fichier : src/main/resources/application-local.properties

# Nom de l'application
spring.application.name=richmillionaire

# -------------------
# PostgreSQL DB
# -------------------
spring.datasource.url=jdbc:postgresql://localhost:5432/richmillionaire
spring.datasource.username=USER
spring.datasource.password=MDP
spring.datasource.driver-class-name=org.postgresql.Driver

# -------------------
# Hibernate / JPA
# -------------------
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

💡 Conseils :
- Remplace USER et MDP par vos identifiants PostgreSQL.
- spring.jpa.hibernate.ddl-auto=update met à jour la base automatiquement (pratique pour dev, à éviter en prod).
- Pour un reload automatique du serveur à chaque changement, ajoutez spring-boot-devtools dans le pom.xml :

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>

🔧 Astuces dev

- Avec DevTools, le serveur se relance automatiquement dès que tu modifies le code.
- Pour vérifier que tout fonctionne, regarde la console : le serveur doit indiquer le port et l'adresse sur lesquels il tourne.
- Profite du mode local pour tester sans toucher la config de prod.
