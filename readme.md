# Jetpack Compose Modularize
Open project with implemented jetpack compose clean architecture or modularize (mutli module) using network call rest api and offline mode / cached data to local database. This project also use kotlin dsl for gradle dependency management for scalable or larger project apps

## Techstack
- Jetpack Compose
- Retrofit
- Room
- Dagger Hilt
- Clean Architecture

## Local Module / Library
- ### App
    > The core project application with main dependency injection
- ### BuildSrc
    > Dependencies management includes all 3rd party library, versioning and default app configurations
- ### Common
    > The module for global state, screen and themes
- ### Data
    > This module only for datasource like as call network api with retrofit, graphql, socket.io and store data to local database like room, realmdb or firebase
- ### Domain
    > The module pure kotlin/java library for business logic and usecases
- ### Fact-Feature
    > The features app library
