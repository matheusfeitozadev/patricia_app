package com.example.android.siv

import com.google.gson.annotations.SerializedName

data class Usuario (

	@SerializedName("usuarioId") val usuarioId : Int,
	@SerializedName("cpfUsuario") val cpfUsuario : Long,
	@SerializedName("emailUsuario") val emailUsuario : String,
	@SerializedName("nomeUsuario") val nomeUsuario : String,
	@SerializedName("dataNascimentoUsuario") val dataNascimentoUsuario : String,
	@SerializedName("senhaUsuario") val senhaUsuario : String,
	@SerializedName("telefoneUsuario") val telefoneUsuario : Long
)