package com.tobibur.aidl_client

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tobibur.aidl_client.domain.model.MenuItem
import com.tobibur.aidl_client.ui.screens.MainMenuScreen
import com.tobibur.aidl_client.ui.screens.MainMenuViewModel
import com.tobibur.aidl_client.ui.theme.AIDLclientTheme
import com.tobibur.aidl_server.IMenuCallback
import com.tobibur.aidl_server.IMenuService
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    private val mainMenuViewModel: MainMenuViewModel by viewModels()

    companion object {
        private const val TAG = "MainActivity"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG, "onCreate: Starting client. Loading..")

        val intent = Intent("com.tobibur.aidl_server.IMenuService")
        intent.setPackage("com.tobibur.aidl_server")
        val bound = bindService(intent, mConnection, BIND_AUTO_CREATE)
        Log.d(TAG, "Bind result = $bound")

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
                            try {
                                iRemoteService?.selectMenuItem(
                                    com.tobibur.aidl_server.domain.model.MenuItem(
                                        menuItem.id,
                                        menuItem.title,
                                        menuItem.isChecked
                                    )
                                )
                            } catch (e: RemoteException) {
                                e.printStackTrace()
                            }
                        },
                        onDelete = { menuItem ->
                            mainMenuViewModel.deleteMenuItem(menuItem)
                        }
                    )
                }
            }
        }
    }


    var iRemoteService: IMenuService? = null

    val mConnection = object : ServiceConnection {

        // Called when the connection with the service is established.
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // Following the preceding example for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service.
            iRemoteService = IMenuService.Stub.asInterface(service)
            Log.e(TAG, "Service connected")
            Toast.makeText(this@MainActivity, "Service connected", Toast.LENGTH_SHORT).show()
            try {
                iRemoteService?.registerListener(menuUpdateCallback)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        // Called when the connection with the service disconnects unexpectedly.
        override fun onServiceDisconnected(className: ComponentName) {
            Log.e(TAG, "Service has unexpectedly disconnected")
            Toast.makeText(
                this@MainActivity,
                "Service has unexpectedly disconnected",
                Toast.LENGTH_SHORT
            ).show()
            iRemoteService = null
        }
    }

    private val menuUpdateCallback = object : IMenuCallback.Stub() {
        override fun onMenuItemSelected(menuItems: List<com.tobibur.aidl_server.domain.model.MenuItem?>?) {
            Log.d(TAG, "onMenuItemSelected: called from service ${menuItems?.size}")
            menuItems?.forEach {
                mainMenuViewModel.insertMenuItem(MenuItem(it!!.id, it.title, it.isChecked))
                Log.d(TAG, "onMenuItemSelected: $it")
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