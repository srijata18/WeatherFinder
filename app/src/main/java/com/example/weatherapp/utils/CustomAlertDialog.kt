package com.example.weatherapp.utils

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.weatherapp.R

/*
*
* Singleton class used for displaying alerts and error handling cases, you can add positive button
* alert view generation or simply use negetive button for displaying the error cases*/
object CustomAlertDialog {
    fun displayAlert(context: Context,
                     msg : String,
                     btnNegetiveMsg : String?="",
                     btnPositivemMsg : String?="",
    isError: Boolean=false)  {
        val builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.alert_layout, null)
        builder.setView(view)
        val text = view.findViewById<TextView>(R.id.alert_msg)
        val img = view.findViewById<ImageView>(R.id.alert_img)
        val negetiveBtn = view.findViewById<Button>(R.id.btn_negetive)
        text?.text = msg
        negetiveBtn?.text = btnNegetiveMsg
        val alert = builder.create()
        negetiveBtn?.setOnClickListener{
            alert.dismiss()
        }
        alert.show()
    }
}