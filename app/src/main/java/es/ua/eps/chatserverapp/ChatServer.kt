package es.ua.eps.chatserverapp

import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Enumeration

class ChatServer(private val activity: MainActivity) {
    private val socketServerPORT = 8080
    private var serverSocket: ServerSocket? = null
    private var message = ""
    private var count = 0

    init {
        GlobalScope.launch(Dispatchers.IO) {
            startServer()
        }
    }

    fun getPort(): Int {
        return socketServerPORT
    }

    fun onDestroy() {
        serverSocket?.close()
    }

    private suspend fun startServer() {
        try {
            serverSocket = ServerSocket(socketServerPORT)

            while (true) {
                val socket = serverSocket?.accept()
                count++
                message += "#$count from ${socket?.inetAddress}:${socket?.port}\n"

                activity.runOnUiThread {
                    activity.msg.text = message
                }

                GlobalScope.launch(Dispatchers.IO) {
                    val socketServerReplyThread = SocketServerReplyThread(socket, count)
                    socketServerReplyThread.run()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private inner class SocketServerReplyThread(private val hostThreadSocket: Socket?, private val cnt: Int) :
        Runnable {
        override fun run() {
            val outputStream: OutputStream
            val msgReply = "Hello from Server, you are #$cnt"

            try {
                outputStream = hostThreadSocket?.getOutputStream() ?: return
                val printStream = PrintStream(outputStream)
                printStream.print(msgReply)
                printStream.close()

                message += "replayed: $msgReply\n"

                activity.runOnUiThread {
                    activity.msg.text = message
                }
            } catch (e: IOException) {
                e.printStackTrace()
                message += "Something wrong! ${e.toString()}\n"
            }

            activity.runOnUiThread {
                activity.msg.text = message
            }
        }
    }

    fun getIpAddress(): String {
        var ip = ""
        try {
            val enumNetworkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (enumNetworkInterfaces.hasMoreElements()) {
                val networkInterface: NetworkInterface = enumNetworkInterfaces.nextElement()
                val enumInetAddress: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (enumInetAddress.hasMoreElements()) {
                    val inetAddress: InetAddress = enumInetAddress.nextElement()
                    if (inetAddress.isSiteLocalAddress) {
                        ip += "Server running at : " + inetAddress.hostAddress
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
            ip += "Something Wrong! ${e.toString()}\n"
        }
        return ip
    }

    fun isRunning(): Boolean {
        return serverSocket != null && !serverSocket!!.isClosed
    }
}