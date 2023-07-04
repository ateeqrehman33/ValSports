package com.attitude.designs.valtrackr.ui.adapter

import DateUtils
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cc.taylorzhang.singleclick.onSingleClick
import coil.load
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.NewsItemBinding
import com.attitude.designs.valtrackr.model.news.Entry
import com.github.sisyphsu.dateparser.DateParserUtils
import java.util.*


class NewsAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<NewsAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(val binding:NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Entry>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            NewsItemBinding.inflate(
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

            title.text = currentItem.title

            banneriv.load(currentItem.bannerSettings.banner?.url){
                crossfade(true)
                placeholder(R.drawable.valorant_masters_lightmode)
            }

            val date = DateParserUtils.parseDate(currentItem.bannerSettings.banner?.publishDetails?.time)
            datee.text = ""+DateUtils.formatDate(date,"MMM dd yyyy")
            
            holder.itemView.onSingleClick(){
                onClickListener.onClick(currentItem)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class OnClickListener(val clickListener: (event: Entry) -> Unit) {
        fun onClick(event: Entry) = clickListener(event)
    }

}