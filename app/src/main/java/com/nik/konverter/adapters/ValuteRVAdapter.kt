package com.nik.konverter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.konverter.R
import com.nik.konverter.model.forms.Valute
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_valute.view.*

class ValuteRVAdapter(val onItemClick: ((Valute) -> Unit)? =null): RecyclerView.Adapter<ValuteRVAdapter.ViewHolder>() {

    var valutes: List<Valute> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(valute: Valute) = with(itemView) {

            tv_name.text = valute.name
            tv_char_code.text = valute.charCode

            itemView.setOnClickListener {
                onItemClick?.invoke(valute)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_valute, parent, false))

    override fun getItemCount() = valutes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(valutes[position])
}