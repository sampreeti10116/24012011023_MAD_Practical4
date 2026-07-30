package com.example.a24012011023_mad_practical4

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    lateinit var textAlarm: TextView
    lateinit var cardSetAlarm: MaterialCardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textAlarm=findViewById<TextView>(R.id.textView4)
        cardSetAlarm=findViewById(R.id.card_list)
        cardSetAlarm.visibility= View.GONE
        findViewById<MaterialButton>(R.id.btnSetAlarm).setOnClickListener {

        }
        findViewById<MaterialButton>(R.id.btnCancelAlarm).setOnClickListener {

        }
    }
    private fun showTimedialog(){
        val cldr: Calendar= Calendar.getInstance()
        val h:Int = cldr.get(Calendar.HOUR_OF_DAY)
        val m:Int=cldr.get(Calendar.MINUTE)
        val picker= TimePickerDialog(
            this,
            {tp,sHour,sMinute->sendDialogDataToActivity(sHour,sMinute)},
            h,m,false
        )
        picker.show()
    }
    private fun sendDialogDataToActivity(hour:Int,minute:Int){

    }
}