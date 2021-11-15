package com.example.android.siv.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.siv.R
import com.example.android.siv.adapters.VacinaAdapter
import com.example.android.siv.interfaces.IVacina
import com.example.android.siv.models.VacinaModel
import com.example.android.siv.utilities.ReturnSharedPreference
import com.example.android.siv.webapi.ApiApp
import com.example.android.siv.webapi.ApiBase
import com.google.android.material.appbar.MaterialToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var toolbar : MaterialToolbar? = null
    private var btnAdd: Button? = null
    private var include_empty: View? = null
    private var progress_bar: ProgressBar? = null
    private var rv_tasks : RecyclerView? = null

    private var apiBase : ApiBase? = null
    private var apiApp : ApiApp? = null
    private var iVacina : IVacina? = null
    private var context : Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        inicializarComponentes()


        /*//-- Ao clicar no botão número busca-se o registro do usuario dentro da API
        pesquisaRegistro?.setOnClickListener {

            progress_bar?.visibility = View.VISIBLE

            val call = RetrofitFactory().retrofitService().getCodUser(cdUser?.text.toString())

            call.enqueue(object : Callback<List<ClasseVacina>> {

                override fun onResponse(call: Call<List<ClasseVacina>>, response: Response<List<ClasseVacina>>) {

                    response.body()?.let {
                        Log.i("Vacina", it.toString())
                        Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_LONG).show()
                        progress_bar?.visibility = View.INVISIBLE
                    } ?: Toast.makeText(this@MainActivity, "Registro não encontrado", Toast.LENGTH_LONG)
                        .show()

                }

                override fun onFailure(call: Call<List<ClasseVacina>>?, t: Throwable?) {
                    t?.message?.let { it1 -> Log.e("Erro", it1) }
                    progress_bar?.visibility = View.INVISIBLE
                }

            })
        }*/

    }

    override fun onRestart() {
        super.onRestart()

        listarVacinas()
    }

    fun inicializarComponentes()
    {
        toolbar = findViewById(R.id.toolbar)
        btnAdd = findViewById(R.id.btnAdd) as Button
        include_empty = findViewById(R.id.include_empty) as View
        progress_bar = findViewById(R.id.progress_bar) as ProgressBar
        rv_tasks = findViewById(R.id.rv_tasks) as RecyclerView

        apiBase = ApiBase()
        apiApp = ApiApp(apiBase?.getRetrofit()!!)
        iVacina = apiApp!!.getiVacina()

        btnAdd?.setOnClickListener(View.OnClickListener {
            openMainActivity()
        })

        toolbar?.setOnClickListener(View.OnClickListener {
            finish()
        })

        listarVacinas()
    }

    fun listarVacinas()
    {
        progress_bar?.visibility = View.VISIBLE

        var idUser = ReturnSharedPreference.GetUserID(application)

        var call = iVacina?.getVacinas(idUser)

        call?.enqueue(object : Callback<List<VacinaModel>> {
            override fun onResponse(
                call: Call<List<VacinaModel>>,
                response: Response<List<VacinaModel>>
            ) {
                if(response.isSuccessful)
                {
                    //obetendo usuario da api
                    var modelList = response.body()

                    if(modelList != null && modelList.size > 0)
                    {
                        rv_tasks?.layoutManager = LinearLayoutManager(applicationContext)
                        rv_tasks?.adapter = VacinaAdapter(modelList, context!!)
                        rv_tasks?.visibility = View.VISIBLE
                        include_empty?.visibility = View.INVISIBLE
                    }
                    else
                    {
                        rv_tasks?.adapter = null
                        rv_tasks?.visibility = View.INVISIBLE
                        include_empty?.visibility = View.VISIBLE
                    }
                }
                else{
                    Toast.makeText(applicationContext, "Erro ao salvar dados", Toast.LENGTH_LONG).show()
                }

                progress_bar?.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<List<VacinaModel>>, t: Throwable) {
                progress_bar?.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun openMainActivity() {
        val intent = Intent(applicationContext, AddActivity::class.java)
        startActivity(intent)
    }

    fun carregarVacinas()
    {

    }
}






























//
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import android.widget.TextView
//import android.content.Intent
//
//
//
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//                // pegando o valor digitado no LoginActivity
//                val intent = intent
//                val text = intent.getStringExtra(Intent.EXTRA_TEXT)
//
//                val call = RetrofitFactory().retrofitService().getCodUser(text.toString())
//
//
//            call.enqueue(object : Callback<ClasseVacina> {
//
//                override fun onResponse(call: Call<ClasseVacina>, response: Response<ClasseVacina>) {
//
//                    //ACHO QUE AQUI EU NAO SEI OQ FAZER
//
//
//                }
//
//                override fun onFailure(call: Call<ClasseVacina>?, t: Throwable?) {
//                    t?.message?.let { it1 -> Log.e("Erro", it1) }
//                }
//            })
//        }
//    }
