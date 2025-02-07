# InterviewTask

## Overview

Showcased the following:

1. Clean architecture following SOLID principles
2. Jetpack Libraries - ViewModel, Room, Fragment
3. Coroutines and Flow
4. Unit testing using MockK
5. Instrumented tests for Room database and Retrofit service (endpoint invoke) 
6. Dependency Injection using Dagger Hilt
7. Retrofit2 with Moshi for network calls
8. Project uses latest stable Kotlin version along with KSP instead of Kapt

*Did not use Jetpack Compose because for the past year I have been working on View only projects.

App functionality:

Triggering endpoint and save result to database, allows to open previous search results by clicking on the search history item. When press search result button dialog with details opens. Clear search history button clears the database. Error handling for network and already done searches.

## Timelog

- about 2h - project setup and business logic
- about 2h - ui implementation
- about 2h - testing and bug-fixing
- about 1h - admin stuff (readme, repo creating because not able to send zip)

## Dev tools & test device

Project was done using Android Studio Ladybug | 2024.2.1 Patch 2, testing was done only on debug build on Android 15 device (due to time constraints).