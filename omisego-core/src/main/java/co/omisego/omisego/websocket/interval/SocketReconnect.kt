package co.omisego.omisego.websocket.interval

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 3/5/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import co.omisego.omisego.model.socket.SocketSend
import co.omisego.omisego.websocket.channel.SocketChannelContract
import java.net.InetAddress
import java.util.Date
import java.util.Timer
import kotlin.concurrent.schedule

class SocketReconnect : BaseSocketInterval(), SocketChannelContract.SocketReconnect {
    val reconnectChannels: MutableSet<SocketSend> = mutableSetOf()

    companion object {
        const val NETWORK_AVAILABILITY_SITE = "google.com"
    }

    @Synchronized
    inline fun startInterval(crossinline join: (SocketSend) -> Unit) {
        synchronized(this) {
            timer?.cancel()
            timer = Timer()
            timer?.schedule(Date(), period) {
                if (isInternetAvailable()) {
                    for (channel in reconnectChannels) {
                        join(channel)
                    }
                }
            }
        }
    }

    override fun stopReconnectIfDone(channelSet: Set<String>) {
        val inReconnectSet: (String) -> Boolean = { topic -> reconnectChannels.any { it.topic == topic } }
        val done = channelSet.all(inReconnectSet)
        if (timer != null && done) {
            stopInterval()
        }
    }

    override fun add(socketSend: SocketSend) {
        reconnectChannels.add(socketSend)
    }

    override fun remove(topic: String) {
        reconnectChannels.findLast { it.topic == topic }?.let {
            reconnectChannels.remove(it)
        }
    }

    /**
     * Check internet availability by getting the ip address of google.com
     */
    fun isInternetAvailable(): Boolean {
        return try {
            InetAddress.getByName(NETWORK_AVAILABILITY_SITE).hostAddress.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}
