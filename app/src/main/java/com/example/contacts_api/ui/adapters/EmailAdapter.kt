package com.example.contacts_api.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts_api.R
import com.example.contacts_api.models.Email
import com.example.contacts_api.ui.interfaces.OnPersonaClickListener

class EmailAdapter(val emails: List<Email>, val listener: OnPersonaClickListener) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(email: Email) {
            itemView.findViewById<TextView>(R.id.email_address).text = email.email
            itemView.findViewById<TextView>(R.id.label_email).text = email.label
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])

        holder.itemView.setOnClickListener {
            listener.OnEmailClick(emails[position])
        }
    }

    override fun getItemCount(): Int = emails.size
}
