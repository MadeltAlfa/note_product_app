package com.fatmawati.noteproductapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.data.model.Order
import com.fatmawati.noteproductapp.util.toRupiahFormat
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(
    private val onOrderClicked: (Order) -> Unit
) : ListAdapter<Order, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallback()) {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdTextView: TextView = itemView.findViewById(R.id.text_order_id)
        private val orderDateTextView: TextView = itemView.findViewById(R.id.text_order_date)
        private val orderTotalTextView: TextView = itemView.findViewById(R.id.text_order_total)

        fun bind(order: Order) {
            orderIdTextView.text = "Order ke-${order.id}"
            orderTotalTextView.text = "Total: Rp ${order.total.toRupiahFormat()}"
            // Format tanggal agar lebih mudah dibaca
            orderDateTextView.text = formatDateTime(order.createdAt)

            itemView.setOnClickListener { onOrderClicked(order) }
        }

        private fun formatDateTime(dateTimeString: String): String {
            return try {
                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
                formatter.format(parser.parse(dateTimeString)!!)
            } catch (e: Exception) {
                dateTimeString // Kembalikan string asli jika format gagal
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class HistoryDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}