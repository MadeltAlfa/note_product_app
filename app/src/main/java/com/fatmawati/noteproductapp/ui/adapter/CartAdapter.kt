package com.fatmawati.noteproductapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.data.model.CartItem
import com.fatmawati.noteproductapp.util.toRupiahFormat

class CartAdapter(
    private val onRemoveClicked: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_cart_item_name)
        private val detailsTextView: TextView = itemView.findViewById(R.id.text_cart_item_details)
        private val removeButton: ImageButton = itemView.findViewById(R.id.btn_remove_from_cart)

        fun bind(cartItem: CartItem) {
            nameTextView.text = cartItem.name
            val pricePerItem = cartItem.price.toRupiahFormat()
            val totalPrice = cartItem.totalPrice.toRupiahFormat()
            val details = "${cartItem.quantity} x $pricePerItem = $totalPrice"
            detailsTextView.text = details

            removeButton.setOnClickListener { onRemoveClicked(cartItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}