/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.okhttp.OkHttpStack

@HiltAndroidApp
class Smart : Application() {

    companion object {

        var instances: Smart? = null
        const val notificationChannelID = "SmartMediaChannel"

        @Synchronized
        fun getInstance(): Smart? {
            var smart: Smart?
            synchronized(Smart::class.java) { smart = instances }
            return smart
        }
    }
    override fun onCreate() {
        super.onCreate()
        instances = this
        createNotificationChannel()
        initializeUploadService()
        if (BuildConfig.DEBUG) {
            val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
                .methodCount(0) // (Optional) How many method line to show. Default 2
                .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
                .tag("::APP:") // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()

            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        }
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                Smart.notificationChannelID,
                "SmartAppChannel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
//        requestRequiredPermissions()
    }
    private fun initializeUploadService() {
        val service = UploadServiceConfig
        service.initialize(
            context = this,
            defaultNotificationChannel = Smart.notificationChannelID,
            debug = BuildConfig.DEBUG
        )
        service.httpStack = OkHttpStack()
    }

}