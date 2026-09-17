val isReleaseBuild = gradle.startParameter.taskNames.any {
  it.contains("Release", ignoreCase = true)
}

fun signingPassword(
  envName: String,
  prompt: String,
): String? {
  System.getenv(envName)?.let { return it }

  if (!isReleaseBuild) return null

  return System.console()
    ?.readPassword("$prompt: ")
    ?.concatToString()
    ?: error("$envName is not set and no interactive console is available")
}

plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
}

android {
  namespace = "com.taka.runejournal"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "com.taka.runejournal"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

  buildFeatures {
    buildConfig = true
  }


  signingConfigs {
    create("release") {
      storeFile = rootProject.file("keystore/taka-release.jks")
      keyAlias = "taka-release"

      storePassword = signingPassword(
        envName = "TAKA_KEYSTORE_PASSWORD",
        prompt = "Keystore password",
      )

      keyPassword = signingPassword(
        envName = "TAKA_KEY_PASSWORD",
        prompt = "Key password",
      )
    }
  }

  buildTypes {
    getByName("release") {
      signingConfig = signingConfigs.getByName("release")

      isMinifyEnabled = true
      isShrinkResources = true

      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

kotlin {
  dependencies {
    implementation(project(":composeApp"))
  }
}

dependencies {
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.koin.core)
}