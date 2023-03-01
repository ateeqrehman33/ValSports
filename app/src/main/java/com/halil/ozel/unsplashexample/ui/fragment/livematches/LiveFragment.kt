package com.halil.ozel.unsplashexample.ui.fragment.livematches

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import cc.taylorzhang.singleclick.onSingleClick
import com.halil.ozel.unsplashexample.databinding.FragmentLiveBinding
import com.halil.ozel.unsplashexample.ui.adapter.ImageAdapter
import com.halil.ozel.unsplashexample.ui.adapter.UpcomingMatchesAdapter
import com.halil.ozel.unsplashexample.ui.fragment.livematches.bottomsheet.ItemListDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LiveFragment : Fragment(), DefaultLifecycleObserver  {

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
            if (response != null && response.isNotEmpty()) {
                imageAdapter.submitList(response)
                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
            else{
                binding.recyclerView.visibility = View.GONE
                binding.NodataView.visibility = View.VISIBLE
                binding.shimmerFrameLayout.visibility = View.GONE

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
                binding.shimmerFrameLayoutUpcoming.stopShimmer()
                binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
                binding.rvUpcoming.visibility = View.VISIBLE
            }
            else{
                binding.rvUpcoming.visibility = View.GONE
                binding.shimmerFrameLayoutUpcoming.visibility = View.GONE

            }
        }

        binding.filerIv.onSingleClick() {

                val sheet = ItemListDialogFragment()
                sheet.show(childFragmentManager, "")

            childFragmentManager.executePendingTransactions()
            sheet.dialog?.setOnDismissListener(DialogInterface.OnDismissListener {
                println("Dissmissed")
                viewModel.getUpcomingMatches()
            })
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

    override fun onResume() {
        super<Fragment>.onResume()
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayoutUpcoming.startShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayoutUpcoming.stopShimmer()
        super<Fragment>.onPause()
    }


}