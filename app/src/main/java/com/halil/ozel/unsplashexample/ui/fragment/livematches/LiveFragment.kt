package com.halil.ozel.unsplashexample.ui.fragment.livematches

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import cc.taylorzhang.singleclick.onSingleClick
import com.halil.ozel.unsplashexample.R
import com.halil.ozel.unsplashexample.databinding.FragmentLiveBinding
import com.halil.ozel.unsplashexample.ui.adapter.ImageAdapter
import com.halil.ozel.unsplashexample.ui.adapter.UpcomingMatchesAdapter
import com.halil.ozel.unsplashexample.ui.fragment.livematches.bottomsheet.ItemListDialogFragment
import com.halil.ozel.unsplashexample.utils.WaveAnimation
import com.xcoder.animator.Animations
import com.xcoder.animator.ScrollAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_live.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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


        setupRecyclerviews()
        subscribeToObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.filerIv.onSingleClick() {

            val sheet = ItemListDialogFragment()
            sheet.show(childFragmentManager, "")
            childFragmentManager.executePendingTransactions()
            sheet.dialog?.setOnDismissListener(DialogInterface.OnDismissListener {
                println("Dissmissed")
                viewModel.getUpcomingMatches()
            })
        }


        ssPullRefresh.setRefreshView(WaveAnimation(requireContext()))



        binding.ssPullRefresh.setOnRefreshListener {
            CoroutineScope(Dispatchers.Main).launch {
               // delay(2000)
                viewModel.getAllImages()
                viewModel.getUpcomingMatches()

            }
        }
        binding.ssPullRefresh.setGifAnimation(R.raw.jett)




    }

    private fun setupRecyclerviews(){

        binding.recyclerView.apply {
            imageAdapter = ImageAdapter()
            adapter = imageAdapter
        }
        binding.rvUpcoming.apply {
            upcomingadapter = UpcomingMatchesAdapter()
            adapter = upcomingadapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false

            ScrollAnimator.create()
                .withAnimation(Animations.ANIMATION_SLIDE_FROM_LEFT)
                .withInterpolator(Animations.INTERPOLATOR_FAST_OUT_SLOW)
                .tillDuration(Animations.DURATION_FAST)
                .playOnlyOnDownScroll(true)
                .animate(this);
        }
    }


    private fun subscribeToObservers() {


        viewModel.responseImages.observe(requireActivity()) { response ->
            if (response != null && response.isNotEmpty()) {

                imageAdapter.submitList(response)
                binding.rvUpcoming.postDelayed({
                    binding.shimmerFrameLayout.stopShimmer()

                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }, 1000)

            }
            else{
                binding.recyclerView.visibility = View.GONE
                binding.NodataView.visibility = View.VISIBLE
                binding.shimmerFrameLayout.visibility = View.GONE

            }
        }

        viewModel.responseUpcoming.observe(requireActivity()) { response ->
            if (response != null) {
                upcomingadapter.submitList(response)

                binding.rvUpcoming.postDelayed({
                    binding.shimmerFrameLayoutUpcoming.stopShimmer()

                    binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
                    binding.rvUpcoming.visibility = View.VISIBLE
                }, 1000)

            }
            else{
                binding.rvUpcoming.visibility = View.GONE
                binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
            }
            binding.ssPullRefresh.setRefreshing(false) // This stops refreshing

        }



    }

}