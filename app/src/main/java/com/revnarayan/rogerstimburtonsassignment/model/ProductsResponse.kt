package com.revnarayan.rogerstimburtonsassignment.model

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products") val products : List<Product>
)