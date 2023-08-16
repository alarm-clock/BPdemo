package com.example.bpdemo

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import locus.api.objects.extra.Location
import kotlin.system.*


class KtorClient {

    fun createPacket( location: Location? , point : Boolean) : ByteReadPacket
    {
        var data = if(location != null) "${location.longitude} ${location.latitude}" else " err "
        data += if( point ) " point " else " user location "
        val packetBuilder = BytePacketBuilder()
        packetBuilder.writeText(data.toCharArray())
        return packetBuilder.build()
    }

    fun sendLoc(location: Location , host: String , port : Int , point : Boolean) : Boolean
    {
        println("here")
        runBlocking {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val socket = aSocket(selectorManager).udp().connect(InetSocketAddress(host,port))

            val datagram = Datagram( createPacket(location , point) , InetSocketAddress(host,port))
            socket.send(datagram)
            socket.close()

        }
        return true
    }

    fun sendErr(host: String , port: Int)
    {
        runBlocking {
            val selectorManager = SelectorManager(Dispatchers.IO)
            val socket = aSocket(selectorManager).udp().connect(InetSocketAddress(host,port))

            val datagram = Datagram( createPacket(null , false) , InetSocketAddress(host,port))
            socket.send(datagram)
            socket.close()
        }
    }
}