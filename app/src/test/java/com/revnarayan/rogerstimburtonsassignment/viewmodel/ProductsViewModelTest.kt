package com.revnarayan.rogerstimburtonsassignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModel
import com.revnarayan.rogerstimburtonsassignment.repository.ProductsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ProductsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var productsListObserver: Observer<List<ProductUiModel>>

    @Mock
    private lateinit var repository: ProductsRepository

    private val unfilteredProductUiModels: List<ProductUiModel> = arrayListOf(
        ProductUiModel(
            "Coffee",
            1.22
        ),
        ProductUiModel(
            "Tea",
            1.25
        )
    )

    @Test
    fun getProductContent_providesProductUiModels() {

        testCoroutineRule.runBlockingTest {
            doReturn(unfilteredProductUiModels).`when`(repository).getProducts()
            val productsViewModel = ProductsViewModel(repository)
            productsViewModel.unfilteredProductUiModels.observeForever(productsListObserver)
            verify(repository).getProducts()
            verify(productsListObserver).onChanged(unfilteredProductUiModels)
            productsViewModel.unfilteredProductUiModels.removeObserver(productsListObserver)
        }
    }


    @Test
    fun executeSearch_providesFilteredProductUiModels() {
        testCoroutineRule.runBlockingTest {
            doReturn(unfilteredProductUiModels).`when`(repository).getProducts()
            val productsViewModel = ProductsViewModel(repository)
            productsViewModel.filteredProductUiModels.observeForever(productsListObserver)

            productsViewModel.executeSearch("c")
            delay(500)

            verify(productsListObserver).onChanged(anyList())
            productsViewModel.filteredProductUiModels.removeObserver(productsListObserver)
        }
    }
}