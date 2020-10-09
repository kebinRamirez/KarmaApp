package com.kebinr.karmaaplication.model
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Favor (val user_asking: String? = "",val user_toDo: String?="", val type: String? = "", val status: String? , val fotocopy: String?, val buyKM5 : String?="" ,val quantityKM5: Int? =0, val delivery: String?="")

