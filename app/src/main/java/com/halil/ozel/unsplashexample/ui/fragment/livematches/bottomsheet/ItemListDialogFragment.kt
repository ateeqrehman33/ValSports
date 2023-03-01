package com.halil.ozel.unsplashexample.ui.fragment.livematches.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.halil.ozel.unsplashexample.databinding.FragmentItemListDialogListDialogBinding
import com.halil.ozel.unsplashexample.ui.adapter.FilterLeagueAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListDialogFragment  : SuperBottomSheetFragment(), DefaultLifecycleObserver {


    private lateinit var binding: FragmentItemListDialogListDialogBinding
    private val viewModel: BottomSheetViewModel by viewModels()
    private lateinit var leagueAdapter: FilterLeagueAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentItemListDialogListDialogBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        leagueAdapter = FilterLeagueAdapter(context = requireContext())

        binding.leagueRv.apply {
            adapter = leagueAdapter
            layoutManager = LinearLayoutManager(context)
        }


        viewModel.responseImages.observe(requireActivity()) { response ->
            if (response != null && response.isNotEmpty()) {
                leagueAdapter.submitList(response)
            }
            else{

            }
        }


    }




}