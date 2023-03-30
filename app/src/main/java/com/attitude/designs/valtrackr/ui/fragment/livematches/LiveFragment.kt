package com.attitude.designs.valtrackr.ui.fragment.livematches

import DateUtils
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.navigation.Navigation
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import cc.taylorzhang.singleclick.onSingleClick
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.FragmentLiveBinding
import com.attitude.designs.valtrackr.model.unstarted.UpcomingEvent_
import com.attitude.designs.valtrackr.ui.adapter.ImageAdapter
import com.attitude.designs.valtrackr.ui.adapter.UpcomingMatchesAdapter
import com.attitude.designs.valtrackr.ui.fragment.livematches.bottomsheet.ItemListDialogFragment
import com.attitude.designs.valtrackr.utils.Constants
import com.attitude.designs.valtrackr.utils.ReminderWorker
import com.attitude.designs.valtrackr.utils.TinyDB
import com.attitude.designs.valtrackr.utils.WaveAnimation
import com.google.android.material.snackbar.Snackbar
import com.xcoder.animator.Animations
import com.xcoder.animator.ScrollAnimator
import dagger.hilt.android.AndroidEntryPoint
import io.github.tonnyl.light.Light.error
import io.github.tonnyl.light.Light.success
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class LiveFragment : Fragment(), DefaultLifecycleObserver{

    private lateinit var binding: FragmentLiveBinding
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var upcomingadapter : UpcomingMatchesAdapter
    private val viewModel: LiveViewModel by viewModels()
    val sheet = ItemListDialogFragment()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveBinding.inflate(layoutInflater)

        val listener = object: ItemListDialogFragment.OnActionCompleteListener {
            override fun onActionComplete(str: String) {

                val fragmentId = Navigation.findNavController(requireView()).currentDestination?.id
                Navigation.findNavController(requireView()).popBackStack(fragmentId!!,true)
                Navigation.findNavController(requireView()).navigate(fragmentId)

            }
        }

        sheet.setOnActionCompleteListener(listener)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ssPullRefresh.apply {
            setRefreshView(WaveAnimation(requireContext()))
            setGifAnimation(R.raw.jett)
            setOnRefreshListener {
                val mediaPlayer = MediaPlayer.create(context, R.raw.jettsound)
                mediaPlayer.start() // no need to call prepare(); create() does that for you
                viewModel.getAllImages()
                viewModel.getUpcomingMatches()
            }
        }




        binding.threedots.setOnClickListener {
            showPopMenu(requireActivity(), it)
        }


        binding.filerIv.onSingleClick() {
            sheet.show(childFragmentManager, "")
            childFragmentManager.executePendingTransactions()
        }




        val tinyDB : TinyDB = TinyDB(context)
        val list : ArrayList<String> = tinyDB.getListString(Constants.League_ID)
        if(list.isEmpty()){
            sheet.show(childFragmentManager, "")
            childFragmentManager.executePendingTransactions()
        }

        setupRecyclerviews()
        subscribeToObservers()

    }

    private fun setupRecyclerviews(){

        imageAdapter = ImageAdapter()
        binding.recyclerView.apply {
            adapter = imageAdapter
            setHasFixedSize(false)
            isNestedScrollingEnabled = false
        }


        upcomingadapter = UpcomingMatchesAdapter(UpcomingMatchesAdapter.OnClickListener{
            createNotification(it)
        })
        binding.rvUpcoming.apply {
            adapter = upcomingadapter
            setHasFixedSize(false)
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
                println("responseImages")

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

                println("responseUpcoming")

                binding.rvUpcoming.postDelayed({
                    binding.shimmerFrameLayoutUpcoming.stopShimmer()

                    binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
                    binding.rvUpcoming.visibility = View.VISIBLE
                }, 1000)

            }
            else{
                binding.rvUpcoming.visibility = View.GONE
                binding.shimmerFrameLayoutUpcoming.visibility = View.GONE
                error(binding.root, "Something went wrong, try again", Snackbar.LENGTH_SHORT).show()

            }
            binding.ssPullRefresh.setRefreshing(false) // This stops refreshing
        }
    }

    @SuppressLint("DiscouragedPrivateApi")
    fun showPopMenu(context: Context, view: View) {

        val pop = PopupMenu(context, view)
        pop.inflate(R.menu.pop_menu_button)
        pop.setOnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.about_dev -> {
                    Navigation.findNavController(view).navigate(R.id.action_live_to_aboutdev)
                    true
                }
                else -> false
            }
        }
        try {
            val fieldMpopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMpopup.isAccessible= true
            val mPopup = fieldMpopup.get(pop)
            mPopup.javaClass
                .getDeclaredMethod("setFoeceShowIcon",Boolean::class.java)
                .invoke(mPopup,true)

        }catch (e:Exception){

        }finally {
            pop.show()
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
        binding.shimmerFrameLayout.stopShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        super<Fragment>.onPause()
    }

    private fun createNotification(event : UpcomingEvent_) {

        val formatedDate : Date = DateUtils.formatDate(event.startTime,"yyyy-MM-dd'T'HH:mm:ss'Z'")
        val nowDate : Date = DateUtils.now()
        val diffSeconds = DateUtils.secondsBetween(nowDate,formatedDate)

        createWorkRequest("Live : "+event.league.name,
            event.match.teams[0].code+" V/s "+ event.match.teams[1].code+"",diffSeconds,event.startTime);

    }

    // Private Function to create the OneTimeWorkRequest
    private fun createWorkRequest(
        title: String,
        message: String,
        timeDelayInSeconds: Long,
        startTime: String
    ) {
        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .addTag(startTime)
            .setInputData(workDataOf(
                "title" to message,
                "message" to title,
                 )
            )
            .build()

        WorkManager.getInstance().enqueueUniqueWork(startTime, ExistingWorkPolicy.REPLACE, myWorkRequest)

        success(binding.recyclerView, "You will be notified when match starts.", Snackbar.LENGTH_SHORT).show()
    }


}