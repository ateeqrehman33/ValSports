package com.attitude.designs.valtrackr.ui.fragment.livematches

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.navigation.Navigation
import cc.taylorzhang.singleclick.onSingleClick
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.FragmentLiveBinding
import com.attitude.designs.valtrackr.ui.adapter.ImageAdapter
import com.attitude.designs.valtrackr.ui.adapter.UpcomingMatchesAdapter
import com.attitude.designs.valtrackr.ui.fragment.livematches.bottomsheet.ItemListDialogFragment
import com.attitude.designs.valtrackr.utils.WaveAnimation
import com.xcoder.animator.Animations
import com.xcoder.animator.ScrollAnimator
import dagger.hilt.android.AndroidEntryPoint
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


        binding.threedots.setOnClickListener {
            showPopMenu(requireActivity(), it)
        }

        setupRecyclerviews()
        subscribeToObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.threedots.setOnClickListener {
            showPopMenu(requireActivity(), it)
        }


        binding.filerIv.onSingleClick() {

            val sheet = ItemListDialogFragment()
            sheet.show(childFragmentManager, "")
            childFragmentManager.executePendingTransactions()
            sheet.dialog?.setOnDismissListener(DialogInterface.OnDismissListener {
                println("Dissmissed")
                viewModel.getUpcomingMatches()
                viewModel.getAllImages()

            })
        }

        binding.ssPullRefresh.apply {
            setRefreshView(WaveAnimation(requireContext()))
            setGifAnimation(R.raw.jett)
            setOnRefreshListener {
                CoroutineScope(Dispatchers.Main).launch {
                    // delay(2000)
                    viewModel.getAllImages()
                    viewModel.getUpcomingMatches()

                }
            }
        }


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

    fun showPopMenu(context: Context, view: View) {

        val pop = PopupMenu(context, view)
        pop.inflate(R.menu.pop_menu_button)

        pop.setOnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.about_dev -> {

//                    val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragNavHost) as NavHostFragment
//                    val navController = navHostFragment.navController
//                    navController.navigate(R.id.action_live_to_aboutdev)
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

}