# BFF - Mobile Aggregator (Ktor & Clean Architecture)

This project is a **Backend-for-Frontend (BFF)** designed to aggregate data from multiple banking microservices (Users, Cards) and provide an optimized, single-entry response for iOS and Android mobile applications.

## 🚀 Architecture

The project strictly follows **Clean Architecture** principles to ensure the codebase remains testable, maintainable, and independent of external frameworks:

* **Domain**: The core of the application containing pure business logic (Use Cases) and domain entities.
* **Data**: Handles data persistence and external sources. Includes Repository implementations and HTTP clients to communicate with microservices.
* **Presentation**: Entry layer (Ktor Routes) managing HTTP requests, input validation, and data transformation into mobile-optimized DTOs.

## 🏗 Industrialization & Scaffolding

This project includes native **IntelliJ File Templates** to automate the creation of new features while strictly following our Clean Architecture patterns. No plugins are required as the templates are shared via the `.idea` folder.

###  Quick Start: Generating a New Endpoint

1.  **Locate your target package**: In the Project tool window, **Right-click** on your base package (e.g., `com.example`).
2.  **Trigger the template**: Select **New** → **BFF Full API**.
3.  **Configure**:
    *   **NAME**: Enter the entity name in PascalCase (e.g., `Transaction` or `User`).
4.  **Confirm**: Press **Enter** or click **OK**.

### Generated Files
The generator creates a complete vertical slice of the architecture in one click:

*   **Controller**: `presentation/controllers/${NAME}Controller.kt` — Handles Ktor routing and requests.
*   **UseCase**: `domain/usecases/Get${NAME}sUseCase.kt` — Contains the business logic.
*   **Repository Interface**: `domain/repository/${NAME}Repository.kt` — Defines the data contract.
*   **Repository Implementation**: `data/repository/${NAME}RepositoryImpl.kt` — Handles data fetching logic.

### 🛠️ Troubleshooting
*   **The "BFF Full API" option is missing**:
    *   Ensure you are using **IntelliJ IDEA**.
    *   Check that your `.idea/fileTemplates` folder contains the `.ft` and `.child.kt` files.
    *   Verify that your **File and Code Templates** scheme is set to **Project** in `Settings` → `Editor`.
*   **The files are generated in the wrong folder**: The template uses relative paths. Always perform the **Right-click** on the root package where the `presentation`, `domain`, and `data` folders should reside.

---

## 🛠 Tech Stack

* **Language**: Kotlin 2.0
* **Framework**: Ktor 3.4 (Server Engine & HTTP Client)
* **DI**: Koin for Dependency Injection.
* **Serialization**: Kotlinx Serialization (JSON)
* **Testing**: MockK & JUnit 5.

## 🚀 DevOps & Deployment

* **Docker**: Multi-stage containerization for lightweight runtime images.
* **CI/CD (GitLab)**: Automated pipelines managing **DEV**, **STAGING**, and **PROD**.
* **Cloud Run (GCP)**: Serverless deployment with dynamic environment configuration.
* **Traceability**: Images are tagged using **Git Commit SHAs**.

## 🚦 Getting Started

### Local Development
1.  **Build**: `./gradlew clean build`
2.  **Run**: Execute the `Application.kt` class from your IDE.
3.  **Test Endpoint**: [http://localhost:8080/mobile/profile?email=test@gmail.com](http://localhost:8080/mobile/profile?email=test@gmail.com)

### Running with Docker
1.  **Build Image**: `docker build -t bff-kotlin:latest .`
2.  **Run Container**: `docker run -p 8080:8080 --name bff-kotlin bff-kotlin:latest`

## ⚙️ Multi-Environment Configuration

The project leverages the **HOCON** format (`application.conf`) to dynamically inject service URLs:

* `MS_USER_URL`: Microservice User API endpoint.
* `MS_CARDS_URL`: Microservice Cards API endpoint.
* `PORT`: Dynamic listening port (injected by Cloud Run).