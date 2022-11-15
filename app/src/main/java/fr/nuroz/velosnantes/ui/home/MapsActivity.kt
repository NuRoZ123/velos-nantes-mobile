package fr.nuroz.velosnantes.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.nuroz.velosnantes.R
import fr.nuroz.velosnantes.databinding.ActivityStationMapsBinding
import fr.nuroz.velosnantes.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStationMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStationMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if(pumpSelected != null) {
            val position = LatLng(pumpSelected!!.lattitude, pumpSelected!!.longitude)
            mMap.addMarker(MarkerOptions()
                .position(position)
                .title(pumpSelected!!.carburant + ": " + pumpSelected!!.prix + "€")
                .icon(BitmapFromVector(applicationContext, R.drawable.ic_baseline_directions_bike_24)))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18F))

            pumpSelected = null;
        } else if(allStations != null) {
            val stations : List<Station> = allStations!!
            stations.forEach {
                val stationLatLng = LatLng(it.lattitude, it.longitude)
                mMap.addMarker(MarkerOptions().position(stationLatLng).title(it.name))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stationLatLng, 12F))
            }

          allStations = null;
        }  else if(allPumps != null) {
            val pumps : List<Pump> = allPumps!!
            pumps.forEach {
                val stationLatLng = LatLng(it.lattitude, it.longitude)
                mMap.addMarker(MarkerOptions().position(stationLatLng).title(it.carburant + ": " + it.prix + "€"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stationLatLng, 8F))
            }

            allStations = null;
        } else {
            val position = LatLng(stationSelected!!.lattitude, stationSelected!!.longitude)
            mMap.addMarker(MarkerOptions()
                .position(position)
                .title(stationSelected!!.name)
                .icon(BitmapFromVector(applicationContext, R.drawable.ic_baseline_directions_bike_24)))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18F))

            stationSelected = null;
        }

        if(currentLocation != null) {
            val currentLatLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
            mMap.addMarker(MarkerOptions())
                .position(currentLatLng)
                .title("You are here")
                .icon(BitmapFromVector(applicationContext, R.drawable.ic_baseline_my_location_24))
        }
    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

private operator fun LatLng.invoke(currentLatLng: LatLng): Any {

}
