package com.example.android.siv.interfaces

import com.example.android.siv.models.UsuarioModel
import com.example.android.siv.models.VacinaModel
import retrofit2.Call
import retrofit2.http.*

interface IVacina {
    @GET("vacinas/")
    fun getVacinas(@Query("user_id") user : String) : Call<List<VacinaModel>>

    @POST("vacinas/")
    fun postVacina(@Body vacinaModel: VacinaModel) : Call<VacinaModel>

    @PUT("vacinas/{id}")
    fun putVacina(@Path("id") id : Int, @Body vacinaModel: VacinaModel) : Call<VacinaModel>

    @DELETE("vacinas/{id}")
    fun deleteVacina(@Path("id") id : Int) : Call<VacinaModel>
}