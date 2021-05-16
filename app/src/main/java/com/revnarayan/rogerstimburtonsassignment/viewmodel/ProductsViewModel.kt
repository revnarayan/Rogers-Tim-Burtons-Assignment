package com.revnarayan.rogerstimburtonsassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsPageUiModel
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.retrofit.Repository

class ProductsViewModel : ViewModel() {
    private val repository = Repository()
    private val _productsPageUiModel = MutableLiveData<ProductsPageUiModel>()
    val productsPageUiModel: LiveData<ProductsPageUiModel>
        get() = _productsPageUiModel


    init {
        repository.apply {
            getProducts()
            productsPageUiModel.observeForever {
                _productsPageUiModel.postValue(it)
            }
        }
    }

}