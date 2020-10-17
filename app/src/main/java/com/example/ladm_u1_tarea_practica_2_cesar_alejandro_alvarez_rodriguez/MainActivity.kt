package com.example.ladm_u1_tarea_practica_2_cesar_alejandro_alvarez_rodriguez

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnguardar.setOnClickListener {
            if(rbmemoriaint.isChecked){
                if(guardarEnMemoriaInterna()==true){
                    Toast.makeText(this,"Memoria Interna", Toast.LENGTH_LONG).show()
                    AlertDialog.Builder(this).setTitle("Atencion")
                        .setMessage("Se guardo el archivo").setPositiveButton("Ok"){d,i->d.dismiss()}
                        .show()
                }else{
                    android.app.AlertDialog.Builder(this).setTitle("ERROR").setMessage("No se guardo el archivo")
                        .setPositiveButton("Ok"){d,i-> d.dismiss()}.show()
                }
                texto.setText("")
            }
            if(rbmemoriasd.isChecked){
                Toast.makeText(this,"Memoria Externa", Toast.LENGTH_LONG).show()
                AlertDialog.Builder(this).setTitle("Atencion")
                    .setMessage("Se guardo el archivo").setPositiveButton("Ok"){d,i->d.dismiss()}
                    .show()
            }
        }

        btnabrir.setOnClickListener {
            if(rbmemoriaint.isChecked){
                if(abrirDesdeMemoriaInterna()==true){
                    Toast.makeText(this,"Memoria Interna", Toast.LENGTH_LONG).show()
                    AlertDialog.Builder(this).setTitle("Atencion")
                        .setMessage("Se abrira el archivo").setPositiveButton("Ok"){d,i->d.dismiss()}
                        .show()
                }else{
                    android.app.AlertDialog.Builder(this).setTitle("ERROR").setMessage("No se abrio el archivo")
                        .setPositiveButton("Ok"){d,i-> d.dismiss()}.show()
                }
            }
            if(rbmemoriasd.isChecked){
                Toast.makeText(this,"Memoria Externa", Toast.LENGTH_LONG).show()
                AlertDialog.Builder(this).setTitle("Atencion")
                    .setMessage("Se abrira el archivo").setPositiveButton("Ok"){d,i->d.dismiss()}
                    .show()
            }
        }
    }

    private fun abrirDesdeMemoriaInterna(): Boolean {
        var contenido =""
        var flujoEntrada = BufferedReader(InputStreamReader(openFileInput(textnombredoc.text.toString())))
        try {
            contenido=flujoEntrada.readLine().toString()
            texto.setText(contenido)
            flujoEntrada.close()
        }catch (io:IOException){
            return false
        }
        return true;
    }

    private fun guardarEnMemoriaInterna(): Boolean{
        var nombredoc=textnombredoc.text.toString()
        try {
            var flujosalida = OutputStreamWriter(openFileOutput(nombredoc, Context.MODE_PRIVATE))
            var data = texto.text.toString()

            flujosalida.write(data)
            flujosalida.flush()
            flujosalida.close()
        }catch (io:IOException){
            return false;
        }
        return true;
    }
}