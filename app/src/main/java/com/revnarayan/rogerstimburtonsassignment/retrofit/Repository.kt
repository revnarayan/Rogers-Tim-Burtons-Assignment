package com.revnarayan.rogerstimburtonsassignment.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.revnarayan.rogerstimburtonsassignment.model.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    private val _productsPageUiModel = MutableLiveData<ProductsPageUiModel>()
    val productsPageUiModel: LiveData<ProductsPageUiModel>
        get() = _productsPageUiModel

    fun getProducts() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(MockResponseInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
            .create(ApiInterface::class.java)

        val products = retrofit.getProducts()

        products.enqueue(object : Callback<ProductsResponse> {
            override fun onResponse(
                call: Call<ProductsResponse>,
                response: Response<ProductsResponse>
            ) {
                Log.i("Network call success!!", response.body().toString())
                //this is where you would have the list of products
                if (response.isSuccessful) {
                    val productList: List<Product>? = response.body()?.products
                    val producsUiModels: List<ProductsUiModel>? = productList?.map {
                        it.convertToUiModel()
                    }
                    val productsPageUiModel = ProductsPageUiModel(producsUiModels)
                    _productsPageUiModel.postValue(productsPageUiModel)
                }

            }

            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                Log.e("Network call failure!!", t.message!!)
            }
        })

    }

}