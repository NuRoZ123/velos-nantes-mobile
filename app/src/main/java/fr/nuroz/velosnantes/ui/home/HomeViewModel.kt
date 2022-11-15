package fr.nuroz.velosnantes.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.nuroz.velosnantes.model.Station
import java.util.*

class HomeViewModel : ViewModel() {

    private val _stations = MutableLiveData<List<Station>>().apply {
        value = ArrayList()
    }
    val stations: MutableLiveData<List<Station>> = _stations
}