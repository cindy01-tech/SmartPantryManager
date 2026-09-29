# Smart Pantry Manager

## 1. Application Description

Smart Pantry Manager is a Java-based Android application developed for the Mobile App Development 700 practical assignment.

The application helps users reduce food waste by keeping track of ingredients available in their pantry and suggesting recipes that can be prepared using only the ingredients they already have.

The main business rule of the application is **strict recipe matching**. A recipe is suggested only when the user has every required ingredient in the required quantity. Recipes that require missing ingredients or insufficient quantities are not displayed as suggested recipes.

### Main Features

* Add pantry ingredients
* View pantry ingredients
* Edit existing pantry ingredients
* Delete pantry ingredients
* Store ingredient quantity, unit and optional expiry date
* Display pantry items using RecyclerView
* Store a collection of pre-loaded recipes
* Suggest recipes based on the user's current pantry
* Handle simple singular/plural ingredient differences
* Handle common unit and quantity conversions
* View recipe ingredients and preparation instructions
* Settings screen with application preferences
* Persistent local data storage using SQLite

## 2. Technology Used

* **Programming Language:** Java
* **IDE:** Android Studio
* **Platform:** Android
* **Minimum Android Version:** Android API 24
* **Recommended API Level:** Android API 26 or higher
* **Database:** SQLite
* **Database API:** SQLiteOpenHelper
* **UI:** Android XML layouts
* **List Display:** RecyclerView
* **Navigation:** Android Intents
* **Version Control:** Git and GitHub

## 3. Database Design

The application uses a local SQLite database named `smart_pantry.db`.

The database contains three main tables:

* **pantry_items** — stores ingredients available in the user's pantry.
* **recipes** — stores the pre-loaded recipe names and preparation instructions.
* **recipe_ingredients** — stores the ingredients, quantities and units required by each recipe.

The recipe ingredients table is associated with the recipes table using the recipe ID.

SQLite was selected because the application is designed to work with the user's pantry data directly on the Android device.

SQLite provides local persistent storage without requiring an internet connection, external servers, Firebase configuration or external API services. It is also suitable for demonstrating the database concepts covered in the Mobile App Development module.

The application uses `SQLiteOpenHelper` to create and manage the local database.

## 4. Application Structure

The main screens of the application include:

1. Main Menu
2. Add Item
3. View Pantry Items
4. Edit Item
5. Suggested Recipes
6. Recipe Detail
7. Settings

The application uses Android Activities and Intents to navigate between these screens.

RecyclerView and custom adapters are used to display pantry items and suggested recipes.

## 5. Strict Recipe Matching

The strict-matching algorithm checks each recipe against the ingredients currently stored in the user's pantry.

For a recipe to appear in the Suggested Recipes screen:

* Every required ingredient must exist in the pantry.
* The available quantity must be sufficient.
* Compatible units are converted where appropriate.
* Simple singular/plural differences are handled.

For example, if a recipe requires bread, milk and eggs, but the pantry only contains bread and milk, the recipe will not be suggested.

This ensures that the application follows the requirement that users should not need to purchase an additional ingredient before preparing a suggested recipe.

## 6. Installation and Setup

### Requirements

* Android Studio
* Android SDK
* Java
* Android emulator or compatible Android device
* Git

### Steps

1. Clone the repository from GitHub.
2. Open the project in Android Studio.
3. Allow Android Studio to synchronise the Gradle project.
4. Connect an Android device or start an Android emulator.
5. Build the project.
6. Run the application from Android Studio.
7. Add pantry ingredients and use the Suggested Recipes screen to test the strict-matching functionality.

### GitHub Repository

Repository name: **SmartPantryManager**

GitHub account: **cindy01-tech**

## 7. Project Purpose

The purpose of Smart Pantry Manager is to demonstrate the practical use of Android development concepts including Activities, Intents, XML layouts, RecyclerView adapters, input validation and persistent database storage while solving a practical food-waste problem.

The application was developed as an individual practical project for Mobile App Development 700.
