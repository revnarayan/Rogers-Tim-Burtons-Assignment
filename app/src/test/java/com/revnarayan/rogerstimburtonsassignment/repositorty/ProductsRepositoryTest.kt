package com.revnarayan.rogerstimburtonsassignment.repositorty

import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel
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
            "NameTest",
            "Small",
            2.99,
            "TypeTest"
        )
    )

    private val productsList: ProductsResponse = ProductsResponse(productList)
    private val productListResponse = Response.success(productsList)

    @Test
    fun testRequestProductList() {
        runBlocking {
            Mockito.`when`(productsApiInterface.getProducts())
                .thenReturn(productListResponse.body())

            val productsUiModel: List<ProductsUiModel> = arrayListOf(
                ProductsUiModel(
                    "NameTest",
                    2.99
                )
            )
            val productsPageUiModel = ProductsPageUiModel(productsUiModel)

            val result = repository.getProductsPageUiModel()

            assertEquals(result, productsPageUiModel)


        }
    }

    // Verify API calls success within coroutine scope
    @Test
    fun testWrapResult_Success() = runBlocking {

        Mockito.`when`(productsApiInterface.getProducts()).thenReturn(productListResponse.body())

        val result = repository.wrapResult {
            repository.getProductsPageUiModel()
        }
        Assert.assertThat(result, CoreMatchers.instanceOf(Try.Success::class.java))
    }

    // Verify API calls failure within coroutine scope and the result is null
    @Test
    fun testWrapResult_Failure() = runBlocking {

        Mockito.`when`(productsApiInterface.getProducts()).thenThrow(HttpException::class.java)

        val result = repository.getProductsPageUiModel()

        Assert.assertNull(result)
    }
}


