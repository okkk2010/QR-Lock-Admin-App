plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.IoTQRDoorLockApp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.IoTQRDoorLockApp"
        minSdk = 24
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    // (2-1) AndroidX Core 라이브러리
    implementation(libs.appcompat.v131)
    implementation(libs.material.v140)
    implementation(libs.constraintlayout.v210)

    // (2-2) RecyclerView (사용자 목록 표시용)
    implementation(libs.recyclerview)

    // (2-3) Retrofit2 + Gson Converter (HTTP 통신 & JSON 파싱)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // (2-4) OkHttp 로깅 인터셉터 (선택)
    implementation(libs.logging.interceptor)

    // JUnit 테스트용 의존성
    testImplementation(libs.junit)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}
