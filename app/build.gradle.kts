import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    val buildConfigProperties = gradleLocalProperties(rootDir, providers)
    buildConfigProperties.load(project.rootProject.file("local.properties").inputStream())
    namespace = "com.rishi.zmovie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rishi.zmovie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String" , "API_KEY", buildConfigProperties.getProperty("API_KEY") ?: "")
        buildConfigField("String" , "BASE_URL", buildConfigProperties.getProperty("BASE_URL") ?: "")
        buildConfigField("String" , "IMAGE_BASE_URL", buildConfigProperties.getProperty("IMAGE_BASE_URL") ?: "")
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
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // compose-navigation
    implementation(libs.androidx.navigation.compose)
    // coil-image
    implementation(libs.coil.compose)
    // hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    kapt(libs.dagger.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    // system bar color
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.work.runtime)
}

kapt {
    correctErrorTypes = true
}