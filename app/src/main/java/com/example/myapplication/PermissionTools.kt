package com.example.myapplication


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity

import androidx.activity.result.ActivityResultLauncher


import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


//object PermissionTools {
//
//    private lateinit var requestLauncher: ActivityResultLauncher<Array<String>>
//
//    fun register(activity: AppCompatActivity) {
//        requestLauncher = activity.registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissions ->
//            handlePermissionResult(activity, permissions)
//        }
//    }
//
//    fun register(fragment: Fragment) {
//        requestLauncher = fragment.registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissions ->
//            handlePermissionResult(fragment.requireActivity(), permissions)
//        }
//    }
//
//    fun request(activity: Activity, callback: (Boolean) -> Unit) {
//        if (isPermissionGranted(activity)) {
//            callback(true)
//        } else if (shouldShowRationale(activity)) {
//            showRationaleDialog(activity) {
//                requestLauncher.launch(getPermissionsArray())
//            }
//        } else {
//            requestLauncher.launch(getPermissionsArray())
//        }
//    }
//
//    private fun isPermissionGranted(context: Context): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ContextCompat.checkSelfPermission(
//                context, android.Manifest.permission.READ_MEDIA_AUDIO
//            ) == PackageManager.PERMISSION_GRANTED
//        } else {
//            ContextCompat.checkSelfPermission(
//                context, android.Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        }
//    }
//
//    private fun shouldShowRationale(activity: Activity): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_MEDIA_AUDIO)
//        } else {
//            ActivityCompat.shouldShowRequestPermissionRationale(activity,android. Manifest.permission.READ_EXTERNAL_STORAGE)
//        }
//    }
//
//    private fun getPermissionsArray(): Array<String> {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO)
//        } else {
//            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
//        }
//    }
//
//    private fun handlePermissionResult(activity: Activity, permissions: Map<String, Boolean>) {
//        val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            permissions[android.Manifest.permission.READ_MEDIA_AUDIO] ?: false
//        } else {
//            permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
//        }
//
//        if (granted) {
//            Toast.makeText(activity, "Permission Granted!", Toast.LENGTH_SHORT).show()
//        } else {
//            if (!shouldShowRationale(activity)) {
//                showSettingsDialog(activity)
//            } else {
//                Toast.makeText(activity, "Permission Denied!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun showRationaleDialog(activity: Activity, onAccept: () -> Unit) {
//        AlertDialog.Builder(activity)
//            .setTitle("Permission Required")
//            .setMessage("This app needs access to your audio files.")
//            .setPositiveButton("Grant") { _, _ -> onAccept() }
//            .setNegativeButton("Cancel", null)
//            .show()
//    }
//
//    private fun showSettingsDialog(activity: Activity) {
//        AlertDialog.Builder(activity)
//            .setTitle("Permission Required")
//            .setMessage("Audio permission is permanently denied. Enable it in settings.")
//            .setPositiveButton("Go to Settings") { _, _ ->
//                val intent = Intent(
//                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.fromParts("package", activity.packageName, null)
//                )
//                activity.startActivity(intent)
//            }
//            .setNegativeButton("Cancel", null)
//            .show()
//    }
//}
object PermissTools {

    private lateinit var requestLauncher: ActivityResultLauncher<Array<String>>
    private var permissionCallback: ((Boolean) -> Unit)? = null

    fun register(activity: AppCompatActivity) {
        requestLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            handlePermissionResult(activity, permissions)
        }
    }

    fun register(fragment: Fragment) {
        requestLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            handlePermissionResult(fragment.requireActivity(), permissions)
        }
    }

    fun request(activity: Activity, permissions: Array<String>, callback: (Boolean) -> Unit) {
        permissionCallback = callback

        if (isPermissionGranted(activity, permissions)) {
            callback(true)
        } else if (shouldShowRationale(activity, permissions)) {
            showRationaleDialog(activity) {
                requestLauncher.launch(permissions)
            }
        } else {
            requestLauncher.launch(permissions)
        }
    }

    private fun isPermissionGranted(context: Context, permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun shouldShowRationale(activity: Activity, permissions: Array<String>): Boolean {
        return permissions.any {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
        }
    }

    private fun handlePermissionResult(activity: Activity, permissions: Map<String, Boolean>) {
        val allGranted = permissions.all { it.value }

        if (allGranted) {
            Toast.makeText(activity, "Permission Granted!", Toast.LENGTH_SHORT).show()
            permissionCallback?.invoke(true)
        } else {
            if (!shouldShowRationale(activity, permissions.keys.toTypedArray())) {
                showSettingsDialog(activity)
            } else {
                Toast.makeText(activity, "Permission Denied!", Toast.LENGTH_SHORT).show()
                permissionCallback?.invoke(false)
            }
        }
    }

    private fun showRationaleDialog(activity: Activity, onAccept: () -> Unit) {
        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("This app needs access to this feature to function properly.")
            .setPositiveButton("Grant") { _, _ -> onAccept() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showSettingsDialog(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("Permission is permanently denied. Enable it in settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", activity.packageName, null)
                )
                activity.startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
