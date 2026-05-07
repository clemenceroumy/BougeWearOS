package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

interface PaperTrailService {
    @POST("logs")
    suspend fun log(@Body message: RequestBody): ResponseBody
}

@Singleton
class LogService @Inject constructor() {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.PAPERTRAIL_TOKEN}")
                .addHeader("Content-Type", "application/octet-stream")
                .build()
            chain.proceed(request)
        }
        .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://logs.collector.eu-01.cloud.solarwinds.com/v1/")
        .build()

    private var service: PaperTrailService = retrofit.create(PaperTrailService::class.java)

    fun registerLog(log: String) {
        CoroutineScope(Dispatchers.IO).launch {
            service.log(log.toRequestBody())
        }
    }
}