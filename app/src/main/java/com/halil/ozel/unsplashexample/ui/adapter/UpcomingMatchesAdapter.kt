package com.halil.ozel.unsplashexample.ui.adapter

import DateUtils
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.halil.ozel.unsplashexample.R
import com.halil.ozel.unsplashexample.databinding.UpcomingMatchItemBinding
import com.halil.ozel.unsplashexample.model.unstarted.UpcomingEvent_
import java.util.*


class UpcomingMatchesAdapter : RecyclerView.Adapter<UpcomingMatchesAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(val binding:UpcomingMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<UpcomingEvent_>() {
        override fun areItemsTheSame(oldItem: UpcomingEvent_, newItem: UpcomingEvent_): Boolean {
            return oldItem.league.id == newItem.league.id
        }

        override fun areContentsTheSame(oldItem: UpcomingEvent_, newItem: UpcomingEvent_): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<UpcomingEvent_>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            UpcomingMatchItemBinding.inflate(
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

            title.text = currImage.league.name


                teamAname.text = currImage.match.teams[0].code
                teamBname.text = currImage.match.teams[1].code

                teamAiv.load(currImage.match.teams[0].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                    //transformations(CircleCropTransformation())

                }
                teamABiv.load(currImage.match.teams[1].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                    ///transformations(CircleCropTransformation())
                }

            val formatedDate : Date = DateUtils.formatDate(currImage.startTime,"yyyy-MM-dd'T'HH:mm:ss'Z'")
            val nowDate : Date = DateUtils.now()
            val daysPassed : Int = DateUtils.getDiffinDays(nowDate,formatedDate)

            if(DateUtils.isSameDaynew(formatedDate,nowDate)){
                dateTitle.text = "Today"
            }
            else if(DateUtils.isSameDaynew(formatedDate,DateUtils.addOneDay(nowDate))){
                dateTitle.text = "Tomorrow"
            }
            else if(daysPassed in 2..4){
                dateTitle.text = ""+DateUtils.formatDate(formatedDate,"EEEE")
            }
            else{
                dateTitle.text = ""+DateUtils.formatDate(formatedDate,"MMM dd yyyy")

            }

            datetime.text = DateUtils.formatDate(formatedDate,"HH:mm aa")

        }
    }

    override fun getItemCount() = differ.currentList.size

}