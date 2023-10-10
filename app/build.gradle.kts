plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.androidassignments"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.androidassignments"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test:runner:1.4.0")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.")
//
//    testImplementation("androidx.test:core-ktx:1.3.0")
//    testImplementation("androidx.test.ext:junit-ktx:1.1.2")
//    androidTestImplementation("com.android.support.test:rules:1.0.2")
//    testImplementation("org.robolectric:robolectric:4.0.2")
//    //Navigation component
//    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")
//    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
}