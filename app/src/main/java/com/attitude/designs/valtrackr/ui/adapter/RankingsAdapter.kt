package com.attitude.designs.valtrackr.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.RankingsItemBinding
import com.attitude.designs.valtrackr.model.brackets.Team_X
import java.util.*


class RankingsAdapter(private val context: Context): RecyclerView.Adapter<RankingsAdapter.ImageViewHolder>() {
    
    inner class ImageViewHolder(val binding:RankingsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Team_X>() {
        override fun areItemsTheSame(oldItem: Team_X, newItem: Team_X): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Team_X, newItem: Team_X): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Team_X>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            RankingsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        
        holder.binding.apply {
            teamAiv.load(currentItem.image){
                crossfade(true)
                placeholder(R.drawable.valorant_masters_lightmode)
            }
            title.text = currentItem.name
            ordinal.text = currentItem.ordinal
            teamAname.text = currentItem.record.wins.toString()+"W - "+currentItem.record.losses.toString()+"L"
            
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

}