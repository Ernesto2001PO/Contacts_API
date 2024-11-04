package com.example.contacts_api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Email(
    val id: Int,
    val email: String,
    var persona_id: Int,
    val label: String
) : Parcelable
