package com.fatmawati.noteproductapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.data.model.OrderItem

class OrderItemAdapter : ListAdapter<OrderItem, OrderItemAdapter.OrderItemViewHolder>(OrderItemDiffCallback()) {

    inner class OrderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_item_product_name)
        private val detailsTextView: TextView = itemView.findViewById(R.id.text_item_quantity_price)

        fun bind(orderItem: OrderItem) {
            nameTextView.text = orderItem.productName
            val details = "${orderItem.quantity} x Rp ${orderItem.price}"
            detailsTextView.text = details
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_detail_product, parent, false)
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class OrderItemDiffCallback : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem == newItem
    }
}