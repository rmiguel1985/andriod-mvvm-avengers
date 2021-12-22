
object ApplicationId {
    const val id = "com.example.mvvmavengers"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Versions {
    const val kotlin = "1.4.21"
    const val gradle = "7.0.4"
    const val compileSdk = 31
    const val buildTools = "30.0.2"
    const val minSdk = 23
    const val targetSdk = 31

    // Android Libraries
    const val appCompat = "1.4.0"
    const val coreKtx = "1.3.2"
    const val dataStoreVersion = "1.0.0-alpha06"
    const val lifeCycleVersion = "2.2.0"

    // UI Libraries
    const val material = "1.3.0-rc01"
    const val constraintLayout = "2.0.0"

    // Test Libraries
    const val testRunner = "1.0.2"
    const val espressoCoreVersion = "3.4.0"
    const val kotlinTestVersion = "3.3.2"
    const val junitVersion = "4.13.2"
    const val junitExtVersion = "1.1.3"
    const val mockkVersion = "1.11.0"
    const val archCoreVersion = "2.1.0"
    const val mockWebServerVersion = "4.9.0"

    // Third Party libraries
    const val leakCanaryVersion = "2.7"
    const val timberVersion = "4.7.1"
    const val glideVersion = "4.12.0"
    const val startupRuntime = "1.0.0"
    const val koinVersion = "2.2.2"
    const val coroutinesVersion = "1.4.2"
    const val roomVersion = "2.2.6"
    const val gsonVersion = "2.8.8"
    const val retrofitGsonVersion = "2.9.0"
    const val retrofitVersion = "2.9.0"
    const val okhttpVersion = "4.9.0"
}

object KotlinLibraries {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object Libraries {
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanaryVersion}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
    const val startupRuntime = "androidx.startup:startup-runtime:${Versions.startupRuntime}"
    const val koinCore = "io.insert-koin:koin-core:${Versions.koinVersion}"
    const val koinCoreExt = "io.insert-koin:koin-core-ext:${Versions.koinVersion}"
    const val koinScope = "io.insert-koin:koin-androidx-scope:${Versions.koinVersion}"
    const val koinViewModel = "io.insert-koin:koin-androidx-viewmodel:${Versions.koinVersion}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonVersion}"
    const val retrofitInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"
    const val retrofitMockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttpVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
}

object AndroidLibraries {
    // ANDROID
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val prefDataStore = "androidx.datastore:datastore-preferences:${Versions.dataStoreVersion}"
    const val protoDataStore = "androidx.datastore:datastore-core:${Versions.dataStoreVersion}"

    // UI
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    // Lifecycle components
    const val lcLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycleVersion}"
    const val lcRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleVersion}"
    const val lcViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleVersion}"

    // Room
    const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomExtensions = "androidx.room:room-ktx:${Versions.roomVersion}"
}

object Testlibraries {
    const val testRunner = "com.android.support.test:runner:${Versions.testRunner}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
    const val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTestVersion}"
    const val junitTest = "junit:junit:${Versions.junitVersion}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val koinTest = "io.insert-koin:koin-test:${Versions.koinVersion}"
    const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"
    const val mocckAndroid = "io.mockk:mockk-android:${Versions.mockkVersion}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServerVersion}"
    const val archCore = "androidx.arch.core:core-testing:${Versions.archCoreVersion}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
}
