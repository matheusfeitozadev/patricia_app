package com.example.android.siv.webapi

import retrofit2.Retrofit
import com.example.android.siv.interfaces.IUsuario
import com.example.android.siv.interfaces.IVacina

class ApiApp(private val retrofit: Retrofit) {
    private var iUsuario: IUsuario? = null
    private var iVacina: IVacina? = null

    fun getiUsuarui(): IUsuario? {
        iUsuario = retrofit.create(IUsuario::class.java)
        return iUsuario
    }

    fun getiVacina(): IVacina? {
        iVacina = retrofit.create(IVacina::class.java)
        return iVacina
    }
}
