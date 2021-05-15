package com.revnarayan.rogerstimburtonsassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import com.revnarayan.rogerstimburtonsassignment.recyclerview.ProductsAdapter
import com.revnarayan.rogerstimburtonsassignment.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_products.*


class ProductsFragment : Fragment() {

    private val myViewModel: ProductsViewModel by activityViewModels()
    private val productList : List<Product> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.productsList.observe(viewLifecycleOwner, Observer {
            setProductListAdapter(it)
        })
    }

    private fun setProductListAdapter(productsList: List<Product>) {
        rv_recycler_view.apply {
            setHasFixedSize(true)
            adapter = ProductsAdapter(productsList)
            layoutManager = LinearLayoutManager(activity?.applicationContext)
        }
    }

}