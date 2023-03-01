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
import com.halil.ozel.unsplashexample.databinding.FragmentItemListDialogListDialogItemBinding
import com.halil.ozel.unsplashexample.model.leagues.League_
import com.halil.ozel.unsplashexample.utils.Constants
import com.halil.ozel.unsplashexample.utils.TinyDB
import java.util.*


class FilterLeagueAdapter(private val context: Context): RecyclerView.Adapter<FilterLeagueAdapter.ImageViewHolder>() {

    private var mContext: Context = context

    inner class ImageViewHolder(val binding:FragmentItemListDialogListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<League_>() {
        override fun areItemsTheSame(oldItem: League_, newItem: League_): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: League_, newItem: League_): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<League_>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            FragmentItemListDialogListDialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currImage = differ.currentList[position]
        val tinyDB = TinyDB(mContext)


        holder.binding.apply {


            leagueIv.load(currImage.image){
                crossfade(true)
                placeholder(R.drawable.valorant_masters_lightmode)
            }

            leagueName.text = currImage.name

            val list : ArrayList<String> = tinyDB.getListString(Constants.League_ID)

            if(list.isEmpty()){
                if(currImage.displayPriority.status.equals("force_selected")) {
                    leagueSwitch.isChecked = true
                    leagueSwitch.isEnabled = false
                }
                else if(currImage.displayPriority.status.equals("selected")){
                    leagueSwitch.isChecked = true
                }

            }
            else if(list.contains(currImage.id)){
                leagueSwitch.isChecked = true
            }










            leagueSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

                val list : ArrayList<String> = tinyDB.getListString(Constants.League_ID)

                if(isChecked){

                    if(!list.contains(currImage.id)){
                        list.add(currImage.id)
                    }

                }else{

                    if(list.contains(currImage.id)){
                        list.remove(currImage.id)
                    }

                }

                tinyDB.putListString(Constants.League_ID,list)

            }

        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

}