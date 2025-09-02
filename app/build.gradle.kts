import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    id("kotlin-parcelize")
}
android {
    namespace = "com.app.barcodescanner"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.barcodescanner"
        minSdk = 25
        targetSdk = 36
        // Dynamic versioning from CI/CD or fallback to defaults
        val versionCodeFromCI =
            project.findProperty("version.code") as String? ?: System.getenv("VERSION_CODE")
        val versionNameFromCI =
            project.findProperty("version.name") as String? ?: System.getenv("VERSION_NAME")

        versionCode = versionCodeFromCI?.toIntOrNull() ?: 1
        versionName = versionNameFromCI ?: "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    applicationVariants.all {
//        outputs.all {
//            val output = this as ApkVariantOutputImpl
//            output.outputFileName = "BarcodeScanner.apk"
//        }
//    }
    signingConfigs {
        create("release") {
            // Load local.properties
            val localProperties = Properties()
            val localPropertiesFile = rootProject.file("local.properties")
            if (localPropertiesFile.exists()) {
                localProperties.load(localPropertiesFile.inputStream())
            }

            // Helper function to get property from local.properties, gradle.properties, or environment
            fun getProperty(key: String): String? {
                return localProperties.getProperty(key)
                    ?: project.findProperty(key) as String?
                    ?: System.getenv(key)
            }

            val keystoreFile = getProperty("KEYSTORE_FILE") ?: "release-keystore.jks"
            storeFile = file(keystoreFile)
            storePassword = getProperty("KEYSTORE_PASSWORD")
            keyAlias = getProperty("KEY_ALIAS")
            keyPassword = getProperty("KEY_PASSWORD")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    // Use a consistent JVM toolchain for Kotlin compilation
    jvmToolchain(11)
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(project(":parser"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.barcode)
    implementation(libs.material3)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.savedstate)
    implementation(libs.androidx.lifecycle.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)
}