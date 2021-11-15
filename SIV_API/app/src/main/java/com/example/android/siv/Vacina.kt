package com.example.android.siv

import com.google.gson.annotations.SerializedName

data class Vacina (

	@SerializedName("vacinaId") val vacinaId : Int,
	@SerializedName("nomeVacina") val nomeVacina : String
)