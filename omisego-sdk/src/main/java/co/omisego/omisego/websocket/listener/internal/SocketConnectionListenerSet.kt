package co.omisego.omisego.websocket.listener.internal

import co.omisego.omisego.websocket.listener.SocketConnectionListener

/**
 * OmiseGO
 *
 * Created by Yannick Badoual on 7/13/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

interface SocketConnectionListenerSet {
    fun addConnectionListener(connectionListener: SocketConnectionListener)
    fun removeConnectionListener(connectionListener: SocketConnectionListener)
}