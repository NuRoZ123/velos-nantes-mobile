package fr.nuroz.velosnantes.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.nuroz.velosnantes.adapter.StationAdapter
import fr.nuroz.velosnantes.api.RetrofitHelper
import fr.nuroz.velosnantes.api.StationApi
import fr.nuroz.velosnantes.databinding.FragmentHomeBinding
import fr.nuroz.velosnantes.model.allStations
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

  private lateinit var homeViewModel: HomeViewModel
private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val bikeLoadingProgresBar = binding.bikeLoadingProgresBar
    val showAllStationBtn = binding.showAllStationBtn

    showAllStationBtn.setOnClickListener { it ->
      val intent = Intent(it.context, MapsActivity::class.java)
      allStations = homeViewModel.stations.value
      it.context.startActivity(intent)
    }

    val recyclerView: RecyclerView = binding.recyclerView
    homeViewModel.stations.observe(viewLifecycleOwner, {
      val adapter = StationAdapter(it, requireContext())
      recyclerView.layoutManager = LinearLayoutManager(requireContext())
      recyclerView.adapter = adapter
    })

    val stationApi = RetrofitHelper().getInstance().create(StationApi::class.java)
    GlobalScope.launch {
      val result = stationApi.getStations()
      homeViewModel.stations.postValue(result.body())
      bikeLoadingProgresBar.setVisibility(View.INVISIBLE);
    }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}