package id.co.sherly.mart.utils.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

tailrec fun Context?.activity(): Activity? = this as? Activity
    ?: (this as? ContextWrapper)?.baseContext?.activity()

tailrec fun Context?.appCompatActivity(): Activity? = this as? AppCompatActivity
    ?: (this as? ContextWrapper)?.baseContext?.appCompatActivity()