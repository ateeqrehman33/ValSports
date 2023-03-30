package com.attitude.designs.valtrackr.ui.fragment.brackets

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import com.attitude.designs.valtrackr.R
import com.attitude.designs.valtrackr.databinding.FragmentBracketsBinding
import com.attitude.designs.valtrackr.model.brackets.*
import com.attitude.designs.valtrackr.model.leagues.League_
import com.attitude.designs.valtrackr.ui.adapter.RankingsAdapter
import com.attitude.designs.valtrackr.utils.CheckInternet
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.ventura.bracketslib.model.ColomnData
import com.ventura.bracketslib.model.CompetitorData
import com.ventura.bracketslib.model.MatchData
import dagger.hilt.android.AndroidEntryPoint
import io.github.tonnyl.light.Light


@AndroidEntryPoint
class BracketsFragment : Fragment(), DefaultLifecycleObserver {

    private lateinit var binding: FragmentBracketsBinding
    private val viewModel: BracketsViewModel by viewModels()
    private lateinit var rankingsAdapter : RankingsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBracketsBinding.inflate(LayoutInflater.from(requireActivity()))
       // binding = FragmentBracketsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rankingsAdapter = RankingsAdapter(requireContext())

        binding.lottieAnimationView2.visibility = View.VISIBLE


        binding.rankingsRevyclerview.apply {
            adapter = rankingsAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }

        if(CheckInternet.checkForInternet(requireContext())){
            setupData()
        }else{
            Light.error(binding.root, "Not connected to internet, please check your connection.", Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun setupData() {

        viewModel.responseLeague.observe(requireActivity()) { leagues ->
            if (leagues != null) {
                setupLeagueData(leagues)
            }
        }

        viewModel.responseImages.observe(requireActivity()) { standings ->
            if (standings != null) {
                setupTournamentData(standings)

            }
        }
    }


    private fun setupLeagueData(leagues: List<League_>) {

        val leagueadapter = IconSpinnerAdapter(binding.dropdownLeague)

        var listofleagues : ArrayList<IconSpinnerItem> = arrayListOf()
        var selectedtourIdpos : Int = 0
        var isexecuted=false;


        for ((i,league) in leagues.withIndex()){

            listofleagues.add(IconSpinnerItem(text = league.name, icon = null))

            if(league.displayPriority.status.equals("force_selected") && !isexecuted){
                isexecuted = true
                selectedtourIdpos = i
            }
            else if(league.displayPriority.status.equals("selected") && !isexecuted){
                isexecuted = true
                selectedtourIdpos = i
            }
        }

        binding.dropdownLeague.apply {
            getSpinnerRecyclerView().adapter = leagueadapter
            leagueadapter.setItems(listofleagues)
            setIsFocusable(true)
            //selectItemByIndex(selectedtourIdpos)
            lifecycleOwner = this@BracketsFragment
            binding.lottieAnimationView2.visibility = View.GONE
            binding.NodataView.visibility = View.VISIBLE

            setOnSpinnerDismissListener {
                println("selected index  : "+binding.dropdownLeague.selectedIndex)
                if(binding.dropdownLeague.selectedIndex>=0){

                    var listoftourid : ArrayList<String> = arrayListOf()

                    for (tour in leagues.get(binding.dropdownLeague.selectedIndex).tournaments){
                        listoftourid.add(tour.id)
                    }
                    val s: String = TextUtils.join(",", listoftourid)

                    binding.dropdownTournament.clearSelectedItem()
                    binding.bracketView.visibility = View.GONE
                    binding.chipGroup.removeAllViews()
                    binding.chipGroup2.removeAllViews()
                    binding.rankingsRevyclerview.visibility = View.GONE

                    binding.lottieAnimationView2.visibility = View.VISIBLE
                    binding.NodataView.visibility = View.GONE

                    viewModel.updateBracketsByTourId(s)
                }
            }
        }
    }

    private fun setupTournamentData(standings: List<Standing_>) {

        var listoftour : ArrayList<IconSpinnerItem> = arrayListOf()
        val tourdapter = IconSpinnerAdapter(binding.dropdownTournament)

        for (tour in standings){
            listoftour.add(
                IconSpinnerItem(
                    text = tour.name)
            )
        }

        binding.dropdownTournament.apply {
            getSpinnerRecyclerView().adapter = tourdapter
            tourdapter.setItems(listoftour)

            binding.lottieAnimationView2.visibility = View.GONE
            binding.NodataView.visibility = View.VISIBLE

            setOnSpinnerDismissListener {
                try {

                    if(binding.dropdownLeague.selectedIndex>=0){
                        if(standings.get(binding.dropdownTournament.selectedIndex).stages.isEmpty()){
                            println("no stages data")
                            binding.NodataView.visibility = View.VISIBLE
                        }
                        else{
                            setupStages(standings.get(binding.dropdownTournament.selectedIndex).stages)
                        }
                    }

                }catch (e : Exception){

                }

            }
        }
    }

    private fun setupStages(stages: List<Stage_>) {

        val chipGroup = binding.chipGroup
        chipGroup.removeAllViews()

        val stageList : ArrayList<Stage_> = arrayListOf()
        var checkFirstItem : Boolean = true

        for (stage in stages){
            stageList.add(stage)
            val chip = layoutInflater.inflate(R.layout.chip_layout, chipGroup, false) as Chip

            chip.apply {
                id = ViewCompat.generateViewId()
                setText(stage.name)
                isCheckable = true
                if(checkFirstItem){
                    chip.isChecked = true
                    checkFirstItem = false
                }
            }
            chipGroup.addView(chip)
        }

        binding.chipGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
            val titleOrNull = chipGroup.findViewById<Chip>(checkedId)?.text
            //Toast.makeText(chipGroup.context, titleOrNull ?: "No Choice", Toast.LENGTH_LONG).show()

            var selectedIndex = 0
                for((i,stage) in stageList.withIndex()){
                    if(stage.name.equals(titleOrNull)){
                        selectedIndex = i
                        break
                    }
                }
            println("selected section id "+selectedIndex)
            println("selected chip colmn size "+stages[selectedIndex].sections[0].columns.size)

            if(stages[selectedIndex].sections[0].columns.isNotEmpty()){
                binding.bracketView.visibility = View.VISIBLE
                binding.rankingsRevyclerview.visibility = View.GONE

                setupSections(stages[selectedIndex].sections)

            }
            else if(stages[0].sections[0].rankings[0].teams.isNotEmpty()){
                binding.rankingsRevyclerview.visibility = View.VISIBLE
                binding.bracketView.visibility = View.GONE
                setupSections(stageList[0].sections)

            }
            else{
                binding.NodataView.visibility = View.VISIBLE
                binding.bracketView.visibility = View.GONE
                binding.rankingsRevyclerview.visibility = View.GONE
            }
        }

        if(stages[0].sections[0].columns.isNotEmpty()){
            binding.bracketView.visibility = View.VISIBLE
            binding.rankingsRevyclerview.visibility = View.GONE
            binding.NodataView.visibility = View.GONE

            setupSections(stageList[0].sections)

        }
        else if(stages[0].sections[0].rankings[0].teams.isNotEmpty()){

            val teamlist : ArrayList<Team_X> = arrayListOf()

            for(ranking in stages[0].sections[0].rankings){
                for ((i,team) in ranking.teams.withIndex()){
                    team.ordinal = ranking.ordinal.toString()
                    teamlist.add(team)
                }
            }

            rankingsAdapter.submitList(teamlist)
            binding.bracketView.visibility = View.GONE
            binding.rankingsRevyclerview.visibility = View.VISIBLE
            binding.NodataView.visibility = View.GONE

            setupSections(stageList[0].sections)


        }
        else{
            binding.NodataView.visibility = View.VISIBLE
        }


    }

    private fun setupSections(sections : List<Section_>) {

        val chipGroup = binding.chipGroup2
        chipGroup.removeAllViews()

        val sectionList : ArrayList<Section_> = arrayListOf()
        var checkFirstItem : Boolean = true

        for (section in sections){
            sectionList.add(section)
            val chip = layoutInflater.inflate(R.layout.chip_layout, chipGroup, false) as Chip

            chip.apply {
                id = ViewCompat.generateViewId()
                setText(section.name)
                isCheckable = true
                if(checkFirstItem){
                    chip.isChecked = true
                    checkFirstItem = false
                }
            }
            chipGroup.addView(chip)
        }

        chipGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
            val titleOrNull = chipGroup.findViewById<Chip>(checkedId)?.text

            var selectedIndex = 0
            for((i,section) in sectionList.withIndex()){
                if(section.name.equals(titleOrNull)){
                    selectedIndex = i
                    break
                }
            }

            if(sections[selectedIndex].columns.isNotEmpty()){
                binding.bracketView.visibility = View.VISIBLE
                updateBracket(sections[selectedIndex].columns)
                println("not empty columns "+sections[selectedIndex].columns.size)
                binding.rankingsRevyclerview.visibility = View.GONE
                binding.NodataView.visibility = View.GONE

            }
            else if(sections[selectedIndex].rankings[0].teams.isNotEmpty()){

                val teamlist : ArrayList<Team_X> = arrayListOf()

                for(ranking in sections[selectedIndex].rankings){
                    for ((i,team) in ranking.teams.withIndex()){
                        team.ordinal = ranking.ordinal.toString()
                        teamlist.add(team)
                    }
                }

                rankingsAdapter.submitList(teamlist)
                binding.bracketView.visibility = View.GONE
                binding.rankingsRevyclerview.visibility = View.VISIBLE
                binding.NodataView.visibility = View.GONE

            }
            else{

                println(" empty columns "+sections[selectedIndex].columns.size)
                binding.NodataView.visibility = View.GONE
            }
        }

        if(sections[0].columns.isNotEmpty()){
            binding.bracketView.visibility = View.VISIBLE
            updateBracket(sections[0].columns)
            println("not empty columns "+sections[0].columns.size)
            binding.NodataView.visibility = View.GONE
            binding.rankingsRevyclerview.visibility = View.GONE


        }
        else if(sections[0].rankings[0].teams.isNotEmpty()){
            val teamlist : ArrayList<Team_X> = arrayListOf()

            for(ranking in sections[0].rankings){
                for ((i,team) in ranking.teams.withIndex()){
                    team.ordinal = ranking.ordinal.toString()
                    teamlist.add(team)
                }
            }

            rankingsAdapter.submitList(teamlist)
            binding.bracketView.visibility = View.GONE
            binding.rankingsRevyclerview.visibility = View.VISIBLE
            binding.NodataView.visibility = View.GONE

        }
        else{
            binding.NodataView.visibility = View.VISIBLE

        }


    }


    private fun updateBracket(columns: List<Column_>) {

        try {

            val columnDataList: ArrayList<ColomnData> = arrayListOf()

            for(i in columns.indices){

                val matchdatalist: ArrayList<MatchData> = arrayListOf()

                for (j in columns[i].cells[0].matches.indices){
                    val teamDataA = CompetitorData(columns[i].cells[0].matches[j].teams[0].name,columns[i].cells[0].matches[j].teams[0].result?.gameWins.toString(),columns[i].cells[0].matches[j].teams[0].image)
                    val teamDataB = CompetitorData(columns[i].cells[0].matches[j].teams[1].name,columns[i].cells[0].matches[j].teams[1].result?.gameWins.toString(),columns[i].cells[0].matches[j].teams[1].image)
                    val matchData = MatchData(teamDataA, teamDataB)
                    println("adding match data "+columns[i].cells[0].matches[j].teams[0].name+" , "+columns[i].cells[0].matches[j].teams[1].name)
                    matchdatalist.add(matchData)
                }

                val finalColomn = ColomnData(matchdatalist,columns.get(i).cells[0].name)
                columnDataList.add(finalColomn)
            }

            binding.bracketView.setBracketsData(columnDataList)

        }catch (e : Exception){

            binding.NodataView.visibility = View.VISIBLE


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