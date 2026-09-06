package com.stitch.messenger.utils

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * Manages runtime permissions for Android 6.0+
 */
class PermissionManager(private val context: Context) {

    // Required permissions
    private val requiredPermissions = listOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_PHONE_STATE
    )

    // Dangerous permissions (need runtime request)
    val dangerousPermissions = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    /**
     * Check if permission is granted
     */
    fun isPermissionGranted(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    /**
     * Check if all permissions are granted
     */
    fun areAllPermissionsGranted(): Boolean {
        return dangerousPermissions.all { isPermissionGranted(it) }
    }

    /**
     * Get permissions that need to be requested
     */
    fun getPermissionsToRequest(): List<String> {
        return dangerousPermissions.filter { !isPermissionGranted(it) }
    }

    /**
     * Show permission explanation dialog
     */
    fun showPermissionExplanation(
        permission: String,
        onConfirm: () -> Unit
    ) {
        val message = when (permission) {
            Manifest.permission.CAMERA -> "Camera is needed for video calls"
            Manifest.permission.RECORD_AUDIO -> "Microphone is needed for audio/video calls"
            Manifest.permission.READ_CONTACTS -> "Contacts access helps find your friends"
            Manifest.permission.READ_EXTERNAL_STORAGE -> "Storage access allows sharing files"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> "Storage write access for saving media"
            Manifest.permission.ACCESS_FINE_LOCATION -> "Location access for location sharing"
            else -> "Permission required for app functionality"
        }

        AlertDialog.Builder(context)
            .setTitle("Permission Required")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> onConfirm() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    /**
     * Check Android version
     */
    fun getAndroidVersion(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * Get Android version name
     */
    fun getAndroidVersionName(): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> "Android 14 (API 34)"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> "Android 13 (API 33)"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> "Android 12 (API 31)"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> "Android 11 (API 30)"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> "Android 10 (API 29)"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> "Android 9 (API 28)"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> "Android 8 (API 26-27)"
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> "Android 7 (API 24-25)"
            else -> "Unknown"
        }
    }

    /**
     * Check if device meets minimum requirements
     */
    fun meetsMinimumRequirements(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N // API 24
    }
}
