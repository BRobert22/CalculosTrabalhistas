package tibaes.com.clculostrabalhistas

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_salario_hora.*

class SalarioHoraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario_hora)

        carregarSpinner()

        btnCalcular2.setOnClickListener {
            calcular()
        }


        // abrindo outro site
        btnFonte.setOnClickListener {
            val uris = Uri.parse("https://www.beirithadvogados.com.br/como-calcular-salario-hora/")
            val intents = Intent(Intent.ACTION_VIEW, uris)
            startActivity(intents)
        }
    }

    fun carregarSpinner() {
        val lstOps = arrayOf(
                getText(R.string.hora_180),
                getText(R.string.hora_220),
                getText(R.string.hora_Outros)
        )

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lstOps)

        spinnerHoras.setAdapter(aa)

        spinnerHoras.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                // pegando o valor do item selecionado no spinner
                val selectedItem = parent.getItemAtPosition(position).toString()

                if (selectedItem == getText(R.string.hora_Outros))
                    txtHoras.visibility = View.VISIBLE
                else
                    txtHoras.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                txtHoras.visibility = View.INVISIBLE
            }
        }
    }

    fun calcular() {
        var salario = txtSalario.text.toString().toBigDecimalOrNull()

        if (salario == null) {
            Toast.makeText(applicationContext, "Valor salario invalido", Toast.LENGTH_LONG).show()
            return
        }

        val selectedItem = spinnerHoras.selectedItem.toString()
        var horas = 0.0
        when (selectedItem) {
            getText(R.string.hora_180) -> {
                horas = 180.0
            }
            getText(R.string.hora_220) -> {
                horas = 220.0
            }
            getText(R.string.hora_Outros) -> {
                val horasInt = txtHoras.text.toString().toIntOrNull()
                if (horasInt == null) {
                    Toast.makeText(applicationContext, "Total de Horas invalido", Toast.LENGTH_LONG).show()
                    return
                } else
                    horas = horasInt.toDouble()
            }
        }

        /*A fórmula para calcular o salario por hora é: 44horas semanais x 5 semanas = 220. Em seguida, faz-se: 1500,00/220= 6,82.
        Por isso, o valor do salário por hora é de R$ 6,82.*/

        val result = salario / horas.toBigDecimal()

        val builder = AlertDialog.Builder(this@SalarioHoraActivity)
            builder.setTitle("Resultado")
            builder.setMessage("Salario hora: " + result.toString().format("%.2f"))
            val dialog: AlertDialog = builder.create()
            dialog.show()

    }
}