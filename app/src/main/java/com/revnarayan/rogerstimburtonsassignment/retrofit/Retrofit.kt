package com.revnarayan.rogerstimburtonsassignment.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
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
                //this is where youd have the list of products
            }

            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                Log.e("Network call failure!!", t.message!!)
            }
        })

    }

}