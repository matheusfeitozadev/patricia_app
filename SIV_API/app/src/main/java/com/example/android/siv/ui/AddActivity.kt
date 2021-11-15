package com.example.android.siv.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.siv.R
import com.example.android.siv.interfaces.IUsuario
import com.example.android.siv.interfaces.IVacina
import com.example.android.siv.models.UsuarioModel
import com.example.android.siv.models.VacinaModel
import com.example.android.siv.utilities.ReturnSharedPreference
import com.example.android.siv.webapi.ApiApp
import com.example.android.siv.webapi.ApiBase
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.TimePicker

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.text.Editable
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {
    private var toolbar : MaterialToolbar? = null
    private var txtTitleValue : TextInputEditText? = null
    private var txtLocalValue : TextInputEditText? = null
    private var txtAplicadorValue : TextInputEditText? = null
    private var txtCrmValue : TextInputEditText? = null
    private var txtDateValue : TextInputEditText? = null
    private var txtHourValue : TextInputEditText? = null

    private var btnCancel : Button? = null
    private var btn_new_task : Button? = null

    private var apiBase : ApiBase? = null
    private var apiApp : ApiApp? = null
    private var iVacina : IVacina? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        incializarComponentes()
    }

    fun incializarComponentes()
    {
        toolbar = findViewById(R.id.toolbar)
        txtTitleValue = findViewById(R.id.txtTitleValue) as TextInputEditText
        txtLocalValue = findViewById(R.id.txtLocalValue) as TextInputEditText
        txtAplicadorValue = findViewById(R.id.txtAplicadorValue) as TextInputEditText
        txtCrmValue = findViewById(R.id.txtCrmValue) as TextInputEditText
        txtDateValue = findViewById(R.id.txtDateValue) as TextInputEditText
        txtHourValue = findViewById(R.id.txtHourValue) as TextInputEditText

        apiBase = ApiBase()
        apiApp = ApiApp(apiBase?.getRetrofit()!!)
        iVacina = apiApp!!.getiVacina()

        btnCancel = findViewById(R.id.btnCancel) as Button
        btn_new_task = findViewById(R.id.btn_new_task) as Button

        txtDateValue?.setOnClickListener(View.OnClickListener {
            showDatePicker()
        })

        txtHourValue?.setOnClickListener(View.OnClickListener {
            showTimerPicker()
        })

        toolbar?.setOnClickListener(View.OnClickListener {
            finish()
        })

        btnCancel?.setOnClickListener(View.OnClickListener {
            finish()
        })

        btn_new_task?.setOnClickListener(View.OnClickListener {
            var message = validarCampos();

            if(message.isEmpty())
            {
                var model = VacinaModel(
                    0,
                    txtTitleValue?.text.toString(),
                    txtLocalValue?.text.toString(),
                    txtAplicadorValue?.text.toString(),
                    txtCrmValue?.text.toString(),
                    txtDateValue?.text.toString(),
                    txtHourValue?.text.toString()
                )

                var call = iVacina?.postVacina(model)

                call?.enqueue(object : Callback<VacinaModel> {
                    override fun onResponse(
                        call: Call<VacinaModel>,
                        response: Response<VacinaModel>
                    ) {
                        if(response.isSuccessful)
                        {
                            //obetendo usuario da api
                            var modelTemp = response.body()

                            if(modelTemp != null)
                            {
                                Toast.makeText(applicationContext, "Adicionado com sucesso!", Toast.LENGTH_LONG).show()
                                finish()
                            }
                            else
                            {
                                Toast.makeText(applicationContext, "Registro não adicionado!", Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Toast.makeText(applicationContext, "Erro ao salvar dados", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<VacinaModel>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                })
            }
            else{
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun showTimerPicker()
    {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            var timeText = SimpleDateFormat("HH:mm").format(cal.time)
            txtHourValue?.setText(timeText)
        }

        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    fun showDatePicker()
    {
        val cal = Calendar.getInstance()
        val dateListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            txtDateValue?.setText(sdf.format(cal.time))

        }

        DatePickerDialog(this, dateListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun validarCampos() : String{
        var mensagem : String = ""

        if(txtTitleValue?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo nome vacina!"
        }

        if(txtLocalValue?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo local aplicação!"
        }

        if(txtAplicadorValue?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo nome aplicação!"
        }

        if(txtCrmValue?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo crm!"
        }

        if(txtDateValue?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo data!"
        }

        if(txtHourValue?.text.toString().isEmpty())
        {
            mensagem += "Preencha o campo hora!"
        }



        return mensagem
    }
}