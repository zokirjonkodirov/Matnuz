package com.intentsoft.matnuz.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.intentsoft.matnuz.BuildConfig
import com.intentsoft.matnuz.api.AppInterceptor
import com.intentsoft.matnuz.api.MatnApi
import com.intentsoft.matnuz.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        okHttpClient.addInterceptor(logging)
        okHttpClient.addInterceptor(AppInterceptor())

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(ChuckerInterceptor.Builder(context).build())
        }

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetroft(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMatnApi(retrofit: Retrofit): MatnApi {
        return retrofit.create(MatnApi::class.java)
    }
}