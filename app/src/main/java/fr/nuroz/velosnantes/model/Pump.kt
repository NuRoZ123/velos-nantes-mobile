package fr.nuroz.velosnantes.model

import android.location.Location
import java.util.*

var pumpSelected : Pump? = null
var allPumps : List<Pump>? = null

data class Pump (
    val id: Long,
    val regionName: String,
    val lattitude: Double,
    val longitude: Double,
    val prix: Double,
    val dateMaj: Date,
    val ville: String,
    val adresse: String,
    val recordId: String,
    val carburant: String
) {
    fun toLocation() : Location {
        val location = Location("")

        location.latitude = lattitude
        location.longitude = longitude

        return location
    }
}
