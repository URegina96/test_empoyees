plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.testempoyes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testempoyes"
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

// RxJava
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
// RxAndroid
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
// Для использования RxJava с Retrofit
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.7.1")

    implementation ("com.google.code.gson:gson:2.9.0")

// Retrofit & OkHttp
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")

// JSON Converter (Gson converter)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

}