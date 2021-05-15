package com.revnarayan.rogerstimburtonsassignment.recyclerview

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revnarayan.rogerstimburtonsassignment.R
import com.revnarayan.rogerstimburtonsassignment.model.Product
import com.revnarayan.rogerstimburtonsassignment.model.ProductsResponse
import kotlinx.android.synthetic.main.product_list_row_item.view.*

class ProductsAdapter(private val productList: List<ProductsResponse>?) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_row_item, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductsViewHolder, position: Int) {
     productList?.run {
         val item = this[position]
         holder.itemView.tv_name.text = item.name
         holder.itemView.tv_cost.text = item.cost.toString()
     }
    }

    override fun getItemCount(): Int = productList?.size ?: 0

    inner class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}