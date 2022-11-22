package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.data.Casting
import com.example.moviesapp.databinding.CastingListItemCellBinding

class CastingAdapter : ListAdapter<Casting, RecyclerView.ViewHolder>(CastingDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            CastingListItemCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CastingViewHolder).bind(getItem(position))
    }


    class CastingViewHolder(private val binding: CastingListItemCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(casting: Casting) {
            Glide
                .with(binding.imgCasting.context)
                .load(casting.profileImage)
                .centerCrop()
                .into(binding.imgCasting)
        }
    }

}


class CastingDiffCallBack : DiffUtil.ItemCallback<Casting>() {

    override fun areItemsTheSame(oldItem: Casting, newItem: Casting): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Casting, newItem: Casting): Boolean {
        return oldItem == newItem
    }
}
