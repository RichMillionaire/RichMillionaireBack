
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
spring.datasource.username=postgres
spring.datasource.password=Ekaterinburg
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
<<<<<<< HEAD

app.richmillionaire.machine-wallet-public-key=MIIDQjCCAjUGByqGSM44BAEwggIoAoIBAQCPeTXZuarpv6vtiHrPSVG28y7FnjuvNxjo6sSWHz79NgbnQ1GpxBgzObgJ58KuHFObp0dbhdARrbi0eYd1SYRpXKwOjxSzNggooi/6JxEKPWKpk0U0CaD+aWxGWPhL3SCBnDcJoBBXsZWtzQAjPbpUhLYpH51kjviDRIZ3l5zsBLQ0pqwudemYXeI9sCkvwRGMn/qdgYHnM423krcw17njSVkvaAmYchU5Feo9a4tGU8YzRY+AOzKkwuDycpAlbk4/ijsIOKHEUOThjBopo33fXqFD3ktm/wSQPtXPFiPhWNSHxgjpfyEc2B3KI8tuOAdl+CLjQr5ITAV2OTlgHNZnAh0AuvaWpoV499/e5/pnyXfHhe8ysjO65YDAvNVpXQKCAQAWplxYIEhQcE51AqOXVwQNNNo6NHjBVNTkpcAtJC7gT5bmHkvQkEq9rI837rHgnzGC0jyQQ8tkL4gAQWDt+coJsyB2p5wypifyRz6Rh5uixOdEvSCBVEy1W4AsNo0fqD7UielOD6BojjJCilx4xHjGjQUntxyaOrsLC+EsRGiWOefTznTbEBplqiuH9kxoJts+xy9LVZmDS7TtsC98kOmkltOlXVNb6/xF1PYZ9j897buHOSXC8iTgdzEpbaiH7B5HSPh++1/et1SEMWsiMt7lU92vAhErDR8C2jCXMiT+J67ai51LKSLZuovjntnhA6Y8UoELxoi34u1DFuHvF9veA4IBBQACggEAYhGjZIcxp1dYradeK/E/fLsdS4cDoRsIW6yoiJGLZlHk60cN4gxGcP+ZZbb7cCr6U/RiO9s8kmx0BBbx3D8+AqKrqhM4tXZcqDE/8b5XkjEFZ5GnFjTxhai2f1bCB4EJSBcB80AElB4FH3bO2zup/Te4LwJR1Km8N3HuEknkmWgFYAf8cpNqis6koMYeEx9Oi17Dm5yhMKHbvBv2GuOU+vqHrA1E6y7UHuG4sDVBCa6nt6Kr+enBRE+MDHbcFbnGAHJDhyrEF63Zcb/SwrugRvzpyEuCQv0bzH3tCWhkGuQBPC4KDnDf4c2peEVglygK5gCR9zZttBfYN642uU8EpQ==
=======
app.richmillionaire.machine-wallet-public-key=MIIDQjCCAjUGByqGSM44BAEwggIoAoIBAQCPeTXZuarpv6vtiHrPSVG28y7FnjuvNxjo6sSWHz79NgbnQ1GpxBgzObgJ58KuHFObp0dbhdARrbi0eYd1SYRpXKwOjxSzNggooi/6JxEKPWKpk0U0CaD+aWxGWPhL3SCBnDcJoBBXsZWtzQAjPbpUhLYpH51kjviDRIZ3l5zsBLQ0pqwudemYXeI9sCkvwRGMn/qdgYHnM423krcw17njSVkvaAmYchU5Feo9a4tGU8YzRY+AOzKkwuDycpAlbk4/ijsIOKHEUOThjBopo33fXqFD3ktm/wSQPtXPFiPhWNSHxgjpfyEc2B3KI8tuOAdl+CLjQr5ITAV2OTlgHNZnAh0AuvaWpoV499/e5/pnyXfHhe8ysjO65YDAvNVpXQKCAQAWplxYIEhQcE51AqOXVwQNNNo6NHjBVNTkpcAtJC7gT5bmHkvQkEq9rI837rHgnzGC0jyQQ8tkL4gAQWDt+coJsyB2p5wypifyRz6Rh5uixOdEvSCBVEy1W4AsNo0fqD7UielOD6BojjJCilx4xHjGjQUntxyaOrsLC+EsRGiWOefTznTbEBplqiuH9kxoJts+xy9LVZmDS7TtsC98kOmkltOlXVNb6/xF1PYZ9j897buHOSXC8iTgdzEpbaiH7B5HSPh++1/et1SEMWsiMt7lU92vAhErDR8C2jCXMiT+J67ai51LKSLZuovjntnhA6Y8UoELxoi34u1DFuHvF9veA4IBBQACggEAU87uUyyKiBIktAq6FsSnZyWGRM/s9yGXcADYBDEKFzlZsCtYMbF4WKsyUs3XieJ4VHKjS1CybSbbgSh1KJue4qvVlYz6mwHHGvvaG0rclLRWwuKNbE4p1ZqzP0LGyevN2LTDlSMKxMABENDlugAtjruUBdlU8BKqXz7h11R4QQqkNzy+oTt7A0fjq/qvaIfDiOvQbqjq7RZRNjUHrt9+NYM6YZccU0rEh7nwCfyafpIUlIWI+VdGXxNS5dl2mZwTiPX7W1pXDcAkPTL8pUnX9UvO/AHbZkgyIjedLIBXZfv3/VSRqxoxzyftRmndXaWWoazyh1geAAmWkRQZs+Af3w==
>>>>>>> b0569c5f9909c7e2ecf40838eebf5dfa7538c070
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
