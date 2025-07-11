package com.fatmawati.noteproductapp.ui.adapter

import android.app.Activity
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.data.model.Product
import com.fatmawati.noteproductapp.ui.view.MainActivity
import com.fatmawati.noteproductapp.util.toRupiahFormat

class ProductAdapter(
    private val onAddToCartClicked: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_product_name)
        private val priceTextView: TextView = itemView.findViewById(R.id.text_product_price)
        private val addToCartButton: Button = itemView.findViewById(R.id.btn_add_to_cart)

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(product: Product) {
            nameTextView.text = product.name
            priceTextView.text = product.price.toRupiahFormat()
            addToCartButton.setOnClickListener { onAddToCartClicked(product) }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            if (v != null && adapterPosition != RecyclerView.NO_POSITION) {
                val activity = itemView.context as? MainActivity
                activity?.setCurrentContextMenuPosition(adapterPosition)

                val inflater: MenuInflater = (itemView.context as Activity).menuInflater
                inflater.inflate(R.menu.product_context_menu, menu)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
