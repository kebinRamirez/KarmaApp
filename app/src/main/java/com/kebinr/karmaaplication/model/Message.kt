package com.kebinr.karmaaplication.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message (val id: Int? = 0, val text: String? = "", val user: String? = "", val destinoid: String?="", val destino: String?="")