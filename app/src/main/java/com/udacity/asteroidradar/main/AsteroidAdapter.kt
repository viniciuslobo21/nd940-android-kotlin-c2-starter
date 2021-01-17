package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.domain.AsteroidDomain

class AsteroidAdapter(val onClickListener: (AsteroidDomain) -> Unit) :
    ListAdapter<AsteroidDomain, AsteroidAdapter.ItemAsteroidViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<AsteroidDomain>() {
        override fun areItemsTheSame(oldItem: AsteroidDomain, newItem: AsteroidDomain): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AsteroidDomain, newItem: AsteroidDomain): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ItemAsteroidViewHolder(private var binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            asteroid: AsteroidDomain,
            onClickListener: (AsteroidDomain) -> Unit
        ) {
            binding.asteroid = asteroid
            binding.root.setOnClickListener { onClickListener(asteroid) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ItemAsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
                return ItemAsteroidViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAsteroidViewHolder {
        return ItemAsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemAsteroidViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}
