package com.example.android.siv

import com.google.gson.annotations.SerializedName

data class Aplicador (

	@SerializedName("aplicadorId") val aplicadorId : Int,
	@SerializedName("crmCreAplicador") val crmCreAplicador : Int,
	@SerializedName("nomeAplicador") val nomeAplicador : String,
	@SerializedName("telefoneAplicador") val telefoneAplicador : Long
)