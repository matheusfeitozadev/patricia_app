package com.example.android.siv.interfaces

import com.example.android.siv.models.UsuarioModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IUsuario {
    @GET("usuarios/")
    fun getUsuario(@Query("usuario") user : String, @Query("senha") password : String) : Call<List<UsuarioModel>>
}