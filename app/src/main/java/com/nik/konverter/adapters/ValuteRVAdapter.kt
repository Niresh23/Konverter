package com.nik.konverter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.konverter.R
import com.nik.konverter.database.Valuta
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_valute.view.*

class ValuteRVAdapter(val onItemClick: ((Valuta) -> Unit)? =null): RecyclerView.Adapter<ValuteRVAdapter.ViewHolder>() {

    var valutas: List<Valuta> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(valuta: Valuta) = with(itemView) {

            tv_name.text = valuta.name
            tv_char_code.text = valuta.charCode

            itemView.setOnClickListener {
                onItemClick?.invoke(valuta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_valute, parent, false))

    override fun getItemCount() = valutas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(valutas[position])
}