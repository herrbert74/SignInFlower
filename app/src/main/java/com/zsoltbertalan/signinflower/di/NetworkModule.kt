package com.zsoltbertalan.signinflower.di

import com.zsoltbertalan.signinflower.BASE_URL
import com.zsoltbertalan.signinflower.data.local.Prefs
import com.zsoltbertalan.signinflower.data.network.SignInFlowerAccessor
import com.zsoltbertalan.signinflower.data.network.SignInFlowerRepository
import com.zsoltbertalan.signinflower.data.network.SignInFlowerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Provides
	@Singleton
	internal fun provideSignInFlowerRetrofit(): Retrofit {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY

		val httpClient = OkHttpClient.Builder()

		httpClient.addInterceptor(logging)
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
	}

	@Provides
	@Singleton
	internal fun provideSignInFlowerService(retroFit: Retrofit): SignInFlowerService {
		return retroFit.create(SignInFlowerService::class.java)
	}

	@Provides
	@Singleton
	fun provideSignInFlowerRepository(
		signInFlowerService: SignInFlowerService,
		prefs: Prefs
	): SignInFlowerRepository {
		return SignInFlowerAccessor(prefs)
	}

}
