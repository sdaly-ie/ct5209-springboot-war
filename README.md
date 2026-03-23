# Stephen's Petitions App

## Overview

Stephen's Petitions is a Spring Boot web application for creating, viewing, searching, and signing petitions.

It was developed as part of the CT5209 Cloud DevOps module and demonstrates a Continuous Integration and Continuous Deployment (CI/CD) workflow using GitHub, Jenkins, Maven, Docker, and deployment to a Dockerized Apache Tomcat container on Amazon Web Services (AWS) Elastic Compute Cloud (EC2).

> **For reviewers:** Start with **[v1.1.0 – Review Snapshot](https://github.com/sdaly-ie/ct5209-springboot-war/releases/tag/v1.1.0)** for the stable assessed snapshot.  
> The `main` branch may include later refinements or documentation updates.  
> Initial release snapshot: **v1.0.0**

## Live Application

- Application URL: http://13.49.44.175:9090/stephenspetitions/

## Features

- Create a petition
- View all petitions
- View petition details
- Sign a petition with name and email
- Search petitions by keyword
- Landing page with navigation

## Tech Stack

- Java 17
- Spring Boot
- Thymeleaf
- Maven
- JUnit and MockMvc
- Jenkins
- GitHub
- Docker
- Apache Tomcat 10
- AWS EC2
- Cloudflare Tunnel

## Observability

The project now includes a local observability demo using Spring Boot Actuator, Prometheus, and Grafana.

### What was added

- Spring Boot Actuator and Micrometer Prometheus registry
- `/actuator/health` and `/actuator/prometheus` endpoints
- Prometheus scrape configuration under `observability/prometheus.yml`
- Grafana dashboard provisioning through `observability/docker-compose.yml`

### What this demonstrates

- Application metrics exposure from Spring Boot
- Prometheus scraping of live application metrics
- Grafana dashboard visualisation for operational monitoring
- A practical first step into observability for Java and platform-oriented roles

### Evidence

- Prometheus target status: `docs/images/prometheus-target-up.jpg`
- Grafana dashboard (CPU): `docs/images/grafana-dashboard-system-cpu.jpg`
- Grafana dashboard (two panels): `docs/images/grafana-dashboard-two-panels.jpg`

## Architecture

The diagram below shows the application runtime flow and the CI/CD path used to build, test, package, and deploy the application.

```mermaid
flowchart LR
    DEV[Developer]
    GH[GitHub Repository]
    CF[Cloudflare Tunnel]
    J[Jenkins Pipeline]
    B[Maven Build, Test, and Package]
    AT[Automated Tests
JUnit, MockMvc, Spring Boot context]
    W[WAR Artifact
stephenspetitions.war]
    DG[Dockerfile]
    AP[Manual deployment approval]

    DEV -->|git push| GH
    DEV -->|browser access| CF
    GH -->|webhook or manual Jenkins run| J
    CF -->|public access to Jenkins| J
    J --> B
    B --> AT
    B --> W
    J --> AP

    subgraph EC2["AWS EC2"]
        DE[Docker Engine]
        IMG[Docker image
stephenspetitions:latest]
        T[Tomcat container
port 8080 mapped to host 9090]
        subgraph APP["Spring Boot Application"]
            C[PetitionController]
            S[PetitionService
business logic]
            M[Petition and Signature models
in-memory seeded list]
            V[Thymeleaf templates
petitions.html
petition.html
create.html
search.html
results.html]
        end
    end

    DG -->|copied to EC2| DE
    W -->|copied to EC2| DE
    AP -->|deploy approved| DE
    DE -->|docker build| IMG
    IMG -->|docker run -p 9090:8080| T

    U[User Browser] -->|HTTP request| T
    T --> C
    C --> S
    S --> M
    C --> V
    T -->|HTML response| U
```

## Project Structure

```text
src/main/java/com/example/demo
├── controller   # Web layer (PetitionController)
├── service      # Business logic (PetitionService)
└── model        # Domain objects (Petition, Signature)

src/test/java/com/example/demo
├── PetitionControllerTest
├── PetitionServiceTest
└── DemoApplicationTests

repo root
├── Jenkinsfile
├── Dockerfile
├── pom.xml
└── README.md
```

## CI/CD Pipeline

Jenkins is used to automate the build, test, packaging, and deployment workflow for the project.

The repository contains a Jenkins pipeline in `Jenkinsfile` with the following stages: `GetProject`, `Build`, `Test`, `Package`, `Archive`, `ApproveDeploy`, and `Deploy`.

The pipeline workflow is:

1. Get the latest code from GitHub
2. Build the application with Maven
3. Run automated tests
4. Package the application as a WAR file
5. Archive the WAR artifact in Jenkins
6. Pause for manual deployment approval
7. Copy the Dockerfile and WAR file to EC2
8. Build a Docker image on EC2
9. Run the application as a Tomcat container on EC2

### Jenkins Access

Jenkins was made securely reachable from the internet using a Cloudflare Tunnel. This supported browser access to the Jenkins dashboard and webhook configuration without directly exposing the server through open inbound ports.

### Jenkins Pipeline Evidence

The screenshot below shows a successful Jenkins pipeline run from the `main` branch, including manual approval and Docker-based deployment to EC2.

![Jenkins pipeline success](docs/images/status-jenkins.jpg)

## Testing

The project includes automated tests across multiple layers.

### Service tests

- Retrieve seeded petitions
- Search petitions by keyword
- Add a new petition
- Add a signature to a petition

### Controller test

- Verify the `/petitions` page loads successfully

### Application startup test

- Verify the Spring Boot application context loads

## How to Run Locally

The application can be started locally using the Maven wrapper from the project root directory.

Open a terminal in the project root directory and run:

### Windows PowerShell

```powershell
.\mvnw.cmd spring-boot:run
```

### macOS / Linux

```bash
./mvnw spring-boot:run
```

Then open:

```text
http://localhost:8080/
```

Useful local routes:

```text
http://localhost:8080/petitions
http://localhost:8080/create
http://localhost:8080/search
```

When run locally through Spring Boot, the application is served from the root context, for example `/petitions`.
When deployed as a WAR file inside Tomcat on EC2, the WAR file name becomes the Tomcat context path, so the deployed application is reached under `/stephenspetitions`.

## Deployment

The deployed version runs in a Dockerized Apache Tomcat container on AWS EC2 and is updated through the Jenkins pipeline after manual approval.

Deployment flow:

- Code is pushed to GitHub, where Jenkins can be triggered by webhook or run manually from Jenkins
- Jenkins retrieves the latest code and runs build and test stages
- The application is packaged as `stephenspetitions.war`
- The WAR artifact is archived in Jenkins
- Deployment proceeds after manual approval
- Jenkins copies `Dockerfile` and `stephenspetitions.war` to the EC2 host
- Jenkins stops and disables the host-level `tomcat10` service to free port `9090` for the containerized deployment
- Jenkins builds the Docker image on EC2
- Jenkins runs the container with port mapping `9090:8080`

For the deployed version, the WAR file name provides the Tomcat context path, so the live application is reached at `/stephenspetitions` on port `9090`.

## Reviewer Quick Tour

This section provides a quick way to review the main features, structure, and deployment evidence of the project.

1. Open the live application
2. Use the navigation links to:
   - View all petitions
   - Create a petition
   - Search petitions
3. Open a petition and sign it
4. Review `Jenkinsfile` for the pipeline stages and deployment logic
5. Review `Dockerfile` for the Tomcat container image setup
6. Inspect the commit history to see iterative development
7. Review the architecture diagram and Jenkins pipeline evidence in this README

## Challenges and Reflection

This section summarises the main technical challenges encountered during development and what was learned from resolving them.

Key challenges included:

- Structuring the application pages and navigation cleanly
- Configuring Jenkins pipeline stages correctly
- Packaging the project as a WAR file for Tomcat deployment
- Converting the deployment flow from a host Tomcat service to a Dockerized Tomcat container on EC2
- Debugging GitHub webhook triggering and remote pipeline behaviour
- Validating manual deployment approval and deployment to AWS EC2
- Adding tests across service and controller layers while keeping the application stable

This project provided practical experience in web development, Continuous Integration, Continuous Deployment, testing, containerized deployment, cloud deployment, and troubleshooting.

## Future Improvements

The following items outline realistic next steps to extend the application beyond the current scope.

- Add persistent database storage such as H2, MySQL, or AWS Relational Database Service (RDS)
- Improve the user interface styling and usability
- Expand controller and integration test coverage
- Add form validation and error handling
- Add authentication and user accounts

## Author

Stephen Daly
