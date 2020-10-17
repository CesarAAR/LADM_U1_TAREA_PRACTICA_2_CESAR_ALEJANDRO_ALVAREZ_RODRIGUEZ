package com.example.ladm_u1_tarea_practica_2_cesar_alejandro_alvarez_rodriguez

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
        }


        btnguardar.setOnClickListener {
            if(rbmemoriaint.isChecked){
                if(guardarEnMemoriaInterna()==true){
                    Toast.makeText(this,"Memoria Interna", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this,"Memoria Externa", Toast.LENGTH_SHORT).show()
                if(guardarEnMemoriaExterna()==true){
                    AlertDialog.Builder(this).setTitle("Atencion").setMessage("Se guardo el archivo en SD")
                        .setPositiveButton("Ok"){d,i-> d.dismiss()}.show()
                }else{
                    android.app.AlertDialog.Builder(this).setTitle("ERROR").setMessage("NO se guardo el archivo")
                        .setPositiveButton("Ok"){d,i-> d.dismiss()}.show()
                }
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
                    android.app.AlertDialog.Builder(this).setTitle("ERROR").setMessage("No se encontro el archivo")
                        .setPositiveButton("Ok"){d,i-> d.dismiss()}.show()
                }
            }
            if(rbmemoriasd.isChecked){
                Toast.makeText(this,"Memoria Externa", Toast.LENGTH_LONG).show()
                if(abrirEnMemoriaExterna()==true){
                    AlertDialog.Builder(this).setTitle("Atencion").setMessage("Se abrio el archivo desde SD")
                        .setPositiveButton("Ok"){d,i-> d.dismiss()}.show()
                }else{
                    android.app.AlertDialog.Builder(this).setTitle("ERROR").setMessage("NO se encontro el archivo")
                        .setPositiveButton("Ok"){d,i-> d.dismiss()}.show()
                }
            }
        }
    }

    private fun abrirEnMemoriaExterna(): Boolean {
        try{
            var rutaSD= Environment.getExternalStorageDirectory()
            var f = File(rutaSD.absolutePath, textnombredoc.text.toString())
            var ff = BufferedReader(InputStreamReader(FileInputStream(f)))

            var contenido = ""
            contenido=ff.readLine().toString()
            texto.setText(contenido)
            ff.close()
        }catch (io: Exception){
            return false;
        }
        return true;
    }

    private fun guardarEnMemoriaExterna(): Boolean{
        try {
            var rutaSD= Environment.getExternalStorageDirectory()
            var archivoSD = File(rutaSD.absolutePath,textnombredoc.text.toString())
            var flujosalidaSD = OutputStreamWriter(FileOutputStream(archivoSD))
            var data = texto.text.toString()
            flujosalidaSD.write(data)
            flujosalidaSD.flush()
            flujosalidaSD.close()
        }catch (ioe:IOException){
            return false
        }
        return true
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