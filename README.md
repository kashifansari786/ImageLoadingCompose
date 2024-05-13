# imageShow_compose_Prashant
In this project assignment provided by Dr. Acharya Prashant, the objective is to fetch images from a server and display them in an ImageView while implementing memory and disk caching mechanisms. This involves optimizing the image loading process to enhance performance and efficiency, ensuring that images are retrieved from cache whenever possible to minimize network requests and improve user experience.
The project follows a structured flow:

1) **Splash Screen:** Initial screen for app launch.
2) **Home Screen:** Features a Scaffold with a top bar and a LazyVerticalGrid displaying images. Clicking on a cell navigates to the Home Detail Screen.
3) **Home Detail Screen:** Displays a parent image with additional details like title and description in a box below.

The navigation between screens is facilitated by a NavGraph setup.

i have used folowing technologies to complete this project
1) Used **Android Studio Iguana 2023.2.1 Patch 1** for development.
2) Utilized **Kotlin** as the primary programming language.
3) Employed **Jetpack Compose** for building UI components and logic.
4) Implemented **MVVM** architecture for clean separation of concerns.
5) Integrated **Dagger Hilt** for efficient dependency injection.
6) Utilized **Retrofit** for seamless API communication.
7) Implemented **disk and memory caching** for optimized data retrieval and performance enhancement.

# Below is the Evaluation criteria which i am achive and implement on this project

1) Images should load lazily. Also if you are on for example page 1 and quickly scroll to page 10, then the image loading of page 1 should be cancelled, and it should start loading for page 10:--- **Done**

2) There must be absolutely no lag while scrolling the image grid.:---- **Done but in compose grid view is show lagging in debug mode but woek in release mode**

3) Disk and Memory cache both should work. Disk cache should be used If image is missing in memory cache. When image is read from disk, memory cache should be updated.:------ **Done**

4) If your code does not compile with the latest Android Studio, your assignment will be rejected without checking.:---- **Done**

5) Step by step and clear instructions should be present in the README file to run the code if any.:----- **Done**

6) Assignment submitted using mediocre technologies like Flutter, React Native etc. will be rejected.:---- **Done**






