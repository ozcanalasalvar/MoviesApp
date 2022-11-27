import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.moviesapp"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", getApiKey())

//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.moviesapp.HiltTestRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    val lifeCycleExtensionVersion = "1.1.1"
    val supportVersion = "28.0.0"
    val retrofitVersion = "2.9.0"
    val okHttpLogging = "4.9.3"
    val glideVersion = "4.12.0"
    val rxJavaVersion = "2.1.1"
    val navVersion = "2.2.2"
    val preferencesVersion = "1.1.1"
    val paging_version = "3.1.1" //current version at the tim"


    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")


    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpLogging")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.2")

    implementation("io.reactivex.rxjava2:rxjava:$rxJavaVersion")
    implementation("io.reactivex.rxjava2:rxandroid:$rxJavaVersion")

    implementation("com.github.bumptech.glide:glide:$glideVersion")


    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")


    implementation("androidx.paging:paging-runtime-ktx:$paging_version")


    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Local Unit Tests
    implementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation("org.mockito:mockito-core:3.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.2.1")

    // Instrumented Unit Tests
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("org.mockito:mockito-core:3.9.0")
    androidTestImplementation("org.mockito:mockito-android:3.2.4")
    // androidTestImplementation "org.robolectric:robolectric:4.3.1"
    androidTestImplementation("com.google.ar:core:1.25.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.0")
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.3")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")

    debugImplementation("androidx.fragment:fragment-testing:1.5.4")


}

kapt {
    correctErrorTypes = true
}


fun getApiKey(): String {
    return gradleLocalProperties(rootDir).get("api.key") as String
}