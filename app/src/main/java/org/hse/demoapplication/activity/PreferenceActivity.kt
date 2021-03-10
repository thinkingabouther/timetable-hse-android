package org.hse.demoapplication.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_preference.*
import org.hse.demoapplication.BuildConfig
import org.hse.demoapplication.R
import org.hse.demoapplication.shared.PreferenceManager
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PreferenceActivity : AppCompatActivity(), SensorEventListener {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PERMISSION_CODE = 10
    private val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    private val timestampPattern = "yyyyMMdd_HHmmss"

    private lateinit var userPhotoPath: String
    private lateinit var preferenceManager : PreferenceManager
    private lateinit var sensorManager : SensorManager
    private lateinit var lightSensor : Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        preferenceManager = PreferenceManager(applicationContext)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


        createPhotoButton.setOnClickListener { checkPermission() }
        saveButton.setOnClickListener { saveUserData() }

        loadUserData()
        loadSensorsList()
    }
    
    private fun checkPermission() {
        val permissionCheck = ActivityCompat.checkSelfPermission(this, CAMERA_PERMISSION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA_PERMISSION)) {
                Toast.makeText(applicationContext,
                    getString(R.string.cameraPermissionToast_text),
                    Toast.LENGTH_LONG)
                    .show()
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION), REQUEST_PERMISSION_CODE)
            }
        }
        else dispatchTakePictureIntent()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            }
            else {
                Log.d("RequestPermissionResult", "Permission not granted")
                ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION), REQUEST_PERMISSION_CODE)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        val currentDate : Date = Calendar.getInstance().time;
        val timeStamp: String = SimpleDateFormat(timestampPattern, Locale.getDefault()).format(currentDate.time)
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            getString(R.string.userPhotoFileNamePrefix, timeStamp),
            getString(R.string.userPhotoFileNameSuffix),
            storageDir
        ).apply {
            userPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            loadUserPhoto()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadUserPhoto() {
        userPhotoPath.let { Glide.with(this).load(userPhotoPath).into(avatarImageView)}
    }

    private fun saveUserData() {
        preferenceManager.set(getString(R.string.nameSharedPreferencesKey), nameEditText.text.toString())
        userPhotoPath.let { preferenceManager.set(getString(R.string.userPhotoSharedPreferencesKey), it) }
        Toast.makeText(applicationContext,
            getString(R.string.userDataSavedToast_text),
            Toast.LENGTH_LONG)
            .show()
    }

    private fun loadUserData() {
        val defaultValue = getString(R.string.sharedPreferencesGetDefaultValue)
        val userName = preferenceManager.get(getString(R.string.nameSharedPreferencesKey))
        if (userName != defaultValue) {
            nameEditText.setText(userName)
        }
        val savedUserPhotoPath = preferenceManager.get(getString(R.string.userPhotoSharedPreferencesKey))
        if (savedUserPhotoPath != defaultValue && savedUserPhotoPath != null) {
            userPhotoPath = savedUserPhotoPath
            loadUserPhoto()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensorValue = event?.values?.get(0)
        currentLightLabel.text = getString(R.string.currentLightLabel_text, sensorValue)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("AccuracyChanged", "Accuracy changed (somehow...)")
    }

    private fun loadSensorsList() {
        val list = sensorManager.getSensorList(Sensor.TYPE_ALL).joinToString("\n"){ it.name }
        sensorsListLabel.text = getString(R.string.sensorsListLabel_text, list)
    }
}

