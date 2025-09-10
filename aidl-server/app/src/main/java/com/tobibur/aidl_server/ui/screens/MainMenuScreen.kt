package com.tobibur.aidl_server.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tobibur.aidl_server.domain.model.MenuItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    menuList: StateFlow<List<MenuItem>>,
    onCheckedChange: (MenuItem) -> Unit = {},
    onDelete: (MenuItem) -> Unit = {}
) {
    val menuItems by menuList.collectAsState()
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
    ) {
        items(menuItems) { menu ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    menu.isChecked,
                    onCheckedChange = { onCheckedChange(menu.copy(isChecked = it)) })
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = menu.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onDelete(menu) },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}
