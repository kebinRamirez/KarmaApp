package com.kebinr.karmaaplication.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class User (val email: String? ="",  val karma: String?="",val key: String?="", val nombre: String?=""){

}