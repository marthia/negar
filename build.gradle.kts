buildscript {
    dependencies {
        classpath("com.huawei.agconnect:agcp:1.7.3.302")
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}

if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "com.google.firebase.crashlytics")
}

if (file("agconnect-services.json").exists()) {
    apply(plugin = "com.huawei.agconnect")
}