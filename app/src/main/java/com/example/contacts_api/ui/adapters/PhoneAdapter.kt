package com.example.contacts_api.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts_api.R
import com.example.contacts_api.models.Phone
import com.example.contacts_api.ui.interfaces.OnPersonaClickListener

class PhoneAdapter( val phones: List<Phone>, val listener: OnPersonaClickListener
) : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {

    class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(phone: Phone) {
            itemView.findViewById<TextView>(R.id.phone_number).text = phone.number
            itemView.findViewById<TextView>(R.id.label_phone).text = phone.label
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_phone, parent, false)
        return PhoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(phones[position])

        holder.itemView.setOnClickListener {
            listener.OnPhoneClick(phones[position])
        }
    }

    override fun getItemCount(): Int = phones.size
}
