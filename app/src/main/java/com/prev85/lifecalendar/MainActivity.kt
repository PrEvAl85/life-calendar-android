package com.prev85.lifecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.prev85.lifecalendar.ui.AppNav
import com.prev85.lifecalendar.ui.theme.LifeCalendarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeCalendarTheme {
                AppNav()
            }
        }
    }
}
