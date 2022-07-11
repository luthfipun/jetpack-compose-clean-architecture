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
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    // Jetpack/Androidx
    implementation(Androidx.coreKtx)
    implementation(Androidx.compose)
    implementation(Androidx.composeMaterial)
    implementation(Androidx.composePreview)
    implementation(Androidx.composeActivity)
    implementation(Androidx.lifecycleRuntime)
    implementation(Androidx.viewModelCompose)
    kapt(Androidx.hiltCompiler)
    implementation(Androidx.composePaging)
    api(Androidx.pagingRuntime)

    // Google
    implementation(Google.hilt)
    kapt(Google.hiltCompiler)

    // Kotlin
    implementation(Kotlin.coroutineAndroid)
    implementation(Kotlin.coroutineCore)

    // Testing
    testImplementation(Testing.jUnit)
    androidTestImplementation(Testing.jUnitExt)
    androidTestImplementation(Testing.espresso)
    androidTestImplementation(Testing.jUnitUi)
    debugImplementation(Testing.composeTooling)
    debugImplementation(Testing.composeTestManifest)
    androidTestImplementation(Testing.hiltTest)
    kaptAndroidTest(Testing.hiltTestCompiler)
    testImplementation(Testing.hiltTest)
    kaptTest(Testing.hiltTestCompiler)
}

kapt {
    correctErrorTypes = true
}