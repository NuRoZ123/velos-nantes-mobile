package fr.nuroz.velosnantes.api

import fr.nuroz.velosnantes.model.Station
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

interface StationApi {
    @GET("/api/stations")
    suspend fun getStations() : Response<List<Station>>

    @POST("/api/stations/status/change/{id}")
    suspend fun changeStatus(@Path("id") id: Long) : Response<Station>
}