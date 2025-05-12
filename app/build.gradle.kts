plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.budgetdot"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.budgetdot"
        minSdk = 23
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Replaced libs.appcompat.v161 with the consolidated libs.appcompat
    implementation(libs.appcompat)
    // Replaced libs.material.v190 and libs.material.vxyz with the consolidated libs.material
    implementation(libs.material)
    // Replaced libs.constraintlayout.v214 with the consolidated libs.constraintlayout
    implementation(libs.constraintlayout)

    implementation(libs.recyclerview)
    implementation(libs.cardview)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.androidx.preference)
    implementation(libs.litert.support.api)
    annotationProcessor(libs.androidx.room.compiler)

    // MPAndroidChart for visualizations
    implementation(libs.mpandroidchart)

    // Bento grid layout
    implementation(libs.discrete.scrollview)

    implementation(libs.android.arch.lifecycle.extensions)
    // Removed the second duplicate MPAndroidChart declaration
    // implementation (libs.mpandroidchart)

    // Color picker for categories
    implementation(libs.colorpicker)

    // Added test dependencies which were present in libs.versions.toml but not in build.gradle.kts
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Added activity dependency based on libs.versions.toml
    implementation(libs.activity)

    // Note: lifecycle "1.1.1" is used by android-arch-lifecycle-extensions
    // Room compiler is annotationProcessor, runtime is implementation
    // Preference is androidx-preference
}