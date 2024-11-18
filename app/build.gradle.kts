plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.deliveryapp_finalproject_androidmobiledev"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.deliveryapp_finalproject_androidmobiledev"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField "String", "MAPS_API_KEY", "\"${project.properties['MAPS_API_KEY']}\""
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.firebase:firebase-database:20.3.2")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
}