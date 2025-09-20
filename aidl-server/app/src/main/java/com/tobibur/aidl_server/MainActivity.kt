package com.tobibur.aidl_server

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tobibur.aidl_server.data.MenuRepository
import com.tobibur.aidl_server.domain.model.MenuItem
import com.tobibur.aidl_server.service.MenuService
import com.tobibur.aidl_server.ui.screens.MainMenu
import com.tobibur.aidl_server.ui.screens.MainMenuViewModel
import com.tobibur.aidl_server.ui.theme.AIDLserverTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainMenuViewModel: MainMenuViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIDLserverTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "AIDL Server") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val menuList by mainMenuViewModel.menuItems.collectAsState(initial = emptyList())

                    LaunchedEffect(Unit) {
                        delay(1000)
                        if (menuList.isEmpty()) {
                            MenuRepository.getMenuList().forEach {
                                mainMenuViewModel.insertMenuItem(
                                    MenuItem(
                                        it.id,
                                        it.title,
                                        it.isChecked
                                    )
                                )
                            }
                            Toast.makeText(
                                this@MainActivity,
                                "Syncing menu items, please wait...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    MainMenu(
                        modifier = Modifier.padding(innerPadding),
                        menuList = mainMenuViewModel.menuItems,
                        onCheckedChange = { menuItem ->
                            // Handle item click if needed
                            mainMenuViewModel.insertMenuItem(menuItem)
                        },
                        onDelete = { menuItem ->
                            mainMenuViewModel.deleteMenuItem(menuItem)
                        }
                    )
                }
            }
        }

        val intent = Intent(this, MenuService::class.java)
        startService(intent)
        bindService(intent, object : android.content.ServiceConnection {
            override fun onServiceConnected(
                name: android.content.ComponentName?,
                service: android.os.IBinder?
            ) {
                Log.d(TAG, "onServiceConnected: connected")
            }

            override fun onServiceDisconnected(name: android.content.ComponentName?) {
                Log.d(TAG, "onServiceDisconnected: disconnected")
            }
        }, BIND_AUTO_CREATE)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIDLserverTheme {
        MainMenu(
            modifier = Modifier.fillMaxSize(),
            menuList = MutableStateFlow(
                listOf(
                    MenuItem(1, "Pizza", false),
                    MenuItem(2, "Burger", true),
                    MenuItem(3, "Pasta", false)
                )
            )
        )
    }
}