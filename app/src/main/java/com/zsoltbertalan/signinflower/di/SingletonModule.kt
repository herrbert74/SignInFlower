package com.zsoltbertalan.signinflower.di

import android.content.Context
import android.net.ConnectivityManager
import com.zsoltbertalan.signinflower.data.local.Prefs
import com.zsoltbertalan.signinflower.data.local.PrefsAccessor
import com.zsoltbertalan.signinflower.data.local.SIGN_IN_FLOWER_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	internal fun provideConnectivityManager(
		@ApplicationContext context: Context
	): ConnectivityManager {
		return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	}

	@DefaultDispatcher
	@Provides
	fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

	@IoDispatcher
	@Provides
	fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

	@MainDispatcher
	@Provides
	fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

	@Provides
	fun providePrefs(@ApplicationContext context: Context): Prefs =
		PrefsAccessor(context.getSharedPreferences(SIGN_IN_FLOWER_PREFS, Context.MODE_PRIVATE))

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher
