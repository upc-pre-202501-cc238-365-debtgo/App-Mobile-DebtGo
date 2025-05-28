package com.example.debtgo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.debtgo.data.model.Notification

@Composable
fun NotificationBadge(
    count: Int,
    notifications: List<Notification>,
    modifier: Modifier = Modifier,
    onNotificationClick: (Notification) -> Unit,
    onViewAll: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notificaciones"
            )
            if (count > 0) {
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp),
                    containerColor = Color(0xFFE53E3E)
                ) {
                    Text(count.toString())
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .padding(8.dp)
        ) {
            Column {
                Text(
                    "Notifications",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                if (notifications.isEmpty()) {
                    Text(
                        "No new notifications",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    notifications.take(3).forEach { notification ->
                        NotificationDropdownItem(
                            notification = notification,
                            onClick = {
                                onNotificationClick(notification)
                                expanded = false
                            }
                        )
                        Divider()
                    }

                    TextButton(
                        onClick = {
                            onViewAll()
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("View All Notifications")
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationDropdownItem(
    notification: Notification,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = notification.icon,
            contentDescription = null,
            tint = notification.type.getColor(),
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = notification.message,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = notification.time,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF718096)
                )
            )
        }
    }
}