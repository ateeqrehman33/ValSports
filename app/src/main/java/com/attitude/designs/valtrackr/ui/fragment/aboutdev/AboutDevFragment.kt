package com.attitude.designs.valtrackr.ui.fragment.aboutdev

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.navigation.Navigation
import cc.taylorzhang.singleclick.onSingleClick
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.FragmentMemberDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutDevFragment : Fragment(), DefaultLifecycleObserver {

    private lateinit var binding: FragmentMemberDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMemberDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filerIv.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.imageViewGithubIcon.onSingleClick(){
            goToUrl(getString(R.string.githublink))
        }

        binding.imageViewLinkedInIcon.onSingleClick(){
            goToUrl(getString(R.string.linkidinlink))
        }

        binding.imageViewinstaIcon.onSingleClick(){
            goToUrl(getString(R.string.instalink))
        }

    }

    private fun goToUrl(url: String) {
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavView)
        navBar.visibility = View.GONE
        activity?.lifecycle?.addObserver(this)
    }

    override fun onDetach() {
        activity?.lifecycle?.removeObserver(this)
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavView)
        navBar.visibility = View.VISIBLE
        super.onDetach()
    }



}