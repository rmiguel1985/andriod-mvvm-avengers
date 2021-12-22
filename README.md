# MvvmAvengers [![Build Status](https://app.bitrise.io/app/ff3ca2386abcbe04/status.svg?token=tZ5t49pIDhAlmyIvLawNAw)](https://app.bitrise.io/app/ff3ca2386abcbe04)

The purpose of this repo is to create an application that tries to 
implement Clean Architecture principles with MVVM and architecture components in Kotlin.

Application simply shows a list of avengers and lets user to get detail info of them.

There are a lot of things to improve, **this is a work in progress!!**

Libraries
=========

### Android
* [Glide][2]
* [JetPak Lifecycle][3]
* [Koin][4]
* [Retrofit][5]
* [Gson][6]
* [OkHttp][7]
* [Coroutines][8]
* [Room Persistence Library][9]

### Testing
* [Junit][10]
* [Corotuines test][11]
* [Mockk][12]
* [MockWebServer][13]

### Debugging 
* [Timber][14]
* [LeakCanary][15]

[2]: https://github.com/bumptech/glide
[3]: https://developer.android.com/jetpack/androidx/releases/lifecycle
[4]: https://insert-koin.io/
[5]: https://github.com/square/retrofit
[6]: https://github.com/google/gson
[7]: https://github.com/square/okhttp
[8]: https://github.com/Kotlin/kotlinx.coroutines
[9]: https://developer.android.com/topic/libraries/architecture/room
[10]: http://developer.android.com/intl/es/reference/junit/framework/package-summary.html
[11]: https://github.com/Kotlin/kotlinx.coroutines/tree/master/core/kotlinx-coroutines-test
[12]: https://mockk.io/
[13]: https://github.com/square/okhttp/tree/master/mockwebserver
[14]: https://github.com/JakeWharton/timber
[15]: https://github.com/square/leakcanary

Presentation Layer
==================
With viewModelScope all lifecycle is managed

Domain Layer
============
The domain layer implements Coroutines to execute operations on *IO thread* and 
return them to *Main thread*.

Data Layer
==========
- **Repository**: exposes functions to get data.
- **Policy**: OnlyCloudWithCache is injected by Koin.
  - *CloudWithCache*: the first attempt is to get data from cloud and if there's
  a successful response save it to disk. If there's no access to cloud, then if 
  there's local data it is returned.
  - *CacheWithCloud*: the first attempt is to get data from disk and if there's
   a successful response return it. If there's no disk data, then get data from 
   cloud and if there's a successful response save it to disk and return it.
  - *OnlyCloud*: gets data from cloud if it's possible.
- **Data Sources**
  - Cloud: using Retrofit.
  - Disk: Using Room. 

Testing
==========
There are resources for both instrumental and unit tests. 
 - Andriod Test: On test assets folder there's a json with sample data. 
 
 - Unit Test: On test resource folders there are two json. One wiht sample
 daga and another with malformed data.


>Instrumental tests are not testing UI. There are just to test Room, because
a context is needed. As said this is a work in progress.

### Presentation Layer
The test tries to verify that observer state is called correctly on success and failure responses from UseCase.

### Data Layer
There are positive and negative test for:
  - Room
  - Retrofit
  - Policies
  
### Jacoco
There's a jacoco script that enables a global coverage since Android Studio only allows coverage with Junit tests.

With the command at console:
```gradle
./gradlew clean jacocoTestReport
```
A report will be generated at **$rootProjectDir/app/build/reports/jacoco/jacocoTestReport/html/index.html**

## Dokka
Generates app documentation.

With the command:
```gradle
./gradlew dokka
```
Documentation will be generated at **$rootProjectDir/docs/app/index.html**

## Detekt
Detekt is included as code analysis

With the command:
```gradle
./gradlew detekt
```
Analysis will be executed and report generated at ** $rootProjectDir/app/build/reports/detekt/detekt.html**  
  

License
=====

```
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
