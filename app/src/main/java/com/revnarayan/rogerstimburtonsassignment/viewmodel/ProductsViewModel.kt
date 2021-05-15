package com.revnarayan.rogerstimburtonsassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.retrofit.Repository

class ProductsViewModel : ViewModel() {
    private val repository = Repository()
    private val _productList = MutableLiveData<List<Product>>()
    val productsList: LiveData<List<Product>>
        get() = _productList


    init {
        repository.apply {
            getProducts()
            productList.observeForever {
                _productList.postValue(it)
            }
        }
    }

}