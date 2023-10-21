package it.polito.countdown

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.view.get
import it.polito.countdown.R


class CountdownActivity : AppCompatActivity(){
    lateinit var textViewSec : TextView
    lateinit var timer : CountDownTimer
    lateinit var circle : ProgressBar
    lateinit var text : TextView
    lateinit var numPickerHour : com.shawnlin.numberpicker.NumberPicker
    lateinit var numPickerMin : com.shawnlin.numberpicker.NumberPicker
    lateinit var numPickerSec : com.shawnlin.numberpicker.NumberPicker
    lateinit var layoutPicker : LinearLayout
    lateinit var layoutTimer : TableLayout
    lateinit var labels : LinearLayout

    lateinit var b : Button
    lateinit var r : Button
    lateinit var setTimerButton : Button

    var timeRemaining: Long=70000
    var timeStart: Long=70000

    private var timeRunning=false

    lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewSec = findViewById(R.id.seconds)
        b = findViewById(R.id.pause)
        r = findViewById(R.id.reset)
        setTimerButton= findViewById(R.id.setTimer)
        numPickerHour=findViewById(R.id.numPickerHours)
        numPickerMin=findViewById(R.id.numPickerMin)
        numPickerSec=findViewById(R.id.numPickerSec)
        layoutPicker=findViewById(R.id.settingTimer)
        layoutTimer=findViewById(R.id.timerLayout)
        labels=findViewById(R.id.pickersLabels)
        progressBar=findViewById(R.id.pbTimer)

        r.setOnClickListener{resetTimer()}
        b.setOnClickListener {
            if (timeRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        setTimerButton.setOnClickListener{
            var sec=numPickerSec.value
            var min=numPickerMin.value
            var h=numPickerHour.value


            setTimer(sec, min, h)
            if(timeRemaining>0){
                startTimer()
                visibleTimer()
            }

        }

        numPickerHour.minValue = 0
        numPickerHour.maxValue = 24
        numPickerMin.minValue = 0
        numPickerMin.maxValue = 59
        numPickerSec.minValue = 0
        numPickerSec.maxValue = 59


        visibleSettingTimer()

    }

    private fun visibleTimer(){
        layoutPicker.visibility=View.INVISIBLE
        setTimerButton.visibility=View.INVISIBLE
        labels.visibility=View.INVISIBLE
        layoutTimer.visibility=View.VISIBLE
    }

    private fun visibleSettingTimer(){
        layoutPicker.visibility=View.VISIBLE
        setTimerButton.visibility=View.VISIBLE
        labels.visibility=View.VISIBLE
        layoutTimer.visibility=View.INVISIBLE

    }

    private fun setTimer(s: Int, m:Int, h:Int) {

        timeStart=((s+m*60+h*60*60)*1000).toLong()
        timeRemaining=timeStart
        updateTimerTextView(timeStart)
        progressBar.max=timeStart.toInt()
    }


    private fun startTimer(){
        progressBar.progress = 0 // barra completa
        timer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if(timeRunning && timeRemaining>0){
                    updateTimerTextView(timeRemaining)
                    timeRemaining=timeRemaining-1000
                    progressBar.progress = timeRemaining.toInt()
                        //in base al tempo rimanente la barra sparisce in senso antiorario
                }
            }

            override fun onFinish() {
                timeRunning=false
                timeRemaining=0
                numPickerHour.value = 0
                numPickerSec.value = 0
                numPickerMin.value = 0

                updateTimerTextView(0)
                b.text="Start"
            }
        }
        if(timeRemaining>0){
            timer.start()
            b.text="Pause"
        }
        if(!timeRunning){
            timeRunning=true
        }

    }

    private fun pauseTimer() {
        timer.cancel()
        timeRunning=false
        b.text="Resume"
    }

    private fun resetTimer(){
        timer.onFinish()
        timer.cancel() //va messo sennò corrono più timer in contemporanea, e invece di aggiornarsi ogni secondo lo fa ogni mezzo secondo o meno
        b.text="Start"
        visibleSettingTimer()


    }

    private fun updateTimerTextView(secondsRemaining: Long) {
        val seconds = (secondsRemaining % 60000) / 1000
        val hours = secondsRemaining / 3600000
        val minutes = (secondsRemaining / 60000)%60
        val timeString = String.format("%02d:%02d:%02d",hours, minutes, seconds)

        textViewSec.text = timeString
    }



}