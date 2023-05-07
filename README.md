# FindRealEstate
This project is built using Clean Architecture with Flow Redux integration.
The project has four modules: app, data, domain and testutils.
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

# Flow Redux
This version demos how to implement flow redux with composite state machine
