package fr.nuroz.velosnantes.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import fr.nuroz.velosnantes.R
import fr.nuroz.velosnantes.model.stationSelected

class DetailStationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_station)

        stationSelected?.let {station ->
            val stationName : TextView = findViewById(R.id.name)
            val openMapBtn : Button = findViewById(R.id.openMapBtn)

            stationName.text = station.name

            openMapBtn.setOnClickListener {
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=${station.lattitude},${station.longitude}(${station.name})")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }
}