package com.halil.ozel.unsplashexample.ui.fragment.brackets

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
import com.google.android.material.chip.Chip
import com.halil.ozel.unsplashexample.R
import com.halil.ozel.unsplashexample.databinding.FragmentBracketsBinding
import com.halil.ozel.unsplashexample.model.brackets.Column_
import com.halil.ozel.unsplashexample.model.brackets.Section_
import com.halil.ozel.unsplashexample.model.brackets.Stage_
import com.halil.ozel.unsplashexample.model.brackets.Standing_
import com.halil.ozel.unsplashexample.model.leagues.League_
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.ventura.bracketslib.model.ColomnData
import com.ventura.bracketslib.model.CompetitorData
import com.ventura.bracketslib.model.MatchData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BracketsFragment : Fragment(), DefaultLifecycleObserver {

    private lateinit var binding: FragmentBracketsBinding
    private val viewModel: BracketsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val baseInflater = LayoutInflater.from(requireActivity()) // NOT context
        binding = FragmentBracketsBinding.inflate(baseInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {

        viewModel.responseLeague.observe(requireActivity()) { leagues ->
            if (leagues != null) {
                setupLeagueData(leagues)
            }
        }

        viewModel.responseImages.observe(requireActivity()) { standings ->
            if (standings != null) {
                //setupStandings(standings)
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
                    viewModel.updateBracketsByTourId(s)
                    //setupTournamentData(leagues.get(binding.dropdownLeague.selectedIndex).tournaments)
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
            //selectItemByIndex(0)
            setOnSpinnerDismissListener {
                try {

                    if(binding.dropdownLeague.selectedIndex>=0){
                        if(standings.get(binding.dropdownTournament.selectedIndex).stages.isEmpty()){
                            println("no stages data")
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
                //updateBracket(stages[selectedIndex].sections[0].columns)
                setupSections(stages[selectedIndex].sections)
            }
            else{
                binding.bracketView.visibility = View.GONE
            }
        }

        if(stages[0].sections[0].columns.isNotEmpty()){
            binding.bracketView.visibility = View.VISIBLE
            updateBracket(stages[0].sections[0].columns)
        }
        else{
            binding.bracketView.visibility = View.GONE
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
            //Toast.makeText(chipGroup.context, titleOrNull ?: "No Choice", Toast.LENGTH_LONG).show()

            var selectedIndex = 0
            for((i,section) in sectionList.withIndex()){
                if(section.name.equals(titleOrNull)){
                    selectedIndex = i
                    break
                }
            }
            println("selected chip id "+selectedIndex)
            println("selected chip colmn size "+sections[selectedIndex].columns.size)

            if(sections[selectedIndex].columns.isNotEmpty()){
                binding.bracketView.visibility = View.VISIBLE
                updateBracket(sections[selectedIndex].columns)
            }
            else{
                binding.bracketView.visibility = View.GONE
            }
        }

        if(sections[0].columns.isNotEmpty()){
            binding.bracketView.visibility = View.VISIBLE
            updateBracket(sections[0].columns)
        }
        else{
            binding.bracketView.visibility = View.GONE
        }


    }


    private fun updateBracket(columns: List<Column_>) {

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