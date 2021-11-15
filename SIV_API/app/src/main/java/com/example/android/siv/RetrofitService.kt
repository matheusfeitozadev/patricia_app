package com.example.android.siv

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    //https://sivwebapi.azurewebsites.net/api/aplicacao/usuario/{CodUser}/
    @GET("{CodUser}")
    fun getCodUser(@Path("CodUser") CodUser : String) : Call<List<ClasseVacina>>

    //https://viacep.com.br/ws/SP/Sao%20Paulo/Avenida%20Lins%20de%20Vasconcelos/json/
    //@GET("{estado}/{cidade}/{endereco}/json/")
    //fun getRCE(@Path("estado") estado: String,
               //@Path("cidade") cidade: String,
               //@Path("endereco") endereco: String): Call<List<Vacina>>
}