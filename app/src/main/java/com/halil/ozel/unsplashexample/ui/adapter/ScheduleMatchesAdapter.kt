package com.halil.ozel.unsplashexample.ui.adapter

import DateUtils
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.halil.ozel.unsplashexample.R
import com.halil.ozel.unsplashexample.databinding.ScheduleMatchItemBinding
import com.halil.ozel.unsplashexample.model.allschedule.Event_
import java.util.*


class ScheduleMatchesAdapter : RecyclerView.Adapter<ScheduleMatchesAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(val binding:ScheduleMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<Event_>() {
        override fun areItemsTheSame(oldItem: Event_, newItem: Event_): Boolean {
            return oldItem.match.id == newItem.match.id
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
        val currImage = differ.currentList[position]
        val context = holder.itemView.context


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


            println("passes : $daysPassed : $formatedDate")

            if(DateUtils.isSameDaynew(formatedDate,nowDate)){
                dateTitle.text = "Today"
            }
            else if(DateUtils.isSameDaynew(formatedDate,DateUtils.addOneDay(nowDate))){
                dateTitle.text = "Tomorrow"
            }
            else if(daysPassed >= 2 && daysPassed < 5){
                dateTitle.text = ""+DateUtils.formatDate(formatedDate,"EEEE")
            }
            else{
                dateTitle.text = ""+DateUtils.formatDate(formatedDate,"MMM dd")

            }

            datetime.text = DateUtils.formatDate(formatedDate,"HH:mm")
            dateYear.text = DateUtils.getYear(formatedDate).toString()

            if(currImage.state == "completed"){
                scoreCons.visibility = View.VISIBLE
                viewline.setBackgroundColor(ContextCompat.getColor(context,R.color.md_red_400))

                teamAscore.text = currImage.match.teams[0].result?.gameWins.toString()
                teamBscore.text = currImage.match.teams[1].result?.gameWins.toString()

                if(currImage.match.teams[0].result?.outcome.equals("win")){
                    teamAWin.setColorFilter(ContextCompat.getColor(context, R.color.md_teal_400), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                else if(currImage.match.teams[0].result?.outcome.equals("loss")){
                    teamAWin.setColorFilter(ContextCompat.getColor(context, R.color.md_red_400), android.graphics.PorterDuff.Mode.SRC_IN);
                }

                if(currImage.match.teams[1].result?.outcome.equals("win")){
                    teamBWin.setColorFilter(ContextCompat.getColor(context, R.color.md_teal_400), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                else if(currImage.match.teams[1].result?.outcome.equals("loss")){
                    teamBWin.setColorFilter(ContextCompat.getColor(context, R.color.md_red_400), android.graphics.PorterDuff.Mode.SRC_IN);
                }

            }
            else{
                scoreCons.visibility = View.GONE
                viewline.setBackgroundColor(ContextCompat.getColor(context,R.color.md_teal_400))

            }


        }
    }

    override fun getItemCount() = differ.currentList.size


}