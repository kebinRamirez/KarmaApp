package com.kebinr.karmaaplication.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class User (val email: String? ="", val karma: Int?=2, val key: String?="", val nombre: String?="", var favores: Int? =0){

}