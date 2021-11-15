package com.example.android.siv

import com.google.gson.annotations.SerializedName

data class ClasseVacina (

	@SerializedName("cdAplicacao") val cdAplicacao : Int,
	@SerializedName("dataHoraAplicacao") val dataHoraAplicacao : String,
	@SerializedName("nomeLocalAplicacao") val nomeLocalAplicacao : String,
	@SerializedName("dataValidadeVacinaAplicacao") val dataValidadeVacinaAplicacao : String,
	@SerializedName("dataProximaAplicacao") val dataProximaAplicacao : String,
	@SerializedName("observacoesAplicacao") val observacoesAplicacao : String,
	@SerializedName("vacinaId") val vacinaId : Int,
	@SerializedName("vacina") val vacina : Vacina,
	@SerializedName("aplicadorId") val aplicadorId : Int,
	@SerializedName("aplicador") val aplicador : Aplicador,
	@SerializedName("usuarioId") val usuarioId : Int,
	@SerializedName("usuario") val usuario : Usuario
)