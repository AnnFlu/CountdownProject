package it.polito.countdown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class CountdownActivity : AppCompatActivity() {

    lateinit var textViewSeconds : TextView
    lateinit var textViewMinutes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
         var sec= 59000
        setContentView(R.layout.activity_main)
        textViewSeconds = findViewById(R.id.seconds)
        textViewMinutes = findViewById(R.id.minutes)

       object : CountDownTimer(3600000, 60000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                textViewMinutes.setText("" + millisUntilFinished / 60000)
            }

            override fun onFinish() {

            }
        }.start()

        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(sec.toLong() , 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                textViewSeconds.setText("" + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
            this.start()
            }
        }.start()
        }

    }
