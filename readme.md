# Jetpack Compose Modularize
Open project with implemented jetpack compose clean architecture or modularize (multi module) using network call rest api and offline mode auto cached data to local database with paging 3. This project also use kotlin dsl for gradle dependency management for scalable or larger project apps

## TechStack
- Jetpack Compose
- Retrofit
- Room
- Dagger Hilt
- Paging 3
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
