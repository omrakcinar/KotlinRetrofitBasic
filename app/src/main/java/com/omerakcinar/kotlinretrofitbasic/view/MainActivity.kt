package com.omerakcinar.kotlinretrofitbasic.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.omerakcinar.kotlinretrofitbasic.adapter.RecyclerViewAdapter
import com.omerakcinar.kotlinretrofitbasic.databinding.ActivityMainBinding
import com.omerakcinar.kotlinretrofitbasic.model.CryptoModel
import com.omerakcinar.kotlinretrofitbasic.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private var adapter : RecyclerViewAdapter? = null
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("ERROR : ${throwable.localizedMessage}")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // BASE : https://raw.githubusercontent.com/
        // atilsamancioglu/K21-JSONDataSet/master/crypto.json

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        loadData()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoModels?.let {
                            adapter = RecyclerViewAdapter(it)
                            binding.recyclerView.adapter = adapter
                        }
                    }
                }
            }
        }


        /*
        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

         */

        /*
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        adapter = RecyclerViewAdapter(cryptoModels!!)
                        binding.recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
        */
    }

    private fun handleResponse(cryptoList : List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            adapter = RecyclerViewAdapter(it)
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}