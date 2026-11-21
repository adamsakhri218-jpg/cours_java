# Activité 2 - Spring Boot et Spring Data JPA

## Description

Ce projet a pour objectif de découvrir la persistance des données avec **Spring Boot**, **Spring Data JPA** et une base de données **H2**.

L’application gère une entité `Product` et expose plusieurs opérations REST pour ajouter, consulter, rechercher et supprimer des produits.

Ce dépôt contient le **code original du TP, sans modification**. La mise à jour d’un produit (`PUT`) fait partie de l’énoncé, mais elle n’est pas implémentée dans la version actuelle.

## Technologies utilisées

- Java 21
- Spring Boot
- Spring Web (Web MVC)
- Spring Data JPA
- H2 Database
- Lombok
- Maven
- Springdoc OpenAPI (dépendance présente dans le projet)

---

# Architecture du projet

```text
src/main/
├── java/org/example/demobddccspringdata/
│   ├── DemoBddccSpringDataApplication.java
│   ├── entities/
│   │   └── Product.java
│   ├── repository/
│   │   └── ProductRepository.java
│   └── web/
│       └── ProductRestController.java
└── resources/
    └── application.properties
```

Le projet est organisé en trois niveaux : **entité**, **repository** et **contrôleur REST**.

---

# 1. Création du projet Spring Initializr

Le projet Maven utilise les dépendances Spring Data JPA, Spring Web, H2 et Lombok. Les dépendances exactes figurent dans le fichier [`pom.xml`](pom.xml).

### Capture du projet dans IntelliJ

> Insérer ici une capture d’écran réelle du projet ouvert dans IntelliJ, dans le dossier `images/`.

---

# 2. Entité JPA Product

L’entité `Product` est enregistrée dans la table `PRODUCTS` et possède quatre attributs :

| Attribut | Type | Rôle |
|---|---|---|
| `id` | `Long` | Identifiant généré automatiquement |
| `name` | `String` | Nom du produit |
| `price` | `double` | Prix du produit |
| `quantity` | `int` | Quantité du produit |

La classe utilise `@Entity`, `@Table`, `@Id`, `@GeneratedValue` ainsi que les annotations Lombok (`@Getter`, `@Setter`, `@Builder`, etc.).

Code source : [`Product.java`](src/main/java/org/example/demobddccspringdata/entities/Product.java).

---

# 3. Configuration de H2

La configuration actuelle, dans `application.properties`, est :

```properties
spring.application.name=demo-bddcc-spring-data
spring.datasource.url=jdbc:h2:mem:products-db
spring.h2.console.enabled=true
server.port=8084
```

H2 fonctionne ici en mémoire : les données sont perdues après l’arrêt de l’application.

Fichier : [`application.properties`](src/main/resources/application.properties).

### Capture de la console H2

> Ajouter une capture réelle de la console H2, si elle a été testée.

---

# 4. Repository Spring Data JPA

`ProductRepository` hérite de `JpaRepository<Product, Long>` :

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContains(String kw);
}
```

Cette interface offre des méthodes standard telles que `save()`, `findAll()`, `findById()` et `deleteById()`. La méthode `findByNameContains()` permet de chercher des produits à partir d’une partie du nom.

Fichier : [`ProductRepository.java`](src/main/java/org/example/demobddccspringdata/repository/ProductRepository.java).

---

# 5. Initialisation des données

Au démarrage, la classe principale ajoute trois exemples :

| Nom | Prix | Quantité |
|---|---:|---:|
| Computer | 3400 | 2 |
| Printer | 1200 | 13 |
| Smartphone | 1300 | 4 |

Elle affiche ensuite la liste des produits dans la console avec `findAll()`.

Fichier : [`DemoBddccSpringDataApplication.java`](src/main/java/org/example/demobddccspringdata/DemoBddccSpringDataApplication.java).

### Capture de la console

> Ajouter une capture réelle des produits affichés au démarrage.

---

# 6. Opérations REST sur les produits

Le contrôleur `ProductRestController` expose les routes suivantes :

| Opération | Méthode HTTP | Route | Disponible dans le code |
|---|---|---|---|
| Consulter tous les produits | GET | `/products` | Oui |
| Consulter un produit | GET | `/products/{id}` | Oui |
| Chercher par nom | GET | `/find/products?kw=Computer` | Oui |
| Ajouter un produit | POST | `/products` | Oui |
| Supprimer un produit | DELETE | `/products/{id}` | Oui |
| Mettre à jour un produit | PUT | `/products/{id}` | **Non implémenté** |

Le port défini dans le projet est **8084**. Exemple d’URL : `http://localhost:8084/products`.

### Exemple de corps JSON pour ajouter un produit

```json
{
  "name": "Keyboard",
  "price": 250,
  "quantity": 5
}
```

### Captures des tests REST

> Ajouter ici les captures d’écran réelles de Postman, du navigateur ou d’un autre outil, après avoir effectué les tests. Aucune capture de test n’a été fournie dans les archives.

---

# 7. Exécution

1. Ouvrir le dossier `activite-2-spring-data-jpa` dans IntelliJ IDEA.
2. Vérifier que Java 21 et Maven sont disponibles.
3. Exécuter `DemoBddccSpringDataApplication`.
4. Accéder à `http://localhost:8084/products` pour consulter les données.

Sur macOS ou Linux, le Maven Wrapper inclus peut également être utilisé :

```bash
./mvnw spring-boot:run
```

Sur Windows :

```bat
mvnw.cmd spring-boot:run
```

---

# Conclusion

Cette activité illustre l’utilisation de Spring Data JPA avec H2 pour gérer les produits via des endpoints REST. L’ajout, la consultation, la recherche et la suppression sont présents dans le code source. La fonctionnalité PUT reste à réaliser si elle est demandée pour une version complète du TP.
