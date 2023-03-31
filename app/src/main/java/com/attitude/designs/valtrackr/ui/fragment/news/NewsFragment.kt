package com.attitude.designs.valtrackr.ui.fragment.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import com.attitude.designs.valtrackr.databinding.FragmentNewsBinding
import com.attitude.designs.valtrackr.ui.adapter.NewsAdapter
import com.attitude.designs.valtrackr.ui.fragment.newsdetails.WebViewActivity
import com.attitude.designs.valtrackr.utils.CheckInternet
import com.google.android.material.snackbar.Snackbar
import com.xcoder.animator.Animations
import com.xcoder.animator.ScrollAnimator
import dagger.hilt.android.AndroidEntryPoint
import io.github.tonnyl.light.Light


@AndroidEntryPoint
class NewsFragment : Fragment(), DefaultLifecycleObserver {


    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter : NewsAdapter
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(CheckInternet.checkForInternet(requireContext())){
            setupData()
        }else{
            Light.error(binding.root, "Not connected to internet, please check your connection.", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupData() {

        newsAdapter = NewsAdapter(NewsAdapter.OnClickListener{
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url","https://valorantesports.com"+it.url.url.toString())
            intent.putExtra("title",it.title.toString())
            startActivity(intent)

        })
        binding.scheduleRevyclerview.apply {
            adapter = newsAdapter
            setHasFixedSize(false)
            isNestedScrollingEnabled = false

            ScrollAnimator.create()
                .withAnimation(Animations.ANIMATION_SLIDE_FROM_LEFT)
                .withInterpolator(Animations.INTERPOLATOR_FAST_OUT_SLOW)
                .tillDuration(Animations.DURATION_FAST)
                .playOnlyOnDownScroll(true)
                .animate(this);

        }

        viewModel.responseNews.observe(requireActivity()) { response ->
            if (response != null) {

                newsAdapter.submitList(response)
                binding.scheduleRevyclerview.postDelayed({

                    binding.shimmerFrameLayoutUpcoming.stopShimmer()
                    binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
                    binding.scheduleRevyclerview.visibility = View.VISIBLE

                }, 1000)

            }
            else{
                binding.scheduleRevyclerview.visibility = View.GONE
                binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
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