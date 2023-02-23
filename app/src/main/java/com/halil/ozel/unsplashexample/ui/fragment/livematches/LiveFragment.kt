package com.halil.ozel.unsplashexample.ui.fragment.livematches

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.halil.ozel.unsplashexample.ui.adapter.ImageAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.halil.ozel.unsplashexample.databinding.FragmentLiveBinding
import com.halil.ozel.unsplashexample.ui.adapter.UpcomingMatchesAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_live.view.*


@AndroidEntryPoint
class LiveFragment : Fragment(), DefaultLifecycleObserver {

    private lateinit var binding: FragmentLiveBinding
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var upcomingadapter : UpcomingMatchesAdapter
    private val viewModel: LiveViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }


    private fun setupData() {
        imageAdapter = ImageAdapter()
        binding.recyclerView.apply {
            adapter = imageAdapter
            setHasFixedSize(true)
        }

        viewModel.responseImages.observe(requireActivity()) { response ->
            if (response != null && response.size>0) {

                println("not null")
                imageAdapter.submitList(response)
            }
            else{
                println("its null")
                binding.recyclerView.visibility = View.GONE
                binding.NodataView.visibility = View.VISIBLE

            }
        }

        upcomingadapter = UpcomingMatchesAdapter()
        binding.rvUpcoming.apply {
            adapter = upcomingadapter
            setHasFixedSize(false)
            isNestedScrollingEnabled = false

        }

        viewModel.responseUpcoming.observe(requireActivity()) { response ->
            if (response != null) {
                upcomingadapter.submitList(response)
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