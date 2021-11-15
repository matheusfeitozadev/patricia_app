package com.example.android.siv.models

import java.lang.reflect.Constructor

data class VacinaModel(
    var id : Int,
    var nome_vacina : String,
    var local : String,
    var nome_aplicador : String,
    var crm : String,
    var data : String,
    var hora : String
)