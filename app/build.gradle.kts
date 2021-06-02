plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "minhdtm.example.exampledatastore"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // App dependencies
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.annotation:annotation:1.2.0")
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.fragment:fragment-ktx:1.3.4")

    // Architecture component dependencies
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    // Kotlin dependencies
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")

    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.35")
    kapt("com.google.dagger:hilt-compiler:2.35")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0-beta01")
}