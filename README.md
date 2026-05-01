
# BFF - Mobile Aggregator (Ktor & Clean Architecture)


## 🚀 Architecture
Le projet respecte les principes de la **Clean Architecture** :
- **Domain** : Logique métier et entités pures.
- **Data** : Implémentation des repositories et appels microservices (Mockés via JSON pour ce PoC).
- **Presentation** : Routes API et DTOs optimisés pour les plateformes iOS/Android.

## 🛠 Tech Stack
- **Kotlin 2.0**
- **Ktor 3.4** (Server & Client)
- **Kotlinx Serialization**
- **MockK & JUnit 5** (Tests Unitaires)

## 🚦 Lancement
1. `gw build` pour compiler.
2. Lancer la classe `Application.kt`.
3. Endpoint de test : `http://localhost:8080/mobile/profile?email=test@gmail.com`
