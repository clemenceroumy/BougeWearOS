package fr.croumy.bouge.presentation.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionService @Inject constructor(
    val context: Context
) {
    companion object {
        const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val ACTIVITY_RECOGNITION = Manifest.permission.ACTIVITY_RECOGNITION
        const val NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS
        const val BLUETOOTH_ADVERTISE = Manifest.permission.BLUETOOTH_ADVERTISE
        const val BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
        const val BLUETOOTH_SCAN = Manifest.permission.BLUETOOTH_SCAN
    }

    fun isNotificationPermissionsGranted(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    }
}