plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = Configs.compileSDKVersion

    defaultConfig {
        minSdk = Configs.minSDKVersion
        targetSdk = Configs.targetSDKVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":domain"))

    // Jetpack/Androidx
    implementation(Androidx.coreKtx)
    implementation(Androidx.composePaging)
    api(Androidx.pagingRuntime)
    implementation(Androidx.lifecycleRuntime)
    kapt(Androidx.hiltCompiler)
    implementation(Androidx.room)
    kapt(Androidx.roomCompiler)
    implementation(Androidx.roomKtx)

    // Google
    implementation(Google.hilt)
    kapt(Google.hiltCompiler)

    // Networking
    implementation(Networking.retrofit)
    implementation(Networking.gsonConverter)
    implementation(Networking.okHttpClient)
    implementation(Networking.okHttpLoggingInterceptor)

    // Kotlin
    implementation(Kotlin.coroutineAndroid)
    implementation(Kotlin.coroutineCore)
}

kapt {
    correctErrorTypes = true
}