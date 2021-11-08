package com.example.vector.ui.way

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vector.R
import com.example.vector.domain.local.entity.MarkDto
import kotlinx.android.synthetic.main.custom_row_for_recyclerview.view.descriptionTextView
import kotlinx.android.synthetic.main.custom_row_for_recyclerview.view.idTextView
import kotlinx.android.synthetic.main.custom_row_for_recyclerview.view.titleTextView

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var markList = emptyList<MarkDto>()
    private var counter = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_for_recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = markList[position]
        counter++
        holder.itemView.idTextView.text = counter.toString()
        holder.itemView.titleTextView.text = currentItem.title
        holder.itemView.descriptionTextView.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return markList.size
    }

    fun setData(mark: List<MarkDto>) {
        this.markList = mark
        notifyDataSetChanged()
    }
}