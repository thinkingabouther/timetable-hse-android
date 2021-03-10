package org.hse.demoapplication.activity.useractivity

import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.*
import org.hse.demoapplication.R
import org.hse.demoapplication.activity.ScheduleActivity.ScheduleActivity
import org.hse.demoapplication.model.enums.ScheduleMode
import org.hse.demoapplication.model.enums.ScheduleType
import org.hse.demoapplication.model.spinner.SpinnerItem
import org.hse.demoapplication.model.json.TimeResponse
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

abstract class AbstractUserActivity : AppCompatActivity() {

    private val TAG = "AbstractUserActivity"
    private val URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977"
    protected lateinit var timeLabel : TextView
    private lateinit var time : Date

    private val client : OkHttpClient = OkHttpClient()

    protected fun showSchedule(mode : ScheduleMode, type: ScheduleType, item: SpinnerItem) {
        intent = Intent(this, ScheduleActivity::class.java)
        intent.putExtra(ScheduleActivity.ARG_ID, item.getId())
        intent.putExtra(ScheduleActivity.ARG_TYPE, type)
        intent.putExtra(ScheduleActivity.ARG_MODE, mode)
        intent.putExtra(ScheduleActivity.ARG_NAME, item.getName())
        intent.putExtra(ScheduleActivity.ARG_TIME,
            SimpleDateFormat("EEEE, dd.MM", Locale("ru")).format(time))

        startActivity(intent)
    }

    protected fun initTime() {
        getTime()
    }

    private fun getTime() {
        val request = Request.Builder().url(URL).build()
        val call = client.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "getTime", e)
            }
            override fun onResponse(call: Call, response: Response) {
                parseResponse(response)
            }
        })
    }

    private fun parseResponse(response: Response) {
        val gson = Gson()
        val body = response.body
        try {
            if (body == null) {
                return
            }
            val string = body.string()
            Log.d(TAG, string)
            val timeResponse = gson.fromJson<TimeResponse>(string, TimeResponse::class.java)
            val currentTimeVal = timeResponse.getTimeZone().getCurrentTime()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale("en"))
            val dateTime = simpleDateFormat.parse(currentTimeVal)
            dateTime?.also {
                time = it
                runOnUiThread {
                    showTime(it)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "failed to parse response", e)
        }
    }

    private fun showTime(dateTime: Date) {
        val simpleDateFormat = SimpleDateFormat("HH:mm, EEEE", Locale("ru"))
        timeLabel.text = getString(R.string.timeLabel_text, simpleDateFormat.format(dateTime))
    }
}