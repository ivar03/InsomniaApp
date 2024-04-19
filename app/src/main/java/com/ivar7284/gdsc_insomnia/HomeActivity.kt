package com.ivar7284.gdsc_insomnia

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class HomeActivity : AppCompatActivity() {

    private lateinit var profileBtn: ImageButton
    private lateinit var homeNav: LinearLayout
    private lateinit var eegNav: LinearLayout
    private lateinit var chatbotNav: LinearLayout
    private lateinit var ircamnav: LinearLayout
    private lateinit var reportNav: LinearLayout

    private lateinit var lineChart: LineChart

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        overridePendingTransition(0, 0)

        //lineChart
        lineChart = findViewById(R.id.line_chart)
        val lineDataSet = LineDataSet(dataVals(), "DataSet1")
        lineDataSet.color = Color.YELLOW
        lineDataSet.valueTextColor = Color.WHITE
        lineDataSet.valueTextSize = 8f
        lineDataSet.setDrawCircles(true)
        lineDataSet.setCircleColor(Color.YELLOW)
        lineDataSet.setCircleRadius(1f)
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER)

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)
        val data = LineData(dataSets)
        lineChart.data = data

        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)

        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawLabels(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisLeft.setDrawLabels(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisRight.setDrawLabels(false)

        lineChart.legend.isEnabled = false
        lineChart.setScaleEnabled(false)
        lineChart.setPinchZoom(false)

        lineChart.setDrawBorders(true)
        lineChart.setBackgroundColor(Color.TRANSPARENT)
        lineChart.setBorderWidth(2f)
        lineChart.setBorderColor(Color.rgb(100,100,100))

        lineChart.isScaleXEnabled = false
        lineChart.isScaleYEnabled = false
        lineChart.isHorizontalScrollBarEnabled = false
        lineChart.isVerticalScrollBarEnabled = false

        lineChart.invalidate()

        //nav bar bottom
        homeNav = findViewById(R.id.Hhome_nav)
        eegNav = findViewById(R.id.Heeg_nav)
        chatbotNav = findViewById(R.id.Hchatbot_nav)
        ircamnav = findViewById(R.id.Hircam_nav)
        reportNav = findViewById(R.id.Hreport_nav)

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
        profileBtn = findViewById(R.id.HprofileBtn)
        profileBtn.setOnClickListener {
            startActivity(Intent(applicationContext, ProfileActivity :: class.java))
        }
    }
    private fun dataVals() : ArrayList<Entry> {
        val dataVals = ArrayList<Entry>()
        dataVals.add(Entry(0F, 10F))
        dataVals.add(Entry(1F, 20F))
        dataVals.add(Entry(2F, 40F))
        dataVals.add(Entry(3F, 30F))
        dataVals.add(Entry(4F, 40F))
        dataVals.add(Entry(5F, 50F))

        return dataVals
    }
    //
}