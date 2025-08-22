package com.app.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.app.barcodescanner.scanner.domain.BarcodeAnalyzerFactory
import com.app.barcodescanner.scanner.domain.GS1ParserFactory
import com.app.barcodescanner.scanner.peristance.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }


    @Provides
    @Singleton
    fun provideParserFactory(): GS1ParserFactory {
        return GS1ParserFactory()
    }

    @Provides
    @Singleton
    fun provideBarcodeAnalyzerFactory(): BarcodeAnalyzerFactory {
        return BarcodeAnalyzerFactory()
    }
}