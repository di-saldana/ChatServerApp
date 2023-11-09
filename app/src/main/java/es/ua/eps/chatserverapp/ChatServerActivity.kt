package es.ua.eps.chatserverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread

class ChatServerActivity : AppCompatActivity() {
    private lateinit var chatServer: ChatServer
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_server)

//        chatServer = ChatServer()
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        // Initialize and configure the RecyclerView for displaying messages
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter()
        recyclerView.adapter = messageAdapter

        // Move the connection code to a background thread
//        thread {
////            chatServer.connect()
//
//            while (true) {
//                val receivedMessage = chatServer.receiveMessage()
//                if (receivedMessage != null) {
//                    runOnUiThread {
//                        // Add receivedMessage to your adapter and update the RecyclerView
//                        messageAdapter.addMessage(receivedMessage)
//                        messageAdapter.notifyDataSetChanged()
//                    }
//                }
//            }
//        }

        sendButton.setOnClickListener {
//            sendMessage()
        }
    }

//    private fun sendMessage() {
//        val message = messageEditText.text.toString()
//        if (message.isNotEmpty()) {
//            chatServer.sendMessage(message)
//
//            // Add the message to the local RecyclerView
//            messageAdapter.addMessage(message)
//            messageAdapter.notifyDataSetChanged()
//
//            // Clear the EditText
//            messageEditText.text.clear()
//        }
//    }
}
