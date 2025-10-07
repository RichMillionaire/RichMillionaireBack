# Architecture Hibernate Pure - DAO Pattern

## ✅ Structure mise en place

### 📁 Couche DAO (Data Access Object)

**Pattern utilisé : DAO avec Hibernate pur (pas de JPA, pas de Repository Spring Data)**

#### Interfaces DAO :
- `UserDao.java` - Interface pour les opérations sur les utilisateurs
- `RoleDao.java` - Interface pour les opérations sur les rôles

#### Implémentations Hibernate :
- `UserDaoJpa.java` - Implémentation Hibernate avec `SessionFactory`
- `RoleDaoJpa.java` - Implémentation Hibernate avec `SessionFactory`

### 🔧 Configuration Hibernate

**`HibernateConfig.java`**
- Crée le bean `SessionFactory` à partir de l'`EntityManagerFactory`
- Permet d'utiliser Hibernate directement sans passer par JPA

### 🎯 Fonctionnalités Hibernate utilisées

#### 1. **SessionFactory**
```java
private final SessionFactory sessionFactory;
private Session getSession() {
    return sessionFactory.getCurrentSession();
}
```
- Gère le pool de connexions
- Cache de niveau 1 (session)
- Cache de niveau 2 (optionnel)

#### 2. **HQL (Hibernate Query Language)**
```java
String hql = "SELECT DISTINCT u FROM UserEntity u LEFT JOIN FETCH u.roles WHERE u.username = :username";
Query<UserEntity> query = getSession().createQuery(hql, UserEntity.class);
```
- Requêtes orientées objet (pas SQL)
- Type-safe avec les génériques

#### 3. **JOIN FETCH**
```java
LEFT JOIN FETCH u.roles
```
- Charge les relations en une seule requête
- Évite le problème N+1
- Utilise `DISTINCT` pour éviter les doublons

#### 4. **Cache Hibernate**
```java
query.setCacheable(true);
```
- Active le cache de requête
- Améliore les performances pour les requêtes répétées

#### 5. **Opérations CRUD optimisées**
```java
// INSERT
session.persist(user);

// UPDATE
session.merge(user);

// SELECT par ID (utilise le cache L1)
session.get(RoleEntity.class, id);

// Requête unique avec Optional
query.uniqueResultOptional();
```

### 📊 Avantages de cette architecture

✅ **Hibernate pur** - Accès direct à toutes les fonctionnalités Hibernate
✅ **Performance optimale** - Cache, lazy loading, batch updates
✅ **Flexibilité** - Contrôle total sur les requêtes et le comportement
✅ **DAO Pattern** - Séparation claire des responsabilités (pas de logique métier dans les DAO)
✅ **Testabilité** - Interfaces facilement mockables

### 🔄 Gestion des transactions

```java
@Transactional // Spring gère les transactions
public class UserDaoJpa implements UserDao {
    // Les transactions sont automatiquement ouvertes/fermées
}
```

### 📝 Application.yml

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
```

### 🗑️ Ce qui a été supprimé

❌ **Repositories Spring Data JPA** (`UserRepository`, `RoleRepository`)
❌ **Dépendance aux repositories** - Tout passe par les DAO
❌ **Abstraction JPA** - On utilise Hibernate directement

### 🎯 Architecture finale

```
Controller
    ↓
Service (logique métier)
    ↓
DAO (accès données - logique repository intégrée)
    ↓
Hibernate SessionFactory
    ↓
PostgreSQL
```

### 💡 Exemple d'utilisation

```java
@Service
public class AuthServiceImpl {
    private final UserDao userDao;
    private final RoleDao roleDao;
    
    public void register(RegisterRequest request) {
        // Vérifier si l'utilisateur existe
        if (userDao.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // Récupérer le rôle par défaut
        RoleEntity role = roleDao.findByName("ROLE_USER")
            .orElseThrow(() -> new RuntimeException("Role not found"));
        
        // Créer et sauvegarder l'utilisateur
        UserEntity user = UserEntity.builder()
            .username(request.getUsername())
            .roles(Set.of(role))
            .build();
        
        userDao.save(user); // Hibernate gère l'INSERT
    }
}
```

## 🚀 Démarrage

1. Assurez-vous que PostgreSQL est démarré
2. Lancez l'application depuis IntelliJ
3. Hibernate se connectera à la base via le `SessionFactory`

Votre application utilise maintenant **Hibernate pur avec le pattern DAO** ! 🎉

