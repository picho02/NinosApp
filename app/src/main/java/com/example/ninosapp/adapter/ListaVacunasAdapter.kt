package com.example.ninosapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.ninosapp.R
import com.example.ninosapp.model.Desparacitada
import kotlinx.android.synthetic.main.item_lista.view.*

class ListaVacunasAdapter(private val mContext: Context, private val listaDesparacitada:
List<Desparacitada>): ArrayAdapter<Desparacitada>(mContext,0,listaDesparacitada){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_lista,parent,false)
        val tmp = listaDesparacitada[position]
        layout.tvTipo.text = tmp.tipo
        layout.tvAplicacion.text = tmp.fechaAplicacion
        layout.tvRefuerzo.text = tmp.fechaRefuerzo
        return layout
    }
}