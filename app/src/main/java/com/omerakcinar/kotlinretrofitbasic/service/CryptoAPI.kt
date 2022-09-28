package com.omerakcinar.kotlinretrofitbasic.service

import com.omerakcinar.kotlinretrofitbasic.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import java.util.*

interface CryptoAPI {
    // BASE : https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData(): Response<List<CryptoModel>>
}