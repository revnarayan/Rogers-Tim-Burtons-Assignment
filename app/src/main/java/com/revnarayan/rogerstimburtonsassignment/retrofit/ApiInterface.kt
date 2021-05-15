package com.revnarayan.rogerstimburtonsassignment.retrofit

import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import retrofit2.Call
import retrofit2.http.GET


const val BASE_URL = "https://api.timburtons.com/"
const val PRODUCTS_END_POINT = "v1/products"

interface ApiInterface {

    @GET(PRODUCTS_END_POINT)
    fun getProducts(): Call<ProductsResponse>

}