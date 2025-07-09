package com.foxmobile.foxnotemini.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.foxmobile.foxnotemini.composables.OverviewScreen
import com.foxmobile.foxnotemini.navigation.NavigationRoot
import com.foxmobile.foxnotemini.ui.theme.FoxNoteMiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(SystemBarStyle.dark(0))
        super.onCreate(savedInstanceState)
        setContent {
            FoxNoteMiniTheme {
                    NavigationRoot(
                        modifier = Modifier
                            .fillMaxSize()
                    )
            }
        }
    }
}