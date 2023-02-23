package com.halil.ozel.unsplashexample.ui.fragment.schedule

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import com.halil.ozel.unsplashexample.databinding.FragmentScheduleBinding
import com.halil.ozel.unsplashexample.ui.adapter.ImageAdapter
import com.halil.ozel.unsplashexample.ui.adapter.ScheduleMatchesAdapter
import com.halil.ozel.unsplashexample.ui.adapter.UpcomingMatchesAdapter
import com.halil.ozel.unsplashexample.ui.fragment.livematches.LiveViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleFragment : Fragment(), DefaultLifecycleObserver {


    private lateinit var binding: FragmentScheduleBinding
    private lateinit var scheduledadapter : ScheduleMatchesAdapter
    private val viewModel: ScheduleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {

        scheduledadapter = ScheduleMatchesAdapter()
        binding.scheduleRevyclerview.apply {
            adapter = scheduledadapter
            setHasFixedSize(false)
            isNestedScrollingEnabled = false

        }

        viewModel.responseImages.observe(requireActivity()) { response ->
            if (response != null) {
                scheduledadapter.submitList(response)
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
    }

    override fun onDetach() {
        activity?.lifecycle?.removeObserver(this)
        super.onDetach()
    }

}