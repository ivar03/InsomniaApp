package com.ivar7284.gdsc_insomnia

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout

class IRCamActivity : AppCompatActivity() {

    private lateinit var profileBtn: ImageButton
    private lateinit var homeNav: LinearLayout
    private lateinit var eegNav: LinearLayout
    private lateinit var chatbotNav: LinearLayout
    private lateinit var ircamnav: LinearLayout
    private lateinit var reportNav: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ircam)
        overridePendingTransition(0, 0)


        //nav bar bottom
        homeNav = findViewById(R.id.Ihome_nav)
        eegNav = findViewById(R.id.Ieeg_nav)
        chatbotNav = findViewById(R.id.Ichatbot_nav)
        ircamnav = findViewById(R.id.Iircam_nav)
        reportNav = findViewById(R.id.Ireport_nav)

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
        profileBtn = findViewById(R.id.IprofileBtn)
        profileBtn.setOnClickListener {
            startActivity(Intent(applicationContext, ProfileActivity :: class.java))
        }
    }
}