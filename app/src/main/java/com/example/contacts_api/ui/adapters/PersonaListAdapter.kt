package com.example.contacts_api.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts_api.R
import com.example.contacts_api.ext.cargarImagen
import com.example.contacts_api.models.Persona
import com.example.contacts_api.ui.interfaces.OnPersonaClickListener


class PersonaListAdapter(
    private var personas: List<Persona>,
    private val listener: OnPersonaClickListener
) : RecyclerView.Adapter<PersonaListAdapter.ViewHolder>() {

    fun updateData(newPersonas: List<Persona>) {
        personas = newPersonas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.persona_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val persona = personas[position]
        holder.bind(persona, listener)



        // Configura el clic en el itemView
        holder.itemView.setOnClickListener {
            listener.onPersonaClick(persona)
        }

    }


    override fun getItemCount(): Int {
        return personas.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(persona: Persona, listener: OnPersonaClickListener ) {

            itemView.findViewById<ImageView>(R.id.imgPersona).cargarImagen(persona.profile_picture)
            itemView.findViewById<TextView>(R.id.persona_name).text = persona.name
            itemView.findViewById<TextView>(R.id.persona_last_name).text = persona.last_name
            itemView.findViewById<TextView>(R.id.persona_company).text = persona.company
            itemView.findViewById<TextView>(R.id.persona_address).text = persona.address
            itemView.findViewById<TextView>(R.id.persona_city).text = persona.city
            itemView.findViewById<TextView>(R.id.persona_state).text = persona.state

            itemView.findViewById<TextView>(R.id.persona_phones)
            val rvPhones = itemView.findViewById<RecyclerView>(R.id.rvPhone)
            rvPhones.layoutManager = LinearLayoutManager(itemView.context)
            rvPhones.adapter = PhoneAdapter(persona.phones ?: emptyList(), listener)

            itemView.findViewById<TextView>(R.id.persona_emails)
            val rvEmails = itemView.findViewById<RecyclerView>(R.id.rvEmail)
            rvEmails.layoutManager = LinearLayoutManager(itemView.context)
            rvEmails.adapter = EmailAdapter(persona.emails?: emptyList() , listener)

        }
    }



}
