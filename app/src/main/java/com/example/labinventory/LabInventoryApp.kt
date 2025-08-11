package com.example.labinventory

//config: A Kotlin object (likely in util/config.kt) that holds your Supabase URL & Key securely (probably pulled from BuildConfig or a secure source).
//appModule: Your main Koin module for app-level dependencies (like ViewModels, Repositories, etc.).
//supabaseModule: The DI module for setting up your Supabase client.
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.labinventory.util.config
import com.example.labinventory.di.appModule
import com.example.labinventory.di.supabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

//Application - base class for maintaining global application state in Android. - acts as the enry point for the app
class LabInventoryApp : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LabInventoryApp)        // Supplies the app-level context for DI —
            modules(    //Registers your DI modules
                supabaseModule(
                config.SUPABASE_URL,
                config.SUPABASE_KEY),
                appModule
            )
        }
    }
}
