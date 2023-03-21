package com.halil.ozel.unsplashexample.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.halil.ozel.unsplashexample.R
import com.halil.ozel.unsplashexample.databinding.RankingsItemBinding
import com.halil.ozel.unsplashexample.model.brackets.Team_X
import java.util.*


class RankingsAdapter(private val context: Context): RecyclerView.Adapter<RankingsAdapter.ImageViewHolder>() {

    private var mContext: Context = context

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
        val currImage = differ.currentList[position]
        
        holder.binding.apply {


            teamAiv.load(currImage.image){
                crossfade(true)
                placeholder(R.drawable.valorant_masters_lightmode)
            }

            title.text = currImage.name

            ordinal.text = currImage.ordinal

            teamAname.text = currImage.record.wins.toString()+"W - "+currImage.record.losses.toString()+"L"

       
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

}