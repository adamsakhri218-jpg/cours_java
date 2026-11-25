# Activité 3 - Spring MVC, Thymeleaf et Spring Security

## Description

Ce TP consiste à développer une application web de gestion des produits avec Spring Boot. L'application permet de consulter, ajouter et supprimer des produits, avec une interface Thymeleaf et une gestion des accès selon les rôles USER et ADMIN.

Le projet suit le cours de Mohamed Youssfi : [vidéo du TP](https://www.youtube.com/watch?v=FHy7raIldgg) et [code source du professeur](https://github.com/mohamedYoussfi/all-in-one-spring-mvc-thymeleaf-spring-security).

## Technologies utilisées

- Java 21 et Maven
- Spring Boot 4.2.0
- Spring MVC et Spring Data JPA
- Spring Security et BCrypt
- Thymeleaf et Thymeleaf Layout Dialect
- Bootstrap 5.3.5 (WebJars)
- H2 Database
- Jakarta Validation et Lombok

---

# Architecture du projet

```text
src/main/
├── java/org/example/bdccmundiaspringmvc/
│   ├── BdccMundiaSpringMvcApplication.java
│   ├── entities/
│   │   └── Product.java
│   ├── repository/
│   │   └── ProductRepository.java
│   ├── web/
│   │   └── ProductController.java
│   └── sec/
│       └── SecurityConfig.java
└── resources/
    ├── application.properties
    └── templates/
        ├── layout1.html
        ├── products.html
        ├── new-product.html
        ├── notAuthorized.html
        └── login.html
```

---

# 1. Création du projet et configuration de H2

Les dépendances du projet sont définies dans [`pom.xml`](pom.xml). La base H2 est configurée en mémoire dans [`application.properties`](src/main/resources/application.properties).

```properties
spring.application.name=bdcc-mundia-spring-mvc
spring.datasource.url=jdbc:h2:mem:produits-db
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
server.port=8094
spring.h2.console.enabled=true
```

Les données en mémoire ne sont pas conservées après l'arrêt de l'application.

### Capture du projet dans IntelliJ

<!-- Ajouter une capture réelle : ![Projet IntelliJ](images/projet.png) -->

---

# 2. Entité Product et accès aux données

L'entité Product représente les produits à gérer : identifiant, nom, prix et quantité. Le repository permet d'effectuer les opérations de persistance utilisées par le contrôleur.

- [Product.java](src/main/java/org/example/bdccmundiaspringmvc/entities/Product.java)
- [ProductRepository.java](src/main/java/org/example/bdccmundiaspringmvc/repository/ProductRepository.java)

# 3. Affichage des produits avec Spring MVC

Le contrôleur récupère les produits avec `findAll()` et transmet la liste à la vue `products.html` au moyen du modèle MVC.

La page `layout1.html` définit la structure commune des pages, notamment la barre de navigation. Le fragment `content1` accueille le contenu spécifique de chaque vue.

### Capture de la liste des produits

<!-- Ajouter : ![Liste des produits](images/products.png) -->

---

# 4. Ajout, validation et suppression des produits

La page `new-product.html` contient un formulaire avec les champs Name, Price et Quantity. La validation est effectuée avec `@Valid` et `BindingResult`. En cas d'erreur, le formulaire est réaffiché ; sinon, le produit est enregistré et l'utilisateur revient au formulaire d'ajout.

La suppression utilise une requête POST et redirige vers la liste des produits. Ces opérations sont réservées à ADMIN.

### Capture du formulaire d'ajout

<!-- Ajouter : ![Nouveau produit](images/new-product.png) -->

---

# 5. Authentification et gestion des rôles

La classe [`SecurityConfig.java`](src/main/java/org/example/bdccmundiaspringmvc/sec/SecurityConfig.java) configure l'authentification en mémoire et le hachage des mots de passe avec BCrypt.

| Utilisateur | Rôles |
|---|---|
| user1 | USER |
| user2 | USER |
| admin | USER, ADMIN |

Pour ce TP local, le mot de passe de démonstration est `1234` pour les trois comptes. Ces identifiants ne sont pas destinés à une application en production.

La configuration active la sécurité des méthodes avec `@EnableGlobalMethodSecurity(prePostEnabled = true)`. La dépendance `spring-security-access` permet de conserver cette annotation dans la version utilisée par le projet.

Les méthodes du contrôleur utilisent `@PreAuthorize` pour limiter l'accès selon les rôles. Thymeleaf utilise également `sec:authorize` pour masquer les actions administratives aux utilisateurs ordinaires.

| Fonctionnalité | USER | ADMIN |
|---|---|---|
| Consulter les produits | Oui | Oui |
| Ajouter un produit | Non | Oui |
| Supprimer un produit | Non | Oui |
| Se déconnecter | Oui | Oui |

### Captures des interfaces USER et ADMIN

<!-- Ajouter : ![Interface USER](images/user.png) -->
<!-- Ajouter : ![Interface ADMIN](images/admin.png) -->

---

# 6. Login, Logout et accès refusé

La page `login.html` propose un formulaire d'authentification personnalisé. Le nom de l'utilisateur connecté apparaît dans la barre de navigation.

Dans le cadre de ce TP, la méthode Logout du contrôleur invalide la session HTTP et affiche la page de connexion. La page `notAuthorized.html` affiche un message lorsqu'un utilisateur tente d'accéder à une fonctionnalité interdite.

### Captures des pages de sécurité

<!-- Ajouter : ![Authentification](images/login.png) -->
<!-- Ajouter : ![Accès refusé](images/not-authorized.png) -->
<!-- Ajouter : ![Déconnexion](images/logout.png) -->

---

# 7. Routes de l'application

| Fonctionnalité | Méthode HTTP | Route | Accès |
|---|---|---|---|
| Accueil | GET | `/` | Authentifié |
| Liste des produits | GET | `/user/index` | USER / ADMIN |
| Formulaire d'ajout | GET | `/admin/newProduct` | ADMIN |
| Enregistrement | POST | `/admin/saveProduct` | ADMIN |
| Suppression | POST | `/admin/delete?id=...` | ADMIN |
| Page Login | GET | `/login` | Public |
| Authentification | POST | `/login` | Public |
| Logout | GET | `/logout` | Authentifié |
| Accès refusé | GET | `/notAuthorized` | Authentifié |

Code source : [ProductController.java](src/main/java/org/example/bdccmundiaspringmvc/web/ProductController.java).

La modification d'un produit existant n'est pas proposée dans cette version du contrôleur.

---

# 8. Progression du TP

Le travail a été organisé en cinq parties de 30 minutes pour les commits Git :

| Partie | Intervalle | Travail |
|---|---|---|
| 1 | 00–30 min | Création du projet et affichage des produits |
| 2 | 30–60 min | Ajout et suppression |
| 3 | 60–90 min | Spring Security et rôles |
| 4 | 90–120 min | Restrictions d'accès, menu utilisateur et accès refusé |
| 5 | 120–150 min | Login personnalisé, Logout et sécurité des méthodes |

Ce tableau présente le découpage du travail du TP, et non un relevé exact des minutes de la vidéo.

---

# 9. Exécution

1. Ouvrir `activite-3-spring-mvc` dans IntelliJ IDEA.
2. Vérifier Java 21 et recharger les dépendances Maven.
3. Exécuter `BdccMundiaSpringMvcApplication`.
4. Accéder à [http://localhost:8094/login](http://localhost:8094/login).
5. Tester les accès avec les comptes USER et ADMIN.

Sur macOS ou Linux :

```bash
./mvnw spring-boot:run
```

Sur Windows :

```bat
mvnw.cmd spring-boot:run
```

---

# Conclusion

Ce TP permet de comprendre le fonctionnement d'une application MVC, la persistance des données avec Spring Data JPA, l'intégration des vues Thymeleaf et la sécurisation des fonctionnalités avec Spring Security. La distinction entre USER et ADMIN illustre le contrôle des accès aux opérations de gestion des produits.
