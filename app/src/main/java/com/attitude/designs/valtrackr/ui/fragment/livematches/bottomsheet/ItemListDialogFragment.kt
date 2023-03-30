package com.attitude.designs.valtrackr.ui.fragment.livematches.bottomsheet

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import cc.taylorzhang.singleclick.onSingleClick
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.attitude.designs.valtrackr.databinding.FragmentItemListDialogListDialogBinding
import com.attitude.designs.valtrackr.ui.adapter.FilterLeagueAdapter
import com.attitude.designs.valtrackr.utils.CheckInternet
import com.attitude.designs.valtrackr.utils.Constants
import com.attitude.designs.valtrackr.utils.TinyDB
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.tonnyl.light.Light

@AndroidEntryPoint
class ItemListDialogFragment  : SuperBottomSheetFragment(), DefaultLifecycleObserver {


    private lateinit var binding: FragmentItemListDialogListDialogBinding
    private val viewModel: BottomSheetViewModel by viewModels()
    private lateinit var leagueAdapter: FilterLeagueAdapter
    private lateinit var listener: OnActionCompleteListener


    fun setOnActionCompleteListener(listener: OnActionCompleteListener) {
        this.listener = listener
    }

    interface OnActionCompleteListener {
        fun onActionComplete(str: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)



        binding = FragmentItemListDialogListDialogBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(CheckInternet.checkForInternet(requireContext())){

        leagueAdapter = FilterLeagueAdapter(context = requireContext())



        binding.leagueRv.apply {
            adapter = leagueAdapter
            layoutManager = LinearLayoutManager(context)
        }

        var tinyDB : TinyDB = TinyDB(context)
        val list : ArrayList<String> = tinyDB.getListString(Constants.League_ID)


        viewModel.responseImages.observe(viewLifecycleOwner) { response ->
            if (response != null && response.isNotEmpty()) {


                if(list.isEmpty()){
                    for(currImage in response){
                            if(currImage.displayPriority.status == "force_selected" || currImage.displayPriority.status == "selected") {
                                if(!list.contains(currImage.id)){
                                    list.add(currImage.id)
                                }
                            }
                    }
                    tinyDB.putListString(Constants.League_ID,list)
                }


                leagueAdapter.submitList(response)
                binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
                binding.leagueRv.visibility = View.VISIBLE
            }
            else{

            }
        }

        binding.btnApply.onSingleClick(){
            listener.onActionComplete("applied")
            this.dismiss()
        }

        binding.btnClose.onSingleClick(){
            this.dismiss()
            tinyDB.clear()
            tinyDB.putListString(Constants.League_ID,list)

        }

        }else{
            Light.error(binding.root, "Not connected to internet, please check your connection.", Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun getBackgroundColor() = Color.BLACK
    override fun isSheetCancelable(): Boolean {
        return false
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }






}