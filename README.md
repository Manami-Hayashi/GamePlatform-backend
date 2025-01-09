# Gamearena
**Integration 5 Team 3**
- Alperen Doganci
- Manami Hayashi
- William Kasasa
- Noah Guerin
- Narjiss Hajji


## Overview
Gamearena is a dynamic platform designed to offer seamless board gaming experiences. This repository contains the backend code, developed using **Java Spring Boot** and **Gradle**. The platform is built for scalability, with features aimed at enhancing player engagement and simplifying game management.


---


## Features


### Core Backend Features
- **Authentication and Authorization**:
  - Utilizes **Keycloak** for secure Single Sign-On (SSO).
  - Ensures seamless authentication across the platform and integrated games.


- **Game Management**:
  - Supports adding new games with ease using asynchronous messaging via **RabbitMQ**.


- **Player Statistics**:
  - Collects and stores detailed player activity data, including:
    - Games played, wins, losses, and draws.
    - Game durations and number of moves per game.
    - Player demographics for personalized experiences.


- **Admin Functionality**:
  - Allows exporting player and game statistics in CSV format.
  - Allows adding games to the platform.
  - Admins can view the win probability of each player, win probability distibution for each game, player classification distribution.
  - Offers insights into player engagement and game performance.


- **Achievements System**:
  - Displays unlocked achievements for each game.
  - Highlights steps for unlocking remaining achievements.


- **Lobby System**:
  - Enables player matchmaking and game initiation.
  - Includes functionality for inviting friends to games.


- **Payment Processing**
  - Stripe Integration: The platform uses Stripe for handling secure payments during game purchases. This allows users to buy games seamlessly and securely through the frontend, with all payment-related logic handled by Stripe's API.


- **Chatbot Integration**
  - A chatbot is integrated to provide players with assistance regarding game rules, tips, and platform guidance.
  - The chatbot is powered by AI, ensuring accurate and fast responses to player queries.
  - Users can interact with the chatbot for a more engaging and informative platform experience.


- **Game Recommendations and Predictions**:
  - Features machine learning models for:
    - Predicting player classification levels.
    - Estimating win probabilities.
  - Provides personalized game recommendations based on player activity.


---


## Technical Stack


- **Java Spring Boot**
- **Gradle**
- **RabbitMQ**
- **MYSQL**
- **MSSQL**
- **Keycloak**
- **Stripe**


---
## Installation and Setup


To set up and run the Gamearena backend platform, follow these steps:


### Step 1: Clone the Repository
```bash
git clone https://gitlab.com/kdg-ti/integration-5/2024-2025/team3/game-arena.git


cd game-arena
```


### Step 2: Build the Project
```bash
./gradlew clean build
```
### Step 3: Start the Containers
```bash
docker compose up -d
```
### Step 4: Run the application




## Deployment


- **Containerized Deployment**: Utilizes **Docker** for containerization.
- **Cloud Deployment**: Compatible with platforms like:
  - **Azure Container Apps**
- **CI/CD**: Configured for automated deployment using **GitLab CI/CD** pipelines.


### Environment Variables
Ensure the following environment variables are configured:


- **Keycloak server details**:
  - `KEYCLOAK_REALM`
  - `KEYCLOAK_CLIENT_ID`
  - `KEYCLOAK_CLIENT_SECRET`


---


## Testing


- **Unit Tests**: Ensures the integrity of game logic.
- **Integration Tests**: Validates API endpoints and platform functionality.
- **Quality Checks**: Integrated into the GitLab CI/CD pipeline.


---


---


## License
