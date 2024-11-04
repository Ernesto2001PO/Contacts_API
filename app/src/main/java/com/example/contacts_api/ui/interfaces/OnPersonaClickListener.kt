package com.example.contacts_api.ui.interfaces

import com.example.contacts_api.models.Email
import com.example.contacts_api.models.Persona
import com.example.contacts_api.models.Phone

interface OnPersonaClickListener {
    fun onPersonaClick(persona: Persona)

    fun OnPhoneClick(phone: Phone)

    fun OnEmailClick(email: Email)

}
