# Safe - Cross-Platform Shopping App

## Overview

**Safe** is a modern, cross-platform mobile shopping application built with Kotlin Multiplatform Mobile (KMM). It allows users to browse a catalog of clothing items, add them to a cart, and simulate a checkout process. The application is designed to run on both Android and iOS from a single, shared Kotlin codebase, utilizing Jetpack Compose Multiplatform for the user interface.

## Features

*   **Product Catalog**: Browse a dynamic list of clothing products on the Home screen.
*   **Product Details**: Each product listing clearly displays its image, title, and price.
*   **Shopping Cart**: Seamlessly add products to a personal shopping cart.
*   **Cart Management**: The Cart screen displays all selected items. Users can review their choices before proceeding. (Future enhancements will include quantity adjustments and item removal).
*   **Simulated Checkout**: A "Checkout" button on the cart screen initiates a simulated order placement, concluding with a confirmation dialog.
*   **About Page**: Provides users with brand information and displays the official "Safe" brand logo.
*   **Splash Screen**: Features the "Safe" brand logo prominently on application startup, enhancing brand recognition.
*   **Direct Access**: No user registration or login is required, allowing users to immediately browse and order products.
*   **Cross-Platform UI**: The user interface is built with Jetpack Compose Multiplatform, enabling a shared UI codebase for both Android and iOS, promoting consistency and development efficiency.

## Technology Stack

*   **Kotlin Multiplatform Mobile (KMM)**: Core framework for sharing Kotlin code across Android and iOS.
*   **Jetpack Compose Multiplatform**: For crafting the user interface for both Android and iOS from a unified Kotlin codebase.
*   **Kotlin Coroutines & Flow**: Employed for managing asynchronous operations, background tasks, and reactive state updates in the shared logic and UI layers.
*   **Shared Business Logic**: Critical application logic, including data models (e.g., `Product`), view models, and repositories, resides in the KMP `shared` module.
*   **Android Architecture Components**: Utilized as needed on the Android-specific side (e.g., `MainActivity`, lifecycle awareness).
*   **Gradle**: For robust dependency management and building the multi-module KMP project.

## Project Structure

The project adheres to a standard Kotlin Multiplatform project organization:

*   **`shared/`**: This module is the heart of the KMP application, containing:
    *   `commonMain/`: Kotlin code, including business logic, data models, ViewModels, and Jetpack Compose UI, shared across all target platforms (Android, iOS).
    *   `androidMain/`: Platform-specific Kotlin code for Android, such as `actual` implementations for `expect` declarations in `commonMain`, or Android-specific utilities.
    *   `iosMain/` (Planned): Platform-specific Kotlin code for iOS, including `actual` implementations and iOS-specific utilities.
*   **`androidApp/`**: The Android application module. It includes Android-specific components like `MainActivity.kt`, AndroidManifest.xml, and resources. This module depends on and utilizes the `shared` module.
*   **`iosApp/`** (Planned): The iOS application module. It will integrate the `shared` module to use the common business logic and Compose Multiplatform UI, along with any necessary Xcode project configurations and Swift/Objective-C interop code.
*   **`app/src/main/assets/images/`**: (Within the `androidApp` module, with KMP resource handling for shared access) Contains all product images (e.g., `1.jpeg` to `18.jpeg`) and the brand logo (`logo.png`). These are managed to be accessible from the shared KMP code.

## Setup & Running

### Prerequisites

*   Android Studio (latest stable version, e.g., Hedgehog or later)
*   Java Development Kit (JDK) 17 or higher
*   (For iOS) Xcode (latest stable version)
*   (For iOS) Kotlin Multiplatform Mobile plugin installed in Android Studio (usually included by default)
*   (For iOS) Cocoapods for managing iOS dependencies, if applicable.

## Android Studio View
<img width="1919" height="1009" alt="image" src="https://github.com/user-attachments/assets/febd3932-0a95-4598-bb92-e1241fa64f79" />

    
