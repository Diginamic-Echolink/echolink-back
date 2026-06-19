# Project groupe *<u>Echolink</u>*  🌦️


## Description 📋

**Résumer** 📝 : **Echolink** est un projet collaboratif développé autour d'une **architecture hexagonale
(Ports & Adapters)** afin de garantir une séparation claire des responsabilités entre le domaine métier et les couches 
techniques. Cette approche favorise la maintenabilité, la testabilité et l'évolutivité de l'application tout en limitant
les dépendances entre les différents composants.

Ce dépôt regroupe l'ensemble du code source et de la documentation du projet.


## Prérequis ✅

- **Version Java** : Java 25.
- **Conteneurisation** : Docker et Docker Compose.
- **Gestionnaire de dépendances** : Apache Maven 3.9+.
- **Base de données** : MariaDB 11.6 (déployée via Docker Compose).
- **Dépendances principales** ⚙️ :
  - `spring-boot-starter-web` : exposition de l'API REST.
  - `spring-boot-starter-data-jpa` : persistance des données avec JPA/Hibernate.
  - `spring-boot-starter-security` : gestion de la sécurité et de l'authentification.
  - `spring-boot-starter-oauth2-resource-server` : sécurisation des ressources via OAuth2/JWT.
  - `spring-boot-starter-validation` : validation des données.
  - `spring-boot-starter-flyway` : gestion des migrations de base de données.
  - `spring-boot-starter-webmvc-ui` : documentation de l'API avec Swagger/OpenAPI.
  - `lombok` : réduction du code boilerplate.
  - `jacoco` : mesure de la couverture des tests.
  - `spotbugs` : analyse statique du code.


## Détails des fonctionnalités implémentées 🚧

<details>
  <summary><strong>Contrôleurs REST</strong> 🧭</summary>

  Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/*/in/`

  Les contrôleurs REST constituent les points d'entrée de l'application. Dans l'architecture hexagonale,
  ils jouent le rôle d'adaptateurs entrants en traduisant les requêtes HTTP vers les cas d'utilisation métier.
  
  - `AuthController`
    - Base d'URL : `/api/v1/auth`
    - Endpoints clés :
      - `POST /register`
        - Création d'un compte utilisateur.
      - `POST /login`
        - Authentification et génération d'un token JWT.
        
  - `ProfileController`
    - Base d'URL : `/api/v1/profile`
    - Endpoints clés :
      - `GET /me`
        - Récupération du profil authentifié.
      - `GET /{profileId}`
        - Consultation d'un profil.
      - `GET /all`
        - Liste des profils (administrateur uniquement).
      - `PUT /{profileId}`
        - Mise à jour d'un profil.
      - `DELETE /{profileId}`
        - Suppression d'un profil.
  
  - `SectionController`
    - Base d'URL : `/api/v1/section`
    - Endpoints clés :
      - `GET /all`
         - Liste des sections du forum.
      - `POST /`
        - Création d'une section.
      - `PUT /{sectionId}`
        - Modification d'une section.
      - `DELETE /{sectionId}`
        - Suppression d'une section.
  
  - `ThreadController`
    - Base d'URL : `/api/v1/thread`
    - Endpoints clés :
      - `GET /all/{sectionId}`
        - Liste des discussions d'une section.
      - `POST /`
        - Création d'une discussion.
      - `PUT /{threadId}`
        - Modification d'une discussion.
      - `DELETE /{threadId}`
        - Suppression d'une discussion.
  
  - `MessageController`
    - Base d'URL : `/api/v1/message`
    - Endpoints clés :
      - `GET /all/{threadId}`
        - Liste des messages d'une discussion.
      - `POST /`
        - Publication d'un message.
      - `PUT /{messageId}`
        - Modification d'un message.
      - `DELETE /{messageId}`
        - Suppression logique d'un message.
  
  - `LocationController`
    - Base d'URL : `/api/v1/location`
    - Endpoints clés :
      - `GET /{locationId}`
        - Consultation d'une localisation.
      - `GET /search`
        - Recherche par nom.
      - `GET /all`
        - Liste des localisations.
      - `GET /geo/{latitude}/{longitude}/{delta}`
        - Recherche géographique par coordonnées.
  
  - `MeteoController`
    - Base d'URL : `/api/v1/meteo`
    - Endpoints clés :
      - `GET /{locationId}`
        - Consultation des dernières données météorologiques.
      - `GET /all/{locationId}`
        - Historique complet des données météo.
  
  - `AirQualityController`
    - Base d'URL : `/api/v1/air-quality`
    - Endpoints clés :
      - `GET /{locationId}`
        - Consultation de la dernière mesure de qualité de l'air.
      - `GET /all/{locationId}`
        - Historique complet des mesures de qualité de l'air.
  
  *Les accès aux différentes ressources sont sécurisés par 
  authentification JWT et contrôlés via des rôles (`ADMIN`, `USER`)*.
</details>

<details>
  <summary><strong>UseCases (Ports In)</strong> 🔌</summary>

  Répertoire : `src/main/java/fr/diginamic/echolink/application/*/port/in/`

  Les Use Cases regroupent les différentes fonctionnalités métier de l'application. Ils peuvent être appelés 
  par les contrôleurs REST ou par des tâches planifiées et constituent les ports d'entrée (Ports In) 
  de l'architecture hexagonale.

  Les principaux domaines fonctionnels couverts sont :
  
  - `Authentification`
    - Inscription des utilisateurs.
    - Authentification et génération de jetons JWT.
  
  - `Profils`
    - Consultation des profils.
    - Modification des informations utilisateur.
    - Suppression de profils.
  
  - `Forum`
    - Gestion des sections de discussion.
    - Création et administration des discussions.
    - Publication et gestion des messages.
  
  - `Localisations`
    - Recherche de localisations.
    - Recherche géographique par coordonnées.
  
  - `Données environnementales`
    - Consultation des données météorologiques.
    - Consultation des données de qualité de l'air.

  - `Intégration avec les API externes`
    - Synchronisation des localisations.
    - Récupération et mise à jour des données météorologiques.
    - Récupération et mise à jour des données de qualité de l'air.
</details>

<details>
  <summary><strong>Services</strong> <i>(logique Métier)</i> 🔧</summary>

  Répertoire : `src/main/java/fr/diginamic/echolink/application/*/service/`

  Les services implémentent les différents Use Cases de l'application. Ils centralisent les règles métier, 
  appliquent les validations nécessaires et orchestrent les échanges avec les ports sortants de 
  l'architecture hexagonale.

  Les principaux services couvrent les domaines fonctionnels suivants :

  - `Authentification`
    - Gestion de l'inscription des utilisateurs.
    - Vérification des identifiants.
    - Génération des jetons JWT.
  
  - `Profils`
    - Consultation et mise à jour des profils.
    - Contrôle des autorisations associées aux utilisateurs.

  - `Sections, Threads et Messages`
    - Consultation des sections, threads et messages.
    - Création des sections, threads et messages.
    - Modification et suppression des sections, threads et messages.
    
  - `Localisations`
    - Recherche et consultation des localisations.
    - Traitements liés aux recherches géographiques.
    
  - `Météo et qualité de l'air`
    - Consultation des données météorologiques.
    - Consultation des données de qualité de l'air.
 
  - `Intégration avec les API externes`
    - Synchronisation des localisations.
    - Mise à jour des données météorologiques.
    - Mise à jour des données de qualité de l'air.
</details>


## Architecture du projet 🏗️

<details>
    <summary><strong>Domaine Métier</strong> 🏛️</summary>

Répertoire : `src/main/java/fr/diginamic/echolink/domain/`

Le domaine métier regroupe les objets principaux manipulés par l'application.
Dans l'architecture hexagonale, cette couche représente le cœur du projet et reste indépendante
des détails techniques comme les contrôleurs REST, la persistance ou les API externes.

Les principaux objets métiers sont:
    
- `Profile` 
  - Représente un utilisateur.
  - Contient les informations liées au compte et aux droits associés.

- `Section`
  - Représente une section permettant d'organiser les threads de discussion.
  - La section est rattachée à un profil.

- `Thread`
  - Représente un sujet de discussion.
  - Le thread appartient à une section.

- `Message`
  - Représente un message publié 
  - Le message appartient à un thread.

- `Location`
  - Représente une localisation géographique suivie par l'application 
  - Sert de point de référence pour les données météo et qualité de l'air.

- `Meteo`
  - Représente les données météorologiques associées à une localisation
  
- `AirQuality`
  - Représente les données de qualité de l'air associées à une localisation

Le domaine contient également des objets de demande utilisés par les cas d'utilisation, tels que, 
`MessageCreateRequest`, `MessageUpdateRequest`, `ThreadCreateRequest`, `ThreadUpdateRequest` ou `ProfileUpdateRequest`,
permettant de transporter les informations nécessaires à l'exécution des traitements métier.
</details>

<details>
    <summary><strong>Ports Out</strong> <i>(Repositories & Providers)</i> 🔄</summary>

Répertoire : `src/main/java/fr/diginamic/echolink/application/*/port/out/`

Les ports sortants définissent les opérations dont les services ont besoin pour accéder aux 
données persistées et aux services externes.
<br>Leur utilisation sous forme d'interfaces permet à la couche applicative de rester indépendante des implémentations 
techniques utilisées par l'infrastructure.

Les principaux ports sortants sont regroupés en deux catégories :

- `Repositories`
    - Accès et gestion des données métier de l'application. 
    - Assurent la persistance et la récupération des objets du domaine.
    - Servent d'intermédiaires entre les services et les adaptateurs chargés de la persistance des données.

- `Providers`
    - Génération des jetons d'authentification JWT.
    - Récupération des localisations via une API externe.
    - Récupération des données météorologiques via une API externe.
    - Récupération des données de qualité de l'air via une API externe.
</details>

<details>
    <summary><strong>Adaptateurs Out</strong> <i>(Persistence)</i> 💾</summary>

Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/*/out/persistence/`

Les adaptateurs sortants de persistance implémentent les ports sortants de type `Repository`.
Ils assurent la communication entre la couche applicative et la base de données en s'appuyant sur Spring Data JPA. 

Deux types de composants sont utilisés :

- `RepositoryAdapter`
  - Implémentent les interfaces Repository définies dans les Ports Out.
  - Traduisent les besoins de la couche applicative vers les composants de persistance.
  - Délèguent les opérations de persistance aux repositories Spring Data JPA.

- `JdbcRepository`
  - Étendent JpaRepository pour accéder aux données persistées. 
  - Définissent les requêtes spécifiques nécessaires à certains traitements. 
  - Fournissent les opérations techniques de lecture, sauvegarde et suppression.
</details>

<details>
    <summary><strong>Adaptateurs Out</strong> <i>(API Externes)</i> 🌐</summary>

Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/*/out/api/`

Les adaptateurs sortants dédiés aux API externes implémentent les ports sortants de type Provider.
Ils assurent la communication entre la couche applicative et les services tiers au moyen de clients HTTP configurés.

Deux types de composants sont utilisés :

- Implémentations des Providers
  - Implémentent les interfaces Provider définies dans les ports sortants. 
  - Réalisent les appels HTTP vers les API externes. 
  - Délèguent la transformation des réponses reçues aux composants dédiés.
  
   
- RestClient 
  - Configurés pour communiquer avec les différentes API utilisées par l'application. 
  - Gèrent les échanges HTTP ainsi que les paramètres de connexion.
</details>


## Composants techniques 🧩

<details>
    <summary><strong>DTOs</strong> 📦</summary>

Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/*/*/dto/`

- `DTOs exposés par l'API REST`
  - Utilisés par les contrôleurs pour structurer les réponses envoyées aux clients.
  - <u>Exemple</u>: *ProfileQuery*, *ThreadQuery*, *MessageQuery*, *MeteoQuery*, *AirQualityQuery*.

- `DTOs d'API externes`
  - Représentent les structures de réponses retournées par les services externes.
  - <u>Exemple</u>: *GeoApiCommuneDto*, *GeoApiLocationCentreDto*, *OpenMeteoWeatherResponse*, 
    *OpenMeteoAirQualityResponse*.
</details>

<details>
    <summary><strong>Mappers</strong> 🔄</summary>

Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/*/*/mapper/`

Les mappers assurent la conversion entre les objets du domaine et les DTOs. 
Ils permettent de séparer les modèles métier des formats utilisés pour les échanges avec les clients ou 
les services externes.

Les principaux mappers sont utilisés pour :

- Convertir les objets du domaine vers les DTOs exposés par l'API REST.
- Convertir les données issues des API externes vers les objets du domaine.

<u>Exemples</u> : *ProfileQueryMapper*, *ThreadQueryMapper*, *MessageQueryMapper*, *GeoApiCommuneMapper*, 
*OpenMeteoWeatherResponseMapper*, *OpenMeteoAirQualityResponseMapper*.
</details>


## Configuration technique ⚙️

Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/common/configuration/`<br>
Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/common/out/`

La configuration technique regroupe les composants permettant d'assurer la sécurité de l'application, 
la communication avec les services externes ainsi que la génération de la documentation Swagger de l'API.

Les principaux éléments de configuration sont :

- `Sécurité`
  - Configuration des règles d'accès aux ressources de l'application. 
  - Gestion de l'authentification par jetons JWT. 
  - Contrôle des autorisations associées aux rôles **ADMIN** et **USER**. 
  - Chiffrement des mots de passe à l'aide de BCrypt.

- `JWT`
  - Configuration de la clé secrète utilisée pour signer et valider les jetons.
  - Génération de jetons contenant les informations d'identification et les rôles des utilisateurs.
  - Mise à disposition des composants nécessaires à la génération et à la vérification des JWT.

- `RestClient`
  - Configuration des clients HTTP utilisés pour communiquer avec les API externes.
  - Gestion des URLs de base, des délais de connexion et des délais de lecture.

- `OpenAPI`
  - Génération automatique de la documentation Swagger. 
  - Documentation des points d'entrée de l'application. 
  - Intégration de l'authentification Bearer JWT dans l'interface Swagger.


## Base de données 🗄️

Répertoire : `src/main/resources/db/migration/`

Les données de l'application sont persistées dans une base MariaDB à l'aide de Spring Data JPA et Hibernate.<br>

Les objets du domaine sont mappés sur les tables de la base de données au moyen des annotations JPA 
telles que `@Entity`, `@Table`, `@Id`, `@ManyToOne` ou `@OneToMany`.<br>

La base de données est déployée au sein de l'environnement Docker utilisé par le projet.


## Traitements automatisés ⏱️

Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/*/in/`

Les tâches planifiées permettent de maintenir à jour les données exploitées par l'application.

Les principales synchronisations concernent :

- Les localisations ;
- Les données météorologiques ;
- Les mesures de qualité de l'air.

Ces traitements s'appuient sur des Use Cases dédiés et 
sur les adaptateurs chargés de communiquer avec les API externes.

## Gestion des erreurs 🚨

Répertoire : `src/main/java/fr/diginamic/echolink/domain/*/exception/`<br>
Répertoire : `src/main/java/fr/diginamic/echolink/infrastructure/common/in/dto/`

L'application utilise des exceptions métier dédiées afin de signaler les erreurs fonctionnelles et 
les échecs rencontrés lors des traitements ou des synchronisations.

Les réponses d'erreur exposées par l'API sont standardisées au moyen de DTOs dédiés.


## Documentation API 📚

La documentation interactive de l'API est générée automatiquement grâce à OpenAPI et SpringDoc.

Une fois l'application démarrée, elle est accessible à l'adresse :

- `http://localhost:8080/swagger-ui/index.html`

Elle permet de consulter les endpoints disponibles, d'explorer les modèles exposés par l'API 
et de tester les opérations nécessitant une authentification JWT.


## Tests 🧪

Répertoire : `src/test/java/fr/diginamic/echolink/`

Le projet intègre des tests automatisés afin de vérifier le comportement des services métier et 
des traitements de synchronisation.

Les tests s'appuient principalement sur :

- `JUnit`
    - Exécution des scénarios de tests unitaires.

- `Mockito`
    - Isolation des dépendances via des mocks.

- `AssertJ`
    - Vérification lisible des résultats attendus.

- `TestData`
    - Mise à disposition de jeux de données réutilisables pour les tests.

Les tests couvrent notamment l'authentification, la consultation des données de qualité de l'air 
ainsi que les traitements de synchronisation avec les API externes.

L'intégration continue est assurée via GitHub Actions avec des workflows dédiés aux tests, à la couverture de code, 
à l'analyse SpotBugs et aux rapports associés.


## Intégration Continue 🤖

Répertoire : `.github/workflows/`

Le projet utilise GitHub Actions afin d'automatiser certaines vérifications lors des évolutions du code.

Les principaux workflows sont :

- `build_test.yaml`
    - Compilation du projet et exécution des tests automatisés.

- `jacoco_report.yaml`
    - Génération des rapports de couverture de code avec JaCoCo.

- `spotbugs_report.yaml`
    - Analyse statique du code avec SpotBugs.

- `codeql_report.yaml`
    - Analyse de sécurité et de qualité du code avec CodeQL.


## Contributeurs 👥

- Axel Hayalian (AzraKaynAxel)
- Romane Bai (Romanebai)
- Baptiste Gérardin (Velki0)

Projet développé dans le cadre d'un projet académique autour de la conception d'une application basée
sur une architecture hexagonale (Ports & Adapters).



