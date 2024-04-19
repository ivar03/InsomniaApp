package com.ivar7284.gdsc_insomnia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout

class EEGActivity : AppCompatActivity() {

    private lateinit var profileBtn: ImageButton
    private lateinit var homeNav: LinearLayout
    private lateinit var eegNav: LinearLayout
    private lateinit var chatbotNav: LinearLayout
    private lateinit var ircamnav: LinearLayout
    private lateinit var reportNav: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eegactivity)
        overridePendingTransition(0, 0)

        //nav bar bottom
        homeNav = findViewById(R.id.Ehome_nav)
        eegNav = findViewById(R.id.Eeeg_nav)
        chatbotNav = findViewById(R.id.Echatbot_nav)
        ircamnav = findViewById(R.id.Eircam_nav)
        reportNav = findViewById(R.id.Ereport_nav)

        homeNav.setOnClickListener {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }

        eegNav.setOnClickListener {
            startActivity(Intent(applicationContext, EEGActivity::class.java))
            finish()
        }

        chatbotNav.setOnClickListener {
            startActivity(Intent(applicationContext, ChatBotActivity::class.java))
            finish()
        }

        ircamnav.setOnClickListener {
            startActivity(Intent(applicationContext, IRCamActivity::class.java))
            finish()
        }

        reportNav.setOnClickListener {
            startActivity(Intent(applicationContext, ReportActivity::class.java))
            finish()
        }





        //profile activity
        profileBtn = findViewById(R.id.EprofileBtn)
        profileBtn.setOnClickListener {
            startActivity(Intent(applicationContext, ProfileActivity :: class.java))
        }
    }
}