package com.tobibur.aidl_client

import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tobibur.aidl_client.domain.model.MenuItem
import com.tobibur.aidl_client.ui.screens.MainMenuScreen
import com.tobibur.aidl_client.ui.screens.MainMenuViewModel
import com.tobibur.aidl_client.ui.theme.AIDLclientTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    private val mainMenuViewModel: MainMenuViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIDLclientTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "AIDL Client") },
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
                    MainMenuScreen(
                        modifier = Modifier.padding(innerPadding),
                        menuList = mainMenuViewModel.menuItemsFlow,
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
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    AIDLclientTheme {
        MainMenuScreen(
            modifier = Modifier.fillMaxSize(),
            menuList = MutableStateFlow(
                listOf(
                    MenuItem(1, "Menu Item 1", false),
                    MenuItem(2, "Menu Item 2", true),
                    MenuItem(3, "Menu Item 3", false)
                )
            ),
            onCheckedChange = {},
            onDelete = {}
        )
    }
}