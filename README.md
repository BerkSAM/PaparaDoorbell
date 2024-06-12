# Papara Doorbell (Recipara)

Papara Doorbell (Recipara) is an Android application that allows users to discover various recipes and save their favorite recipes. The application fetches recipe data using the Spoonacular API and presents it to the user.

## Features

- **Recipe Discovery**: The app presents users with various recipes. Users can filter recipes by category.
- **Recipe Details**: Users can view the details of a recipe, including ingredients, preparation time, and serving size.
- **Favorite Recipes**: Users can add recipes they like to their favorites. Favorite recipes are easily accessible for quick reference.
- **Notifications**: The app sends notifications to users when new recipes are available.

## Installation

1. Clone or download the project from GitHub.
2. Open the project in Android Studio.
3. Add your Spoonacular API key to the `local.properties` file: `API_KEY=YOUR_API_KEY`
4. Add buildConfig = true in buildFeatures on build.gradle.kts(Module:app)
5. Run the app using the 'app' command.

## Technologies Used

- Kotlin
- Android Jetpack Compose
- Hilt (Dependency Injection)
- Retrofit - OkHttp (Network requests)
- Coil (Image loading)
- Room (Local database)
- Coroutines (Asynchronous programming)
- ViewModel - UI State Management
- JUnit and Mockito (Unit testing)

## Architecture

The application is built using modern Android application development techniques and best practices, including the following components:

- **ViewModel**: Responsible for storing and managing data related to the UI. The ViewModel manages the state of the UI and performs operations that can change the state of the UI.
- **Repository**: Provides access to the application's data sources. The Repository fetches data from various sources and provides it to the ViewModel.
- **Room Database**: The local data source for the application. Room is built on top of SQLite and enables storing and retrieving data locally.
- **Retrofit**: The network data source for the application. Retrofit is used to interact with HTTP APIs.
- **Coil**: Coil is a lightweight image loading library for Kotlin. Coil is used for loading and caching images.

## Testing

The application has been tested using unit tests. The tests are written using JUnit and Mockito.

## Project Screenshots
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/6098a279-c319-4d02-84e9-769fce427148" alt="UygulamaIcon" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/c642e5d1-7d45-477b-a82d-e0a0777c2cca" alt="LottieScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/af83d05f-784a-46ba-93d2-f3ab033a9deb" alt="HomaScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/ca81837b-dbed-4e4e-87dc-9d8e4cea96bf" alt="RecipeScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/7a39ec58-a5e6-498e-8de6-ab100ed57ffd" alt="RecipeScreen2" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/87f989ff-97a7-419c-88a4-72e1af0b1bf6" alt="RecipeDetailScreen1" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/05e59ae2-3f5c-4197-8a68-09909fc565cd" alt="RecipeDetailScreen2" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/1aca53fe-cdee-40db-ac4d-e37f0a6ef969" alt="FavoriteScreen" width="300"/>
<img src="https://github.com/BerkSAM/PaparaDoorbell/assets/95809835/95813514-f218-4a5c-92fd-0e08d13f3411" alt="FavoriteExtendScreen" width="300"/>
