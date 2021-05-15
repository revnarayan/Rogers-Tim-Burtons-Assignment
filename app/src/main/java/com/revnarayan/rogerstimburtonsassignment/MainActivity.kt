package com.revnarayan.rogerstimburtonsassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.recyclerview.ProductsAdapter
import com.revnarayan.rogerstimburtonsassignment.retrofit.ApiInterface
import com.revnarayan.rogerstimburtonsassignment.retrofit.BASE_URL
import com.revnarayan.rogerstimburtonsassignment.retrofit.MockResponseInterceptor
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var productList: List<Product> = listOf()
    private val controller = com.revnarayan.rogerstimburtonsassignment.retrofit.Retrofit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller.getProducts()
        setProductListAdapter(productList)
    }

    private fun setProductListAdapter(productsList: List<Product>) {
        rv_recycler_view.apply {
            setHasFixedSize(true)
            adapter = ProductsAdapter(productsList)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}