package com.intentsoft.matnuz.api

import com.intentsoft.matnuz.utils.Constants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AppInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer " + Constants.TOKEN)
            .build()

        return chain.proceed(request)
    }
}