package com.halil.ozel.unsplashexample.ui.fragment.brackets

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import com.halil.ozel.unsplashexample.databinding.FragmentBracketsBinding
import com.ventura.bracketslib.BracketsView
import com.ventura.bracketslib.model.ColomnData
import com.ventura.bracketslib.model.CompetitorData
import com.ventura.bracketslib.model.MatchData
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class BracketsFragment : Fragment(), DefaultLifecycleObserver {


    private lateinit var binding: FragmentBracketsBinding
    private val viewModel: BracketsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBracketsBinding.inflate(layoutInflater)


        val bracketsView = binding.bracketView

        val player1 = CompetitorData("Player 1", "3")
        val player2 = CompetitorData("Player 2", "1")
        val player3 = CompetitorData("Player 3", "3")
        val player4 = CompetitorData("Player 4", "2")
        val player5 = CompetitorData("Player 5", "4")
        val player6 = CompetitorData("Player 6", "2")
        val player7 = CompetitorData("Player 7", "2")
        val player8 = CompetitorData("Player 8", "2")
        val player9 = CompetitorData("Player 9", "2")
        val player10 = CompetitorData("Player 10", "2")


        val matchQuarterFinal1 = MatchData(player1, player2)
        val matchQuarterFinal2 = MatchData(player3, player4)
        val matchQuarterFinal3 = MatchData(player5, player6)
        val matchQuarterFinal4 = MatchData(player7, player8)
        val match1SemiFinal = MatchData(player2, player4)
        val match2SemiFinal = MatchData(player5, player7)
        val match3Final = MatchData(player2, player7)

        val quarterFinalColomn = ColomnData(
            Arrays.asList(
                matchQuarterFinal1,
                matchQuarterFinal2,
                matchQuarterFinal3,
                matchQuarterFinal4
            )
        )
        val semiFinalColomn = ColomnData(Arrays.asList(match1SemiFinal, match2SemiFinal))
        val finalColomn = ColomnData(Arrays.asList(match3Final))

        bracketsView.setBracketsData(
            Arrays.asList(
                quarterFinalColomn,
                semiFinalColomn,
                finalColomn
            )
        )


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        viewModel.responseImages.observe(requireActivity()) { response ->
            if (response != null) {





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