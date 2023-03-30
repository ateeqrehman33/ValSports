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
import com.attitude.designs.valtrackr.databinding.UpcomingMatchItemBinding
import com.attitude.designs.valtrackr.model.unstarted.UpcomingEvent_
import java.util.*


class UpcomingMatchesAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<UpcomingMatchesAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(val binding:UpcomingMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root)





    private val diffCallback = object : DiffUtil.ItemCallback<UpcomingEvent_>() {
        override fun areItemsTheSame(oldItem: UpcomingEvent_, newItem: UpcomingEvent_): Boolean {
            return oldItem.startTime == newItem.startTime
        }

        override fun areContentsTheSame(oldItem: UpcomingEvent_, newItem: UpcomingEvent_): Boolean {
            return oldItem.startTime == newItem.startTime && oldItem == newItem
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

            var formatedDate : Date = DateUtils.formatDate(currImage.startTime,"yyyy-MM-dd'T'HH:mm:ss'Z'")
            val nowDate : Date = DateUtils.now()
            val daysPassed : Int = DateUtils.getDiffinDays(nowDate,formatedDate)

            if(DateUtils.isSameDaynew(formatedDate,nowDate)){
                dateTitle.text = "Today"
            }
            else if(DateUtils.isSameDaynew(formatedDate,DateUtils.addOneDay(nowDate))){
                dateTitle.text = "Tomorrow"
            }
            else if(daysPassed in 1..4){
                dateTitle.text = ""+DateUtils.formatDate(formatedDate,"EEEE")
            }
            else if(daysPassed in 4..20){
                dateTitle.text = ""+DateUtils.formatDate(formatedDate,"MMM dd yyyy")
            }
            else{
                dateTitle.text = ""+DateUtils.formatDate(formatedDate,"MMM dd yyyy")

            }

            datetime.text = DateUtils.formatDate(formatedDate,"hh:mm aa")

            linearLayout25.onSingleClick(){
                onClickListener.onClick(currImage)
            }

        }
    }

    override fun getItemCount() = differ.currentList.size
    override fun getItemViewType(position: Int): Int {
        return position
    }

    class OnClickListener(val clickListener: (event: UpcomingEvent_) -> Unit) {
        fun onClick(event: UpcomingEvent_) = clickListener(event)
    }
}