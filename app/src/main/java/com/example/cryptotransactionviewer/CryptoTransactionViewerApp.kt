package com.example.cryptotransactionviewer


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for CryptoTransactionViewerApp app.
 * This class is annotated with @HiltAndroidApp to enable Hilt dependency injection.
 * It must be referenced in the AndroidManifest.xml file with the android:name attribute.
 */
@HiltAndroidApp
class CryptoTransactionViewerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any application-wide components here if needed
        // For example: logging, crash reporting, analytics, etc.
    }


}