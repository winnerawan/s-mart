import com.android.build.gradle.internal.tasks.databinding.DataBindingGenBaseClassesTask
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    //    id("kotlin-kapt")

}

android {
    namespace = "id.co.sherly.mart"
    compileSdk = 34

    defaultConfig {
        applicationId = "id.co.sherly.mart"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1-dev"
        vectorDrawables {
            useSupportLibrary = true
        }
//        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_URL", "\"https://store-api.s-mart.dev/\"")
        buildConfigField("String", "DATABASE", "\"s-mart.db\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
//        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.4"
//    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    /** core */
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("androidx.annotation:annotation:1.9.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.preference:preference-ktx:1.2.1")

    //compose
//    implementation("androidx.activity:activity-compose:1.8.0")
//    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material:material:1.5.4")

    //room
    implementation("androidx.room:room-runtime:2.6.1")

    annotationProcessor("androidx.room:room-compiler:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    //extensions and coroutines for room
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-rxjava3:2.6.1")

    //dagger-hilt
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:dagger-compiler:2.50")
    ksp("com.google.dagger:hilt-compiler:2.50")
//    ksp("androidx.hilt:hilt-compiler:1.1.0")

    //RxJava
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.7.0")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    ksp("com.github.bumptech.glide:compiler:4.16.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")

    //upload service
    implementation("net.gotev:uploadservice:4.7.0")
    implementation("net.gotev:uploadservice-okhttp:4.7.0")

    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.daimajia.swipelayout:library:1.2.0@aar")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.daimajia.androidanimations:library:2.4@aar")
    implementation("com.dolatkia:full-screen-card-viewpager:1.0.0")
    implementation("com.orhanobut:logger:2.2.0")
//    implementation("com.roughike:bottom-bar:2.3.1")

    /** test */
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            project.tasks.getByName("ksp" + variant.name.capitalized() + "Kotlin") {
                val dataBindingTask =
                    project.tasks.getByName("dataBindingGenBaseClasses" + variant.name.capitalized()) as DataBindingGenBaseClassesTask
                (this as AbstractKotlinCompileTool<*>).setSource(dataBindingTask.sourceOutFolder)
            }
        }
    }
}