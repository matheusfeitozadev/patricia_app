package com.example.android.siv.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.siv.R
import com.example.android.siv.interfaces.IVacina
import com.example.android.siv.models.VacinaModel
import com.example.android.siv.ui.EditActivity
import com.example.android.siv.ui.MainActivity
import com.example.android.siv.webapi.ApiApp
import com.example.android.siv.webapi.ApiBase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VacinaAdapter(var vacinas : List<VacinaModel>, var context: Context) : RecyclerView.Adapter<VacinaAdapter.VacinaViewHolder>()
{
    private var apiBase : ApiBase? = null
    private var apiApp : ApiApp? = null
    private var iVacina : IVacina? = null
    private var listData: MutableList<VacinaModel> = vacinas as MutableList<VacinaModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacinaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        apiBase = ApiBase()
        apiApp = ApiApp(apiBase?.getRetrofit()!!)
        iVacina = apiApp!!.getiVacina()
        return VacinaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: VacinaViewHolder, position: Int) {
        return holder.bind(listData[position])
    }

    inner class VacinaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val txtNomeVacina : TextView = itemView.findViewById(R.id.txtNomeVacina)
        private val txtLocal : TextView = itemView.findViewById(R.id.txtLocal)
        private val txtDataHora : TextView = itemView.findViewById(R.id.txtDataHora)
        private val txtNomeAplicador : TextView = itemView.findViewById(R.id.txtNomeAplicador)
        private val txtCrm : TextView = itemView.findViewById(R.id.txtCrm)
        private val btnEdit : Button = itemView.findViewById(R.id.btnEdit)
        private val btnDelete : Button = itemView.findViewById(R.id.btnDelete)

        fun bind(vacina : VacinaModel)
        {
            txtNomeVacina.text = vacina.nome_vacina
            txtLocal.text = vacina.local
            txtDataHora.text = vacina.data + " " + vacina.hora
            txtNomeAplicador.text = vacina.nome_aplicador
            txtCrm.text = vacina.crm

            var gson = Gson();
            var json = gson.toJson(vacina);

            btnEdit.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("json_model", json)
                itemView.context.startActivity(intent)
            })

            btnDelete.setOnClickListener(View.OnClickListener {
                val dialogBuilder = AlertDialog.Builder(itemView.context)

                // set message of alert dialog
                dialogBuilder.setMessage("Deseja remover o item?")
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Sim", DialogInterface.OnClickListener {
                            dialog, id ->
                                removeItem(vacina)
                    })
                    // negative button text and action
                    .setNegativeButton("Não", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Atenção")
                alert.show()

            })
        }

        fun removeItem(vacina : VacinaModel)
        {
            var call = iVacina?.deleteVacina(vacina.id)

            call?.enqueue(object : Callback<VacinaModel> {
                override fun onResponse(
                    call: Call<VacinaModel>,
                    response: Response<VacinaModel>
                ) {
                    if(response.isSuccessful)
                    {
                        listData.remove(vacina)
                        notifyDataSetChanged()

                        if(listData.size == 0)
                        {
                            val activity = context as MainActivity
                            activity.listarVacinas()
                        }
                    }
                    else{
                        Toast.makeText(context, "Erro ao salvar dados", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<VacinaModel>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }

            })
        }
    }

}

