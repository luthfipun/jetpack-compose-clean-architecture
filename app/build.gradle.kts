plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = Configs.compileSDKVersion

    signingConfigs {
        create("signingConfigRelease"){
            storeFile = file(KeyHelper.getValue(KeyHelper.KEY_STORE_FILE))
            storePassword = KeyHelper.getValue(KeyHelper.KEY_STORE_PASSWORD)
            keyAlias = KeyHelper.getValue(KeyHelper.KEY_ALIAS)
            keyPassword = KeyHelper.getValue(KeyHelper.KEY_PASSWORD)
        }
    }

    defaultConfig {
        applicationId = Configs.coreAppId
        minSdk = Configs.minSDKVersion
        targetSdk = Configs.targetSDKVersion
        versionCode = Configs.versionCode
        versionName = Configs.versionName
        multiDexEnabled = true

        signingConfig = signingConfigs.getByName("signingConfigRelease")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildTypes {
        getByName("release"){
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
    buildFeatures {
        compose = true
    }
    flavorDimensions += listOf("appType")
    productFlavors {
        create("_dev") {
            dimension = "appType"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "BASE_URL", KeyHelper.getValue(KeyHelper.BASE_URL))
        }
        create("_staging") {
            dimension = "appType"
            applicationIdSuffix = ".stag"
            versionNameSuffix = "-stag"
            buildConfigField("String", "BASE_URL", KeyHelper.getValue(KeyHelper.BASE_URL))
        }
        create("_prod") {
            dimension = "appType"
            buildConfigField("String", "BASE_URL", KeyHelper.getValue(KeyHelper.BASE_URL))
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Jetpack/Androidx
    implementation(Androidx.coreKtx)
    implementation(Androidx.compose)
    implementation(Androidx.composeMaterial)
    implementation(Androidx.composePreview)
    implementation(Androidx.composeActivity)
    implementation(Androidx.lifecycleRuntime)
    kapt(Androidx.hiltCompiler)
    implementation(Androidx.room)
    kapt(Androidx.roomCompiler)
    implementation(Androidx.roomKtx)

    // Google
    implementation(Google.hilt)
    kapt(Google.hiltCompiler)
    implementation(Google.multiDex)

    // Networking
    implementation(Networking.retrofit)
    implementation(Networking.gsonConverter)
    implementation(Networking.okHttpClient)
    implementation(Networking.okHttpLoggingInterceptor)

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