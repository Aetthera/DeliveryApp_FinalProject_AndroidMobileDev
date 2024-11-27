plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.spayment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.spayment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
<<<<<<< HEAD

    // Testing dependencies
=======
    implementation(libs.firebase.database)
>>>>>>> 38d278c46db1e7ce1d6b6e30b085b441e1215a96
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Stripe SDK for payment integration
    implementation(libs.stripe)

    // Gson for JSON parsing
    implementation(libs.gson)

}
