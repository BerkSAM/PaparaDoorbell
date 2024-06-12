package com.sammy.paparadoorbell.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sammy.paparadoorbell.feature.recipe.RecipeViewModel
import com.sammy.paparadoorbell.ui.theme.CustomColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTopAppBar() {
    var showMenu by remember { mutableStateOf(false) }
    val viewModel: RecipeViewModel = hiltViewModel()

    val notifications by viewModel.notifications.collectAsState(initial = listOf())
    val hasNotifications = notifications.isNotEmpty()

    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            BadgedBox(
                badge = {
                    if (hasNotifications) {
                        Badge(
                            modifier = Modifier
                                .size(8.dp)
                                .offset(y = (8).dp, x = (-15).dp),
                            backgroundColor = CustomColors.PRIMARY_COLOR
                        ) { /* Empty content - just the dot */ }
                    }
                }
            ) {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White
                    )
                }
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier.width(300.dp)
            ) {
                notifications.forEachIndexed { index, notification ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = notification.title ?: "",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },

                        onClick = {
                            showMenu = false
                        },
                        modifier = Modifier.background(
                            if (index % 2 == 0) Color.White else CustomColors.LIGHTPINK
                        )
                    )
                }
            }
        }
    )
}