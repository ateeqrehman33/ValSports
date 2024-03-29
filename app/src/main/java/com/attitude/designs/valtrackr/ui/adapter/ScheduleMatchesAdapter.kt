package com.attitude.designs.valtrackr.ui.adapter

import DateUtils
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.ScheduleMatchItemBinding
import com.attitude.designs.valtrackr.model.allschedule.Event_
import java.util.*


class ScheduleMatchesAdapter : RecyclerView.Adapter<ScheduleMatchesAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(val binding:ScheduleMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<Event_>() {
        override fun areItemsTheSame(oldItem: Event_, newItem: Event_): Boolean {
            return oldItem.blockName == newItem.blockName
        }

        override fun areContentsTheSame(oldItem: Event_, newItem: Event_): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Event_>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ScheduleMatchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        val context = holder.itemView.context


        holder.binding.apply {

            title.text = currentItem.league.name

            try {

                teamAname.text = currentItem.match.teams[0].code
                teamBname.text = currentItem.match.teams[1].code

                teamAiv.load(currentItem.match.teams[0].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                }
                teamABiv.load(currentItem.match.teams[1].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                }

                val formatedDate : Date = DateUtils.formatDate(currentItem.startTime,"yyyy-MM-dd'T'HH:mm:ss'Z'")
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
                else if(daysPassed in 4..10){
                    dateTitle.text = ""+DateUtils.formatDate(formatedDate,"MMM dd")
                }
                else{
                    dateTitle.text = ""+DateUtils.formatDate(formatedDate,"MMM dd")

                }

                datetime.text = DateUtils.formatDate(formatedDate,"hh:mm aa")
                dateYear.text = DateUtils.getYear(formatedDate).toString()

                if(currentItem.state == "completed"){
                    scoreCons.visibility = View.VISIBLE
                    viewline.setBackgroundColor(ContextCompat.getColor(context,R.color.md_red_400))

                    teamAscore.text = currentItem.match.teams[0].result?.gameWins.toString()
                    teamBscore.text = currentItem.match.teams[1].result?.gameWins.toString()

                    if(currentItem.match.teams[0].result?.outcome.equals("win")){
                        teamAWin.setColorFilter(ContextCompat.getColor(context, R.color.md_teal_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                    else if(currentItem.match.teams[0].result?.outcome.equals("loss")){
                        teamAWin.setColorFilter(ContextCompat.getColor(context, R.color.md_red_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    }

                    if(currentItem.match.teams[1].result?.outcome.equals("win")){
                        teamBWin.setColorFilter(ContextCompat.getColor(context, R.color.md_teal_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                    else if(currentItem.match.teams[1].result?.outcome.equals("loss")){
                        teamBWin.setColorFilter(ContextCompat.getColor(context, R.color.md_red_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    }

                }
                else{
                    scoreCons.visibility = View.GONE
                    viewline.setBackgroundColor(ContextCompat.getColor(context,R.color.md_teal_400))

                }

            }catch (e : Exception){

                teamAname.text = "NA"
                teamBname.text = "NA"
            }

        }
    }

    override fun getItemCount() = differ.currentList.size
    override fun getItemViewType(position: Int): Int {
        return position
    }

}