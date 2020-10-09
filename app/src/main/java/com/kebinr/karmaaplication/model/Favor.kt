package com.kebinr.karmaaplication.model
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Favor (val user_asking: String? = "",val user_toDo: String?="" ,val user_askingid: String? = "",val user_toDoid: String?="" , val type: String? = "", val details: String? ="", val status: String? ="", val delivery: String?="")

