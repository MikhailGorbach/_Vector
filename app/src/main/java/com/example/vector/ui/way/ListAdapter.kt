package com.example.vector.ui.way

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vector.R
import com.example.vector.domain.local.entity.MarkDto
import kotlinx.android.synthetic.main.custom_row_for_recyclerview.view.descriptionTextView
import kotlinx.android.synthetic.main.custom_row_for_recyclerview.view.titleTextView

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var markList = emptyList<MarkDto>()
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(markDto: MarkDto)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener, mark: List<MarkDto>) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(mark[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_for_recyclerview, parent, false), mListener, markList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = markList[position]
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