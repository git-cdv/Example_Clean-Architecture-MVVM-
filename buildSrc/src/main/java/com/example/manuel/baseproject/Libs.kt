package com.example.manuel.baseproject

object Libs {

    object Android {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.Android.APP_COMPAT}"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Android.KOTLIN_VERSION}"
        const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Android.KOTLIN_VERSION}"
        const val LIFECYCLE = "androidx.lifecycle:lifecycle-extensions:${Versions.Android.LIFECYCLE}"
        const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.LIFECYCLE}"
        const val TOOLS_BUILD_GRADLE = "com.android.tools.build:gradle:${Versions.Android.TOOLS_BUILD_GRADLE}"
    }

    object Asynchronous {
        const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Asynchronous.COROUTINES}"
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Asynchronous.COROUTINES}"
    }

    object DependencyInversion {
        const val KOIN_ANDROID = "org.koin:koin-android:${Versions.DependencyInversion.KOIN}"
        const val KOIN_SCOPE_FEATURES = "org.koin:koin-androidx-scope:${Versions.DependencyInversion.KOIN}"
        const val KOIN_VIEWMODEL_FEATURES = "org.koin:koin-androidx-viewmodel:${Versions.DependencyInversion.KOIN}"
    }

    object Network {
        const val GSON = "com.squareup.retrofit2:converter-gson:${Versions.Network.GSON}"
        const val RETROFIT = "com.squareup.retrofit2:${Versions.Network.RETROFIT}"
        const val RETROFIT_COROUTINES = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.Network.COROUTINES}"
    }

    object Testing {
        const val CORE = "androidx.arch.core:core-testing:${Versions.Testing.CORE_TESTING}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.Testing.ESPRESSO}"
        const val JUNIT = "junit:junit:${Versions.Testing.JUNIT}"
        const val JUNIT5 = "io.kotlintest:kotlintest-runner-junit5:${Versions.Testing.JUNIT5}"
        const val MOCKITO = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.Testing.MOCKITO}"
        const val MOCKITO_INLINE = "org.mockito:mockito-inline:${Versions.Testing.MOCKITO_INLINE}"
        const val RUNNER = "androidx.test:runner:${Versions.Testing.RUNNER}"
        const val TRUTH = "com.google.truth:truth:${Versions.Testing.GOOGLE_TRUTH}"
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Testing.COROUTINES}"
    }

    object UI {
        const val CARD_VIEW = "androidx.cardview:cardview:${Versions.UI.CARD_VIEW}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.UI.CONSTRAINT_LAYOUT}"
        const val GLIDE = "com.github.bumptech.glide:glide:${Versions.UI.GLIDE}"
        const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.UI.GLIDE}"
        const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.UI.RECYCLER_VIEW}"
        const val SUPPORT_DESIGN = "com.android.support:design:${Versions.UI.SUPPORT_DESIGN}"
    }
}