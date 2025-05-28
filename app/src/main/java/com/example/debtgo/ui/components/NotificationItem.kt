package com.example.debtgo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.debtgo.data.model.Notification

@Composable
fun NotificationItem(
    notification: Notification,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = notification.type.getBackground(),
                shape = RoundedCornerShape(8.dp)
            )
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
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = notification.time,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF718096)
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (!notification.read) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = notification.type.getColor(),
                        shape = CircleShape
                    )
            )
        }
    }
}