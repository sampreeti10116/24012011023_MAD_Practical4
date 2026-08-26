package com.example.a24012011023_mad_practical4

import android.app.PendingIntent
import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.Locale

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

        textAlarm=findViewById<TextClock>(R.id.textView4)
        cardSetAlarm=findViewById(R.id.card_list)
        cardSetAlarm.visibility= View.GONE

        findViewById<MaterialButton>(R.id.btnSetAlarm).setOnClickListener {
            showTimedialog()
        }
        findViewById<MaterialButton>(R.id.btnCancelAlarm).setOnClickListener {
            setAlarm(-1, AlarmBroadcastReceiver.STOP_VAL)
            cardSetAlarm.visibility= View.GONE
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
        val cldr: Calendar= Calendar.getInstance()
        cldr.set(Calendar.HOUR_OF_DAY,hour)
        cldr.set(Calendar.MINUTE,minute)
        cldr.set(Calendar.SECOND,0)
        if(setAlarm(cldr.timeInMillis, AlarmBroadcastReceiver.START_VAL)){
            cardSetAlarm.visibility=View.VISIBLE
            findViewById<TextView>(R.id.alarmTimeValue).text=String.format("%02d:%02d",hour,minute)
        }
    }

    private fun setAlarm(modifyTime: Long, str: String): Boolean {
        val intent= Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra(AlarmBroadcastReceiver.SERVICE_KEY,str)
        val pendingIntent=
            PendingIntent.getBroadcast(applicationContext,
                240111023,intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager=getSystemService(ALARM_SERVICE)as AlarmManager
        if(str== AlarmBroadcastReceiver.START_VAL){
            if(alarmManager.canScheduleExactAlarms()){
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,modifyTime,pendingIntent
                )
                Toast.makeText(this, "Started Alarm", Toast.LENGTH_SHORT).show()
                return true
            }
            else{
                Toast.makeText(this, "No permission to set alarm", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        else if(str== AlarmBroadcastReceiver.STOP_VAL){
            sendBroadcast(intent)
            alarmManager.cancel(pendingIntent)
            return true
        }
        return false
    }
}