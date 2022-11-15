package fr.nuroz.velosnantes.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.nuroz.velosnantes.adapter.PumpAdapter
import fr.nuroz.velosnantes.api.PumpApi
import fr.nuroz.velosnantes.api.RetrofitHelper
import fr.nuroz.velosnantes.databinding.FragmentDashboardBinding
import fr.nuroz.velosnantes.model.allPumps
import fr.nuroz.velosnantes.ui.home.MapsActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

  private lateinit var dashboardViewModel: DashboardViewModel
private var _binding: FragmentDashboardBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val pumpLoadingProgresBar = binding.pumpLoadingProgresBar
    val showAllPumpBtn = binding.showAllPumpBtn

    showAllPumpBtn.setOnClickListener { it ->
      val intent = Intent(it.context, MapsActivity::class.java)
      allPumps = dashboardViewModel.pumps.value
      it.context.startActivity(intent)
    }

    val recyclerView: RecyclerView = binding.recyclerViewPump
      dashboardViewModel.pumps.observe(viewLifecycleOwner, Observer {
      val adapter : PumpAdapter = PumpAdapter(it, requireContext())
      recyclerView.layoutManager = LinearLayoutManager(requireContext())
      recyclerView.adapter = adapter
    })

    val pumpApi = RetrofitHelper().getInstance().create(PumpApi::class.java)
    GlobalScope.launch {
      val result = pumpApi.getStations()
      dashboardViewModel.pumps.postValue(result.body())
      pumpLoadingProgresBar.setVisibility(View.INVISIBLE)
    }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}