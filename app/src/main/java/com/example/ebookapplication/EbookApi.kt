package com.example.ebookapplication

import com.example.example.EbookApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


class EbookApi {
    interface EbookService {
        // For testing https://www.googleapis.com/books/v1/"
        @GET("volumes")

        fun getEbooks( @Query("q") criteria: String):
                Call<EbookApiResponse>
    }

    private val baseUrl = "https://www.googleapis.com/books/v1/"

    fun getClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}