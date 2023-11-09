package es.ua.eps.chatserverapp

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : Activity() {
    private lateinit var server: ChatServer
    private lateinit var infoip: TextView
    lateinit var msg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        infoip = findViewById(R.id.infoip)
        msg = findViewById(R.id.msg)
        server = ChatServer(this)
        infoip.text = "${server.getIpAddress()}:${server.getPort()}"

        // Check if the server is running
        if (server.isRunning()) {
            // If the server is running, start the ChatServerActivity
            val intent = Intent(this, ChatServerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.IO) {
            server.onDestroy()
        }
    }
}
