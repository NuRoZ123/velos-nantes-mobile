package fr.nuroz.velosnantes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.nuroz.velosnantes.R
import fr.nuroz.velosnantes.model.Pump
import fr.nuroz.velosnantes.model.currentLocation
import fr.nuroz.velosnantes.model.pumpSelected
import fr.nuroz.velosnantes.ui.home.MapsActivity
import java.text.SimpleDateFormat
import java.util.*

class PumpAdapter(private val pumps:List<Pump>, private val ctx : Context) : RecyclerView.Adapter<PumpAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView : CardView = itemView.findViewById(R.id.cardViewPump)
        val carburant : TextView = itemView.findViewById(R.id.carburantTextViewPump)
        val prix : TextView = itemView.findViewById(R.id.prixTextViewPump)
        val addresse : TextView = itemView.findViewById(R.id.addresseTextViewPump)
        val maj : TextView = itemView.findViewById(R.id.majTextViewPump)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_pumps, parent, false)
        return ViewHolder(view)
    }

    // pour chaque viewItem on alimente la vue
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pump : Pump = pumps[position]

        var dist : String = ": "
        if(currentLocation != null) {
            dist += String.format("%.2f", currentLocation!!.distanceTo(pump.toLocation()) / 1000) + "KM"
        }

        holder.addresse.text = pump.adresse + dist
        val lastMaj : Date = pump.dateMaj
        holder.prix.text = "${pump.prix}€"

        if(lastMaj == null) {
            holder.maj.setTextColor(ctx.getColor(R.color.red))
            holder.maj.text = ctx.getText(R.string.aucune_information)
        } else {
            holder.maj.setTextColor(ctx.getColor(R.color.black))
            holder.maj.text = SimpleDateFormat("dd/MM/yy hh:mm").format(lastMaj)
        }

        if(pump.carburant == null) {
            holder.carburant.text = ctx.getText(R.string.aucune_information)
            holder.carburant.setTextColor(ctx.getColor(R.color.red))
        } else {
            holder.carburant.text = pump.carburant
            holder.carburant.setTextColor(ctx.getColor(R.color.black))
        }

        holder.cardView.setOnClickListener {
            val intent : Intent = Intent(ctx, MapsActivity::class.java)
            pumpSelected = pump
            ctx.startActivity(intent)
        }
    }

    // return stations size
    override fun getItemCount(): Int {
        return pumps.size;
    }
}