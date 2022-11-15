package fr.nuroz.velosnantes.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.nuroz.velosnantes.R
import fr.nuroz.velosnantes.model.pumpSelected

class DetailPumpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pump)

        pumpSelected?.let {pump ->
            val pumpAddresse : TextView = findViewById(R.id.addressePump)
            val openMapBtn : Button = findViewById(R.id.openMapBtnPump)

            pumpAddresse.text = pump.adresse

            openMapBtn.setOnClickListener {
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=${pump.lattitude},${pump.longitude}(${pump.carburant}: ${pump.prix}€)")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }
}