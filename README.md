# FindRealEstate
This sample project is built using Clean Architecture with FlowRedux integration.
The project applied latest tech: Jetpack Compose, Coroutines Flow, Composite Build Gradle, FlowRedux, Composite State Machine
# FlowRedux
FlowRedux is a concept derived from Redux and is implemented using coroutine Flow for Android applications. Additionally, there's RxRedux, which is implemented using RxJava or RxKotlin for Android apps. The primary programming paradigms in FlowRedux include reactive programming and functional programming.

What are the benefits of implementing Redux in the project:
- Single Source of Truth: In Redux, the entire state of your application is stored in a single object. This single source of truth makes it easy to understand where to find the current state of the application as well as to maintain consistency across your application.
- State Immutability: Redux encourages immutable state. Instead of directly modifying the state, you create a new state object for each update. This makes it easier to track changes and maintain a history of state changes.
- Time-Travel Debugging: Redux supports time-travel debugging, which allows you to step forward and backward through the state changes in your application. This can be incredibly useful for tracking down bugs and understanding how your application reached a particular state.
- Testability: Redux applications are generally more testable because the state changes are isolated. You can easily write unit tests for logic in reducers and side effects.
- Separation of Concerns: Redux promotes a clear separation of concerns by keeping the state logic in reducers and side effects separate from the UI components. This makes your codebase more maintainable and scalable.
- Scaling and Performance: Redux is designed to handle complex state management efficiently. With proper usage, it can help you maintain good performance in your application even as it grows.
  
# Tech Stacks
This project uses many of the popular libraries, plugins and tools of the android ecosystem.
- [Compose](https://developer.android.com/jetpack/compose)
    - [Material](https://developer.android.com/jetpack/androidx/releases/compose-material) - Build Jetpack Compose UIs with ready to use Material Design Components.
    - [Foundation](https://developer.android.com/jetpack/androidx/releases/compose-foundation) - Write Jetpack Compose applications with ready to use building blocks and extend foundation to build your own design system pieces.
    - [UI](https://developer.android.com/jetpack/androidx/releases/compose-ui) - Fundamental components of compose UI needed to interact with the device, including layout, drawing, and input.
    - [Coil](https://coil-kt.github.io/coil/compose/) - An image loading library for Android backed by Kotlin Coroutines.
- [Retrofit](https://square.github.io/retrofit/) - Type-safe http client and supports coroutines out of the box.
- [Flow](https://developer.android.com/kotlin/flow) - Flows are built on top of coroutines and can provide multiple values. A flow is conceptually a stream of data that can be computed asynchronously.
- [Room](https://developer.android.com/training/data-storage/room) - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite
- [Material Design](https://material.io/develop/android/docs/getting-started/) - Build awesome beautiful UIs.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides runBlocking coroutine builder used in tests.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency Injection library
- [Test](https://en.wikipedia.org/wiki/Unit_testing)
    - [Mockk](https://mockk.io/) - A modern Mockk library for UnitTest.
    - [Coroutine-Test](https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test) - Provides testing utilities for effectively testing coroutines.
### Static Code Analysis Tools
- [Ktlint](https://github.com/jlleitschuh/ktlint-gradle) - A ktlint gradle plugin. Provides a convenient wrapper plugin over the ktlint project.
- [Detekt](https://github.com/detekt/detekt) - Static code analysis for Kotlin.
- [Detek for Compose](https://detekt.dev/docs/introduction/compose/) - Detekt configuration for Compose
- [Conventional Commit](https://plugins.jetbrains.com/plugin/13389-conventional-commit) - To provide completion for conventional commits
