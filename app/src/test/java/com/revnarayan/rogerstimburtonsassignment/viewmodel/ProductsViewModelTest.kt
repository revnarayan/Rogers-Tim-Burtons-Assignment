package com.revnarayan.rogerstimburtonsassignment.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModels
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.repository.ProductsRepository
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith((AndroidJUnit4::class))
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class ProductsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val repository: ProductsRepository = Mockito.mock(ProductsRepository::class.java)

    @Test
    fun getProductContent_providesProductPageUiModel() {
        val productUiModels: List<ProductUiModels> = arrayListOf(
            ProductUiModels(
                "NameTest",
                1.22
            )
        )
        val productPageUiModels = ProductsPageUiModel(productUiModels)

        // Given a fresh ViewModel
        val productsViewModel = ProductsViewModel(repository)
        productsViewModel.getProductContent()

        // Then the new task event is triggered
        val value = productsViewModel.productsPageUiModel.getOrAwaitValue()
        print("value :$value")
        assertEquals(value, productPageUiModels)
    }

}