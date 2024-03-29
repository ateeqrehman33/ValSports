package com.attitude.designs.valtrackr.ui.adapter

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.LivematchItemBinding
import com.attitude.designs.valtrackr.model.livematches.Event_
import com.attitude.designs.valtrackr.model.livematches.Stream_


class LiveMatchesAdapter : RecyclerView.Adapter<LiveMatchesAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(val binding:LivematchItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<Event_>() {
        override fun areItemsTheSame(oldItem: Event_, newItem: Event_): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event_, newItem: Event_): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Event_>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LivematchItemBinding.inflate(
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
            title.text = currentItem.league.name+" : "+currentItem.blockName

            if(currentItem.type.contains("match")){

                teamAname.text = currentItem.match.teams[0].name
                teamBname.text = currentItem.match.teams[1].name

                teamAiv.load(currentItem.match.teams[0].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                }
                teamABiv.load(currentItem.match.teams[1].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                }

                teamAscore.text = currentItem.match.teams[0].result.gameWins.toString()
                teamBscore.text = currentItem.match.teams[1].result.gameWins.toString()

                if(currentItem.match.teams[0].result.outcome.equals("win")){
                    teamAWin.setColorFilter(ContextCompat.getColor(context, R.color.md_teal_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    teamAWin.visibility = View.VISIBLE
                }
                else if(currentItem.match.teams[0].result.outcome.equals("loss")){
                    teamAWin.setColorFilter(ContextCompat.getColor(context, R.color.md_red_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    teamAWin.visibility = View.VISIBLE

                }

                if(currentItem.match.teams[1].result.outcome.equals("win")){
                    teamBWin.setColorFilter(ContextCompat.getColor(context, R.color.md_teal_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    teamBWin.visibility = View.VISIBLE

                }
                else if(currentItem.match.teams[1].result.outcome.equals("loss")){
                    teamBWin.setColorFilter(ContextCompat.getColor(context, R.color.md_red_400), android.graphics.PorterDuff.Mode.SRC_IN);
                    teamBWin.visibility = View.VISIBLE
                }

            }
            else{
                teamAname.text = "Not Available"
                teamBname.text = "Not Available"

                teamAiv.load(currentItem.league.image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)

                }
                teamABiv.load(currentItem.league.image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                }
            }


            var streamlist : List<Stream_> = currentItem.streams
            var youtubeparam : String = ""
            var twitcheparam : String = ""

            for(item in streamlist){
                if(item.provider.equals("youtube")){
                    println("youtube")
                    youtubeparam = item.parameter

                }else if(item.provider.equals("twitch")){
                    twitcheparam = item.parameter
                }
            }

            LLBtnYoutube.setOnClickListener {
                context.watchYoutube(youtubeparam)
            }

            LLBtnTwitch.setOnClickListener {
                context.watchTwitch(twitcheparam)
            }
        }
    }
    
    override fun getItemCount() = differ.currentList.size

    fun Context.watchYoutube(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://youtu.be/$id")
        )
        try {
            this.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            this.startActivity(webIntent)
        }
    }

    fun Context.watchTwitch(id: String) {
        val defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        defaultBrowser.data = Uri.parse("https://www.twitch.tv/$id")
        startActivity(defaultBrowser)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}