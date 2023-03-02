package com.halil.ozel.unsplashexample.ui.fragment.schedule

import DateUtils
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import cc.taylorzhang.singleclick.onSingleClick
import com.halil.ozel.unsplashexample.databinding.FragmentScheduleBinding
import com.halil.ozel.unsplashexample.model.allschedule.Event_
import com.halil.ozel.unsplashexample.ui.adapter.ScheduleMatchesAdapter
import com.xcoder.animator.Animations
import com.xcoder.animator.ScrollAnimator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ScheduleFragment : Fragment(), DefaultLifecycleObserver {


    private lateinit var binding: FragmentScheduleBinding
    private lateinit var scheduledadapter : ScheduleMatchesAdapter
    private val viewModel: ScheduleViewModel by viewModels()
    var positionToScroll : Int = 0

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
            setHasFixedSize(true)
            isNestedScrollingEnabled = false

            ScrollAnimator.create()
                .withAnimation(Animations.ANIMATION_SLIDE_FROM_LEFT)
                .withInterpolator(Animations.INTERPOLATOR_FAST_OUT_SLOW)
                .tillDuration(Animations.DURATION_FAST)
                .playOnlyOnDownScroll(true)
                .animate(this);

        }



        viewModel.responseImages.observe(requireActivity()) { response ->
            if (response != null) {

                positionToScroll = getScrollPosition(response)
                scheduledadapter.submitList(response)


                binding.scheduleRevyclerview.postDelayed({
                    (binding.scheduleRevyclerview.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(positionToScroll, 0)

                    binding.shimmerFrameLayoutUpcoming.stopShimmer()
                    binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
                    binding.scheduleRevyclerview.visibility = View.VISIBLE

                }, 1000)



                binding.floatingButtonScroll.onSingleClick(){
                    binding.scheduleRevyclerview.smoothSnapToPosition(positionToScroll)
                }
            }
            else{
                binding.scheduleRevyclerview.visibility = View.GONE
                binding.shimmerFrameLayoutUpcoming.visibility = View.GONE

            }
        }


    }

    private fun getScrollPosition(response: List<Event_>) : Int{

        var position : Int = 0

        for (i in response.indices){

            val formatedDate : Date = DateUtils.formatDate(response[i].startTime,"yyyy-MM-dd'T'HH:mm:ss'Z'")
            val nowDate : Date = DateUtils.now()
            val daysPassed : Int = DateUtils.getDiffinDays(nowDate,formatedDate)

            if(DateUtils.isSameDaynew(formatedDate,nowDate)){
                position = i //today
                break
            }
            else if(DateUtils.isSameDaynew(formatedDate,DateUtils.addOneDay(nowDate))){
                position = i //tomorrow
                break
            }
            else if(daysPassed in 2..4){
                position = i // 2-4days
                break
            }

        }
        return position
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
    }

    override fun onDetach() {
        activity?.lifecycle?.removeObserver(this)
        super.onDetach()
    }
    fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int = snapMode
            override fun getHorizontalSnapPreference(): Int = snapMode
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }


}