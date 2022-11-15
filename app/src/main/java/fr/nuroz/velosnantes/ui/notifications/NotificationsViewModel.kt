package fr.nuroz.velosnantes.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.nuroz.velosnantes.model.Parking
import fr.nuroz.velosnantes.model.Pump

class NotificationsViewModel : ViewModel() {

    private val _parkings = MutableLiveData<List<Parking>>().apply {
        value = ArrayList()
    }
    val parkings: MutableLiveData<List<Parking>> = _parkings
}