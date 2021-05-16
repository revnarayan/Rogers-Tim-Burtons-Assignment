package com.revnarayan.rogerstimburtonsassignment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel
import com.revnarayan.rogerstimburtonsassignment.repository.ProductsRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ActivityScoped
class ProductsViewModel @ViewModelInject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {

    private val _productsPageUiModel = MutableLiveData<ProductsPageUiModel>()
    val productsPageUiModel: LiveData<ProductsPageUiModel>
        get() = _productsPageUiModel

    init {
        getProductContent()
    }

    private fun getProductContent() = liveData(Dispatchers.IO) {
        emit(
            withContext(Dispatchers.IO) {
                productsRepository.getProducts()
            })
    }.observeForever { result ->
        val currentProductPageUiModel = _productsPageUiModel.value
        val currentProductsUiModel = currentProductPageUiModel?.productsUiModel
        val productsUiModel = result?.productsUiModel

        val mergedUiModels = mutableListOf<ProductsUiModel>().apply {
            if (currentProductsUiModel != null) addAll(currentProductsUiModel)
            if (productsUiModel != null) addAll(productsUiModel)
        }
        val mergedUiModel = ProductsPageUiModel(mergedUiModels)
        _productsPageUiModel.postValue(mergedUiModel)

    }
}