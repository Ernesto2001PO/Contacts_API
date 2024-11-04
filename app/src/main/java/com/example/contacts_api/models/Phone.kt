package com.example.contacts_api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Phone(
    val id: Int,
    val number: String,
    var persona_id: Int,
    val label: String
) : Parcelable

