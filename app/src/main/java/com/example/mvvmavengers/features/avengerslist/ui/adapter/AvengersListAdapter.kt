package com.example.mvvmavengers.features.avengerslist.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmavengers.features.avengerslist.domain.entities.Result
import com.example.mvvmavengers.R
import com.example.mvvmavengers.databinding.ActivityAvengersListItemBinding
import com.example.mvvmavengers.features.avengerslist.ui.adapter.AvengersListAdapter.AvengersListItemViewHolder
import com.example.mvvmavengers.utils.inflate
import com.example.mvvmavengers.utils.loadUrlWithCircleCrop
import kotlin.properties.Delegates

private typealias AvengerListener = (Result) -> Unit

class AvengersListAdapter(avengers: List<Result> = emptyList(), private val listener: AvengerListener) :
    RecyclerView.Adapter<AvengersListItemViewHolder>() {

    var avengers by Delegates.observable(avengers) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvengersListItemViewHolder {
        val view = parent.inflate(R.layout.activity_avengers_list_item)

        return AvengersListItemViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: AvengersListItemViewHolder, position: Int) {
        holder.bind(avengers[position])
    }

    override fun getItemCount(): Int {
        return avengers.size
    }

    class AvengersListItemViewHolder(itemView: View, private val listener: AvengerListener) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ActivityAvengersListItemBinding.bind(itemView)

        fun bind(avenger: Result) {
            val imageUrl = avenger.thumbnail?.path + "." + avenger.thumbnail?.extension
            binding.apply {
                avengersListItemTxt.text = avenger.name
                avengersListItemImg.loadUrlWithCircleCrop(imageUrl)
            }
            binding.root.setOnClickListener { listener(avenger) }
        }
    }
}
