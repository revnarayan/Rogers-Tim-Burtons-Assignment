package com.revnarayan.rogerstimburtonsassignment.network

import com.revnarayan.rogerstimburtonsassignment.di.PRODUCTS_END_POINT
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import retrofit2.http.GET


interface ApiInterface {

    @GET(PRODUCTS_END_POINT)
    suspend fun getProducts(): ProductsResponse

}