package com.example.madeinbrasil.api

import com.example.madeinbrasil.model.search.serie.SearchSerieDataSourceFactory
import com.example.madeinbrasil.utils.Constants.Api.API_AUTH_NAME
import com.example.madeinbrasil.utils.Constants.Api.API_AUTH_VALUE
import com.example.madeinbrasil.utils.Constants.Api.API_CONTENT_TYPE_NAME
import com.example.madeinbrasil.utils.Constants.Api.API_CONTENT_TYPE_VALUE
import com.example.madeinbrasil.utils.Constants.Api.BASE_URL_v3
import com.example.madeinbrasil.utils.Constants.Api.QUERY_PARAM_APPEND_LABEL
import com.example.madeinbrasil.utils.Constants.Api.QUERY_PARAM_APPEND_VALUE
import com.example.madeinbrasil.utils.Constants.Api.QUERY_PARAM_APPEND_VALUE_PERSON
import com.example.madeinbrasil.utils.Constants.Api.QUERY_PARAM_LANGUAGE_LABEL
import com.example.madeinbrasil.utils.Constants.Api.QUERY_PARAM_LANGUAGE_VALUE
import com.example.madeinbrasil.utils.Constants.Api.QUERY_PARAM_REGION_LABEL
import com.example.madeinbrasil.utils.Constants.Api.QUERY_PARAM_REGION_VALUE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object APIService {

    val tmdbApi: TmdbAPI = getTMDbApiClient().create(TmdbAPI::class.java)
    val tmdbApiSearch: TmdbAPI = getTMDbApiClientSearch().create(TmdbAPI::class.java)
    val tmdbApiMovieDetailed: TmdbAPI= getTMDbApiClientMovieDetailed().create(TmdbAPI::class.java)
    val tmdbApiPerson: TmdbAPI = getTMDbApiClientPersonDetailed().create(TmdbAPI::class.java)
    val tmdbApiDiscoverTv: TmdbAPI = getTMDbApiClientDiscoverTv().create(TmdbAPI::class.java)

    private fun getTMDbApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_v3)
            .client(getInterceptorClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getTMDbApiClientDiscoverTv(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL_v3)
                .client(getInterceptorClientDiscoverTv())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getInterceptorClientDiscoverTv(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                            .addHeader(API_AUTH_NAME, API_AUTH_VALUE)
                            .addHeader(API_CONTENT_TYPE_NAME, API_CONTENT_TYPE_VALUE)
                            .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor { chain ->
                    val url = chain.request().url().newBuilder()
                            .addQueryParameter(QUERY_PARAM_LANGUAGE_LABEL, QUERY_PARAM_LANGUAGE_VALUE)
                            .build()
                    val newRequest = chain.request().newBuilder().url(url).build()
                    chain.proceed(newRequest)
                }
        return interceptor.build()
    }

    private fun getInterceptorClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(API_AUTH_NAME, API_AUTH_VALUE)
                    .addHeader(API_CONTENT_TYPE_NAME, API_CONTENT_TYPE_VALUE)
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor { chain ->
                val url = chain.request().url().newBuilder()
                    .addQueryParameter(QUERY_PARAM_LANGUAGE_LABEL, QUERY_PARAM_LANGUAGE_VALUE)
                    .addQueryParameter(QUERY_PARAM_REGION_LABEL, QUERY_PARAM_REGION_VALUE)
                    .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                chain.proceed(newRequest)
            }
        return interceptor.build()
    }

    private fun getTMDbApiClientSearch(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL_v3)
                .client(getInterceptorClientSearch())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getTMDbApiClientMovieDetailed(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_v3)
            .client(getInterceptorClientMovieDetailed())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun getTMDbApiClientPersonDetailed(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL_v3)
                .client(getInterceptorClientPersonDetailed())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }



    private fun getInterceptorClientSearch(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                            .addHeader(API_AUTH_NAME, API_AUTH_VALUE)
                            .addHeader(API_CONTENT_TYPE_NAME, API_CONTENT_TYPE_VALUE)
                            .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor { chain ->
                    val url = chain.request().url().newBuilder()
                            .addQueryParameter(QUERY_PARAM_LANGUAGE_LABEL, QUERY_PARAM_LANGUAGE_VALUE)
                            .build()
                    val newRequest = chain.request().newBuilder().url(url).build()
                    chain.proceed(newRequest)
                }
        return interceptor.build()
    }

    private fun getInterceptorClientMovieDetailed(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                            .addHeader(API_AUTH_NAME, API_AUTH_VALUE)
                            .addHeader(API_CONTENT_TYPE_NAME, API_CONTENT_TYPE_VALUE)
                            .build()
                    chain.proceed(newRequest)
                }
                .addInterceptor { chain ->
                    val url = chain.request().url().newBuilder()
                            .addQueryParameter(QUERY_PARAM_LANGUAGE_LABEL, QUERY_PARAM_LANGUAGE_VALUE)
                            .addQueryParameter(QUERY_PARAM_APPEND_LABEL, QUERY_PARAM_APPEND_VALUE)
                            .addQueryParameter(QUERY_PARAM_REGION_LABEL, QUERY_PARAM_REGION_VALUE)

                            .build()
                    val newRequest = chain.request().newBuilder().url(url).build()
                    chain.proceed(newRequest)
                }
        return interceptor.build()
    }

    private fun getInterceptorClientPersonDetailed(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader(API_AUTH_NAME, API_AUTH_VALUE)
                    .addHeader(API_CONTENT_TYPE_NAME, API_CONTENT_TYPE_VALUE)
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor { chain ->
                val url = chain.request().url().newBuilder()
                    .addQueryParameter(QUERY_PARAM_LANGUAGE_LABEL, QUERY_PARAM_LANGUAGE_VALUE)
                    .addQueryParameter(QUERY_PARAM_APPEND_LABEL, QUERY_PARAM_APPEND_VALUE_PERSON)
                    .addQueryParameter(QUERY_PARAM_REGION_LABEL, QUERY_PARAM_REGION_VALUE)

                    .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                chain.proceed(newRequest)
            }
        return interceptor.build()
    }




}