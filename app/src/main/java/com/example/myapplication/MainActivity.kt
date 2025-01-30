package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Setup ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Request permission when button is clicked

        PermissTools.register(this)
        binding.btnRequestPermission.setOnClickListener {



            PermissTools.request(this@MainActivity,PermissionConstants.CAMERA_PERMISSIONS){granted->
                if (granted) accessAudioLibrary()
            }

        }










    }

//    private fun checkAndRequestAudioPermission() {
//        if (isAudioPermissionGranted()) {
//            accessAudioLibrary()
//        } else if (shouldShowRequestPermissionRationale()) {
//            // User has denied permission before, show custom dialog
//            showPermissionRationaleDialog()
//        } else {
//            // Request permission normally
//            requestAudioPermission()
//        }
//    }
//
//    private fun isAudioPermissionGranted(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ContextCompat.checkSelfPermission(
//                this, Manifest.permission.READ_MEDIA_AUDIO
//            ) == PackageManager.PERMISSION_GRANTED
//        } else {
//            ContextCompat.checkSelfPermission(
//                this, Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        }
//    }
//
//    private fun shouldShowRequestPermissionRationale(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_AUDIO)
//        } else {
//            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//        }
//    }
//
//    private fun requestAudioPermission() {
//        requestAudioPermissionLauncher.launch(
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                arrayOf(Manifest.permission.READ_MEDIA_AUDIO)
//            } else {
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
//            }
//        )
//    }
//
//    private val requestAudioPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                permissions[Manifest.permission.READ_MEDIA_AUDIO] ?: false
//            } else {
//                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
//            }
//
//            if (granted) {
//                accessAudioLibrary()
//            } else {
//                if (!shouldShowRequestPermissionRationale()) {
//                    // Permission permanently denied, show custom dialog
//                    showSettingsDialog()
//                } else {
//                    // Permission denied, but can request again
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//
//    private fun showPermissionRationaleDialog() {
//        AlertDialog.Builder(this)
//            .setTitle("Permission Needed")
//            .setMessage("This app requires access to your audio library to function properly.")
//            .setPositiveButton("Grant") { _, _ ->
//                requestAudioPermission()
//            }
//            .setNegativeButton("Cancel", null)
//            .show()
//    }
//
//    private fun showSettingsDialog() {
//        AlertDialog.Builder(this)
//            .setTitle("Permission Required")
//            .setMessage("Audio permission is permanently denied. You need to enable it in settings.")
//            .setPositiveButton("Go to Settings") { _, _ ->
//                val intent = Intent(
//                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.fromParts("package", packageName, null)
//                )
//                startActivity(intent)
//            }
//            .setNegativeButton("Cancel", null)
//            .show()
//    }
//
    private fun accessAudioLibrary() {
        Toast.makeText(this, "Audio Access Granted!", Toast.LENGTH_LONG).show()
    }
}
