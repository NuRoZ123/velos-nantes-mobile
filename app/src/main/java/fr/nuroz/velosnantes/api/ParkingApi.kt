package fr.nuroz.velosnantes.api

import fr.nuroz.velosnantes.model.Parking
import retrofit2.Response
import retrofit2.http.GET

interface ParkingApi {
    @GET("/api/parkings")
    suspend fun getParkings() : Response<List<Parking>>
}