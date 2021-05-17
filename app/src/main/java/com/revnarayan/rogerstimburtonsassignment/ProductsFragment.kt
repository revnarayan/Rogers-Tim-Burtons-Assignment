package com.revnarayan.rogerstimburtonsassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.revnarayan.rogerstimburtonsassignment.databinding.FragmentProductsBinding
import com.revnarayan.rogerstimburtonsassignment.model.ProductsUiModel
import com.revnarayan.rogerstimburtonsassignment.recyclerview.ProductsAdapter
import com.revnarayan.rogerstimburtonsassignment.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val myViewModel: ProductsViewModel by activityViewModels()
    private var productsList: List<ProductsUiModel>? = listOf()

    @Inject
    lateinit var productsAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.productsPageUiModel.observe(viewLifecycleOwner, Observer {
            productsList = it.productsUiModel
            productsAdapter.apply {
                productItems = productsList
                notifyDataSetChanged()
            }
        })
        rv_recycler_view.apply {
            setHasFixedSize(true)
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(activity?.applicationContext)
        }

    }
}