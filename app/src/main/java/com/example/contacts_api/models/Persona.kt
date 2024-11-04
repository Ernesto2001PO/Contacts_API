package com.example.contacts_api.models
typealias Personas = List<Persona>

data class Persona (
    val id: Int,
    val name: String,
    val last_name: String,
    val company: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val profile_picture: String?,
    val phones: List<Phone> = emptyList(),
    val emails: List<Email> = emptyList()

)

