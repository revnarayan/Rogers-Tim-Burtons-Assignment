package com.revnarayan.rogerstimburtonsassignment.repositorty

import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.network.ApiInterface
import com.revnarayan.rogerstimburtonsassignment.network.Try
import com.revnarayan.rogerstimburtonsassignment.repository.ProductsRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

class ProductsRepositoryTest {
    private val productsApiInterface: ApiInterface = Mockito.mock(ApiInterface::class.java)
    private val repository = ProductsRepository(productsApiInterface)

    private val productList: List<Product> = listOf(
        Product(
            1,
            "Coffee",
            "Small",
            2.99,
            "Drink"
        ),
        Product(
            2,
            "Coffee",
            "Medium",
            3.99,
            "Drink"
        )
    )

    private val productsList: ProductsResponse = ProductsResponse(productList)
    private val productListResponse = Response.success(productsList)

    @Test
    fun testRequestProductList() {
        runBlocking {
            Mockito.`when`(productsApiInterface.getProducts())
                .thenReturn(productListResponse.body())

            val productUiModels: List<ProductUiModel> = arrayListOf(
                ProductUiModel(
                    "Coffee",
                    2.99
                ),
                ProductUiModel(
                    "Coffee",
                    3.99
                )
            )

            val result = repository.getProducts()

            assertEquals(result, productUiModels)
        }
    }

    // Verify API calls success within coroutine scope
    @Test
    fun testWrapResult_Success() = runBlocking {
        Mockito.`when`(productsApiInterface.getProducts()).thenReturn(productListResponse.body())

        val result = repository.wrapResult {
            repository.getProducts()
        }
        Assert.assertThat(result, CoreMatchers.instanceOf(Try.Success::class.java))
    }

    // Verify API calls failure within coroutine scope and the result is null
    @Test
    fun testWrapResult_Failure() = runBlocking {

        Mockito.`when`(productsApiInterface.getProducts()).thenThrow(HttpException::class.java)

        val result = repository.getProducts()

        Assert.assertNull(result)
    }
}


