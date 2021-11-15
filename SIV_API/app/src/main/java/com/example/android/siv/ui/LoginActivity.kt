package com.example.android.siv.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.siv.interfaces.IUsuario
import com.example.android.siv.models.UsuarioModel
import com.example.android.siv.webapi.ApiApp
import com.example.android.siv.webapi.ApiBase
import retrofit2.Call
import retrofit2.Response

import com.example.android.siv.R
import com.example.android.siv.utilities.ReturnSharedPreference
import retrofit2.Callback


class LoginActivity : AppCompatActivity() {
    private var btnAvancar : Button? = null
    private var btnCadastrar : Button? = null
    private var txtUsuario : EditText? = null
    private var txtSenha : EditText? = null

    private var apiBase : ApiBase? = null
    private var apiApp : ApiApp? = null
    private var iUsuario : IUsuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inicializarComponentes()
    }

    fun inicializarComponentes(){
        btnAvancar = findViewById(R.id.btn_entrar) as Button
        btnCadastrar = findViewById(R.id.btnCadastrar) as Button
        txtUsuario = findViewById(R.id.txtUsuario) as EditText
        txtSenha = findViewById(R.id.txtSenha) as EditText


        apiBase = ApiBase()
        apiApp = ApiApp(apiBase?.getRetrofit()!!)
        iUsuario = apiApp!!.getiUsuarui()

        btnAvancar?.setOnClickListener(View.OnClickListener {
           if(validarCampos().isEmpty())
           {
               var call = iUsuario?.getUsuario(txtUsuario?.text.toString(), txtSenha?.text.toString())

               call?.enqueue(object : Callback<List<UsuarioModel>>{
                   override fun onResponse(
                       call: Call<List<UsuarioModel>>,
                       response: Response<List<UsuarioModel>>
                   ) {
                       if(response.isSuccessful)
                       {
                           //obetendo usuario da api
                           var listUser = response.body()

                           if(listUser?.size!! > 0)
                           {
                               var model = listUser?.get(0)!!

                               //salvando id do usuário para coletar as vacinas
                               ReturnSharedPreference.SaveUserID(application, model.id)

                               openMainActivity()
                           }
                           else
                           {
                               Toast.makeText(applicationContext, "Usuário não encontrado!", Toast.LENGTH_LONG).show()
                           }
                       }
                       else{
                           Toast.makeText(applicationContext, "Erro ao buscar dados", Toast.LENGTH_LONG).show()
                       }
                   }

                   override fun onFailure(call: Call<List<UsuarioModel>>, t: Throwable) {
                       Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                   }

               })
           }
           else
           {
               Toast.makeText(applicationContext, "OK", Toast.LENGTH_LONG).show()
           }
       })
    }

    fun validarCampos() : String{
        var mensagem : String = ""

        if(txtUsuario?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo usuário!"
        }

        if(txtSenha?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo senha!"
        }

        return mensagem
    }

    fun openMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}