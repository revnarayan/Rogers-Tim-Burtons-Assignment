package com.revnarayan.rogerstimburtonsassignment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.revnarayan.rogerstimburtonsassignment.databinding.FragmentProductsBinding
import com.revnarayan.rogerstimburtonsassignment.model.ProductUiModel
import com.revnarayan.rogerstimburtonsassignment.recyclerview.ProductsAdapter
import com.revnarayan.rogerstimburtonsassignment.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val myViewModel: ProductsViewModel by activityViewModels()
    private var productList: List<ProductUiModel>? = listOf()

    @Inject
    lateinit var productsAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductsBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.unfilteredProductUiModels.observe(viewLifecycleOwner){
            productList = it
            updateProductList()
        }
        rv_recycler_view.apply {
            setHasFixedSize(true)
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(activity?.applicationContext)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.menu_main, menu)

    private fun updateProductList() {
        productsAdapter.apply {
            productItems = productList
            notifyDataSetChanged()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_icon -> {
                findNavController().navigate(R.id.action_productsFragment_to_searchItemFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}