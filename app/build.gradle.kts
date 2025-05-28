plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")  // Necesario para Hilt y Moshi
}

android {
    namespace = "com.example.debtgo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.debtgo"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false  // Corregido: isWinifyEnabled -> isMinifyEnabled
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
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.material.icons.extended)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.hilt.android)
        implementation("androidx.compose.material:material:1.5.0")
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.androidx.lifecycle.runtime.ktx.v280)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.runtime.livedata)
        kapt(libs.hilt.compiler)
        implementation(libs.androidx.hilt.navigation.compose)
        kapt(libs.androidx.hilt.compiler)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.recyclerview)
        implementation(libs.filament.android)
        implementation(libs.androidx.datastore.core.android)
    }
}
