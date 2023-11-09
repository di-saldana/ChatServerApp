package es.ua.eps.chatserverapp

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : Activity() {
    private lateinit var server: ChatServer
    private lateinit var infoip: TextView
//    lateinit var msg: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        infoip = findViewById(R.id.infoip)
//        msg = findViewById(R.id.msg)
        server = ChatServer(this)
        infoip.text = "${server.getIpAddress()}:${server.getPort()}"

        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        // Initialize and configure the RecyclerView for displaying messages
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter()
        recyclerView.adapter = messageAdapter

        sendButton.setOnClickListener {
//            sendMessage()
        }

        // Check if the server is running
        if (server.isRunning()) {
            // If the server is running, start the ChatServerActivity
//            val intent = Intent(this, ChatServerActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.IO) {
            server.onDestroy()
        }
    }

//    private fun sendMessage() {
//        val message = messageEditText.text.toString()
//        if (message.isNotEmpty()) {
//            server.sendMessage(message)
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
