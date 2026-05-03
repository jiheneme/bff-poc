
Here is the professional English version of your **README**, optimized for a GitHub or GitLab repository.

---

# BFF - Mobile Aggregator (Ktor & Clean Architecture)

This project is a **Backend-for-Frontend (BFF)** designed to aggregate data from multiple banking microservices (Users, Cards) and provide an optimized, single-entry response for iOS and Android mobile applications.

## 🚀 Architecture

The project strictly follows **Clean Architecture** principles to ensure the codebase remains testable, maintainable, and independent of external frameworks:

* **Domain**: The core of the application containing pure business logic (Use Cases) and domain entities.
* **Data**: Handles data persistence and external sources. Includes Repository implementations and HTTP clients to communicate with microservices (Real sources or JSON Mocks for this PoC).
* **Presentation**: Entry layer (Ktor Routes) managing HTTP requests, input validation, and data transformation into mobile-optimized DTOs.



## 🛠 Tech Stack

* **Language**: Kotlin 2.0
* **Framework**: Ktor 3.4 (Server Engine & HTTP Client)
* **DI**: Dependency Injection via Koin (or manual injection for PoC stability)
* **Serialization**: Kotlinx Serialization (JSON)
* **Testing**: MockK & JUnit 5 for Unit and Integration testing.

## 🏗 Industrialization & DevOps

This PoC is "Cloud-Ready" and built with industrial standards in mind:

* **Docker**: Multi-stage containerization for a lightweight and secure runtime image.
* **CI/CD (GitLab)** : Automated pipelines managing **DEV**, **STAGING** (Recette), and **PROD** environments.
* **Cloud Run (GCP)**: Serverless deployment with dynamic configuration via environment variables.
* **Traceability**: Docker images are tagged using **Git Commit SHAs** to ensure a 1-to-1 correlation between deployed code and the source repository.



## 🚦 Getting Started

### Local Development
1.  **Build**: `./gradlew clean build`
2.  **Run**: Execute the `Application.kt` class from your IDE.
3.  **Test Endpoint**: [http://localhost:8080/mobile/profile?email=test@gmail.com](http://localhost:8080/mobile/profile?email=test@gmail.com)

### Running with Docker
1.  **Build Image**: `docker build -t bff-poc:latest .`
2.  **Run Container**: `docker run -p 8080:8080 --name bff-poc bff-poc:latest`

## ⚙️ Multi-Environment Configuration

The project leverages the **HOCON** format (`application.conf`) to dynamically inject service URLs based on the target environment:

* `MS_USER_URL`: Microservice User API endpoint.
* `MS_CARDS_URL`: Microservice Cards API endpoint.
* `PORT`: Dynamic listening port injected by Google Cloud Run at runtime.

---

### 💡 Pro-Tip for your Demo
You might want to add a "Sequence Diagram" section to show how the BFF calls both the User and Card services in parallel before merging them—it’s a great way to show the "Aggregator" power of your project!

Do you need help creating that sequence diagram or any other section?
