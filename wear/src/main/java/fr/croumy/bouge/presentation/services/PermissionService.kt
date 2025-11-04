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
        const val LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
        const val ACTIVITY_RECOGNITION = android.Manifest.permission.ACTIVITY_RECOGNITION
        const val NOTIFICATIONS = android.Manifest.permission.POST_NOTIFICATIONS
    }

    fun isNotificationPermissionsGranted(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    }
}