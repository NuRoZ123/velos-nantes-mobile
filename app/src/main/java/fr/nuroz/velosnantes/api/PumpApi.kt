package fr.nuroz.velosnantes.api

import fr.nuroz.velosnantes.model.Pump
import fr.nuroz.velosnantes.model.Station
import retrofit2.Response
import retrofit2.http.GET

interface PumpApi {
    @GET("/api/pumps")
    suspend fun getStations() : Response<List<Pump>>
}