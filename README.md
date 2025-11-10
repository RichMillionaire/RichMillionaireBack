
## RichMillionaire - Projet Spring Boot

⚡ Démarrage du projet

1. Lancer le serveur par défaut :
```bash
mvn spring-boot:run
```

2. Lancer avec un profil spécifique (`local`) :
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

> Cela permet d’utiliser le fichier application-local.properties pour la configuration locale.

🛠 Configuration locale (PostgreSQL)

Fichier : src/main/resources/application-local.properties
```
spring.application.name=richmillionaire

spring.datasource.url=jdbc:postgresql://localhost:5432/richmillionaire
spring.datasource.username=USER
spring.datasource.password=MDP
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

💡 Conseils :
- Remplacez USER et MDP par vos identifiants PostgreSQL.
- `spring.jpa.hibernate.ddl-auto=update` met à jour la base automatiquement (pratique pour le développement, à éviter en production).
- Pour un rechargement automatique du serveur à chaque changement de code, vous pouvez ajouter Spring Boot DevTools dans le pom.xml :

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

---

🐳 Lancer le projet avec Docker

1. Assurez-vous que le fichier `application-docker.properties` est configuré correctement pour Docker :
```
spring.datasource.url=jdbc:postgresql://database:5432/richmillionaire
spring.datasource.username=USER
spring.datasource.password=MDP
```
- `database` est le nom du service PostgreSQL dans Docker Compose.

2. Construisez et lancez les containers :
```bash
docker compose up --build
```

3. Vérifiez que le backend et la base de données sont bien lancés :
```bash
docker ps
```

💡 Astuce :
- Si vous souhaitez réinjecter le dump SQL, supprimez le volume Docker lié à la base de données avant de relancer :
```bash
docker compose down -v
```
