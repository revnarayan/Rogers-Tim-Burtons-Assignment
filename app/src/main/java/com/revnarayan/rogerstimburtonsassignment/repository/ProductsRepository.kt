package com.revnarayan.rogerstimburtonsassignment.repository

import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModel
import com.revnarayan.rogerstimburtonsassignment.network.ApiInterface
import com.revnarayan.rogerstimburtonsassignment.network.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ProductsRepository @Inject constructor(private val apiInterface: ApiInterface) {

    open suspend fun getProductsPageUiModel(): List<ProductUiModel>? {
        val tryProductResponse: Try<ProductsResponse> = wrapResult {
            apiInterface.getProducts()
        }
        return when (tryProductResponse) {
            is Try.Success -> {
                val productList = tryProductResponse.value
                val productUiModels: List<ProductUiModel>? = productList.products?.map {
                    it.convertToUiModel()
                }
                productUiModels
            }
            is Try.Failure<*> -> null
        }
    }

    private fun Product.convertToUiModel(): ProductUiModel = ProductUiModel(
        name,
        cost
    )

    suspend fun <T> wrapResult(apiCall: suspend () -> T): Try<T> {
        return try {
            Try.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            Try.Failure(throwable)
        }
    }
}