package com.revnarayan.rogerstimburtonsassignment.network

import com.google.gson.GsonBuilder
import com.revnarayan.rogerstimburtonsassignment.di.PRODUCTS_END_POINT
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import okhttp3.*
import javax.inject.Inject

const val SUCCESS_CODE = 200

class MockResponseInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val mockProducts = arrayListOf(
            Product(id = 1, name = "Coffee Sm", size = "small", cost = 0.99, type = "drink"),
            Product(id = 2, name = "Coffee Med", size = "medium", cost = 1.22, type = "drink"),
            Product(id = 3, name = "Coffee Lg", size = "large", cost = 1.54, type = "drink"),
            Product(
                id = 4,
                name = "Lemon Poppy Seed Cake",
                size = null,
                cost = 0.99,
                type = "food"
            ),
            Product(id = 5, name = "Banana", size = null, cost = 0.99, type = "food")
        )
        val gson = GsonBuilder().create()
        val responseString = when {
            uri.endsWith(PRODUCTS_END_POINT) -> gson.toJson(ProductsResponse(mockProducts))
            else -> ""
        }

        return Response.Builder()
            .body(ResponseBody.create(MediaType.parse("application/json"), responseString))
            .code(SUCCESS_CODE)
            .message("MOCK")
            .protocol(Protocol.HTTP_1_0)
            .request(chain.request())
            .build()
    }
}