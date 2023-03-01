package com.halil.ozel.unsplashexample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.halil.ozel.unsplashexample.R
import com.halil.ozel.unsplashexample.databinding.LivematchItemBinding
import com.halil.ozel.unsplashexample.model.livematches.Event_
import com.halil.ozel.unsplashexample.model.livematches.Stream_
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController


class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


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

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currImage = differ.currentList[position]

        holder.binding.apply {

            title.text = currImage.league.name+" : "+currImage.blockName

            if(currImage.type.contains("match")){

                teamAname.text = currImage.match.teams[0].name
                teamBname.text = currImage.match.teams[1].name

                teamAiv.load(currImage.match.teams[0].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                   // transformations(CircleCropTransformation())

                }
                teamABiv.load(currImage.match.teams[1].image){
                    crossfade(true)
                    placeholder(R.drawable.valorant_masters_lightmode)
                    //transformations(CircleCropTransformation())
                }

            }
            else{
                consDetails.visibility = View.GONE
            }


            var streamlist : List<Stream_> = currImage.streams
            var parameter : String = ""

            for(item in streamlist){
                if(item.provider.equals("youtube")){
                    println("youtube")

                    parameter = item.parameter

                    val youTubePlayerView: YouTubePlayerView = itemYoutubeplayer
                    getlifecycle()?.addObserver(youTubePlayerView)

                    youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            val defaultPlayerUiController =
                                DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                            youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                            youTubePlayer.cueVideo(parameter, 0f)



                        }


                    })


                }else if(item.provider.equals("twitch")){

                    println(item.provider+"  : "+ item.parameter )

                }

            }


        }
    }



    override fun getItemCount() = differ.currentList.size

    fun getlifecycle() = lifecycle

    companion object {
        private const val DURATION_MILLIS = 1000
        private val lifecycle: Lifecycle? = null

    }
}