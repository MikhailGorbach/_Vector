package com.example.vector.ui.way

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vector.R
import com.example.vector.domain.local.entity.MarkDto
import kotlinx.android.synthetic.main.item_marker.view.descriptionTextView
import kotlinx.android.synthetic.main.item_marker.view.latitudeTextView
import kotlinx.android.synthetic.main.item_marker.view.longitudeTextView
import kotlinx.android.synthetic.main.item_marker.view.titleTextView

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
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_marker, parent, false), mListener, markList)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = markList[position]
        with(holder) {
            itemView.titleTextView.text = currentItem.title
            itemView.descriptionTextView.text = currentItem.description
            itemView.latitudeTextView.text = currentItem.latitude
            itemView.longitudeTextView.text = currentItem.longitude
        }
    }

    override fun getItemCount(): Int {
        return markList.size
    }

    fun setData(mark: List<MarkDto>) {
        this.markList = mark
        notifyDataSetChanged()
    }
}