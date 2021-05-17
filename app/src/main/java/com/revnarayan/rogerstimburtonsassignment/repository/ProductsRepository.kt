package com.revnarayan.rogerstimburtonsassignment.repository

import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModels
import com.revnarayan.rogerstimburtonsassignment.network.ApiInterface
import com.revnarayan.rogerstimburtonsassignment.network.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ProductsRepository @Inject constructor(private val apiInterface: ApiInterface) {

    open suspend fun getProductsPageUiModel(): ProductsPageUiModel? {
        val tryProductResponse: Try<ProductsResponse> = wrapResult {
            apiInterface.getProducts()
        }
        return when (tryProductResponse) {
            is Try.Success -> {
                val productList = tryProductResponse.value
                val productUiModels: List<ProductUiModels>? = productList.products?.map {
                    it.convertToUiModel()
                }
                ProductsPageUiModel(productUiModels)
            }
            is Try.Failure<*> -> null
        }
    }
    private fun Product.convertToUiModel(): ProductUiModels = ProductUiModels(
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