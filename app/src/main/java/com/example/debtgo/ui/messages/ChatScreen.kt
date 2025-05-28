package com.example.debtgo.ui.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.debtgo.data.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, contactId: String?) {
    // Questions for different contacts
    val contactQuestions = mapOf(
        "1" to "How can I track my debt repayment progress?",
        "2" to "What's the best way to set up payment reminders?",
        "3" to "Can you explain the debt snowball feature?",
        "4" to "How do I connect my bank account?",
        "5" to "What reports can I generate about my debts?",
        "6" to "How does the interest calculation work?",
        "7" to "Can I share my progress with my financial advisor?",
        "8" to "What's the difference between avalanche and snowball methods?"
    )

    fun handleSystemReply(userMessage: String): String {
        return when {
            contactId == "1" && userMessage.matches("(?i)yes|yes please|sure|show me".toRegex()) -> {
                navController.navigate("dashboardRoute")
                "Taking you to your dashboard..."
            }
            contactId == "1" && userMessage.matches("(?i)no|no thanks|not now".toRegex()) -> {
                "Okay, let me know if you need help with anything else!"
            }
            contactId == "1" -> {
                "I didn't understand. Would you like me to show you the Dashboard?"
            }
            // ... otros casos para otros contactIds
            else -> "Thanks for your question about DebtGo!"
        }
    }

    var messages by remember(contactId) {
        mutableStateOf(
            listOf(
                ChatMessage(
                    id = UUID.randomUUID().toString(),
                    senderId = "system",
                    content = contactQuestions[contactId] ?: "Welcome to DebtGo chat!",
                    timestamp = "Just now",
                    isSystem = true
                )
            )
        )
    }
    var newMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                // Message list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    reverseLayout = false
                ) {
                    items(messages) { message ->
                        MessageBubble(message = message)
                    }
                }

                // Message input
                MessageInput(
                    message = newMessage,
                    onMessageChange = { newMessage = it },
                    onSend = {
                        if (newMessage.isNotBlank()) {
                            // Add user message
                            val userMessage = ChatMessage(
                                id = UUID.randomUUID().toString(),
                                senderId = "me",
                                content = newMessage,
                                timestamp = SimpleDateFormat("h:mm a", Locale.getDefault())
                                    .format(Date()),
                                isMe = true
                            )

                            // Add system reply (simulated)
                            val systemReply = ChatMessage(
                                id = UUID.randomUUID().toString(),
                                senderId = "system",
                                content = getSystemReply(contactId, newMessage),
                                timestamp = SimpleDateFormat("h:mm a", Locale.getDefault())
                                    .format(Date()),
                                isSystem = true
                            )

                            messages = messages + userMessage + systemReply
                            newMessage = ""
                        }
                    }
                )
            }
        }
    )
}

private fun getSystemReply(contactId: String?, userMessage: String): String {
    return when (contactId) {
        "1" -> handleDebtTrackingResponse(userMessage)
        "2" -> "Go to Settings > Notifications to set reminders. Need help with this?"
        "3" -> "The snowball method pays smallest debts first. Want a detailed explanation?"
        else -> "Thanks for your question about DebtGo! Our support team will follow up."
    }
}

private fun handleDebtTrackingResponse(userMessage: String): String {
    return when (userMessage.toLowerCase().trim()) {
        "yes", "yes please", "sure", "show me" -> "Here's your repayment progress dashboard: [Dashboard details]"
        "no", "no thanks", "not now" -> "Okay, let me know if you need help with anything else!"
        else -> "I didn't understand. Would you like me to show you the Dashboard?"
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val bubbleColor = when {
        message.isMe -> MaterialTheme.colorScheme.primary
        message.isSystem -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = when {
        message.isMe -> MaterialTheme.colorScheme.onPrimary
        message.isSystem -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = if (message.isMe) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Text(text = message.content, color = textColor)
        }
        Text(
            text = message.timestamp,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(
                top = 4.dp,
                start = 8.dp,
                end = 8.dp
            )
        )
    }
}