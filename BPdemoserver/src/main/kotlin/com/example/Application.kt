package com.example

import com.example.plugins.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import sun.misc.Signal
//import sun.nio.ch.NativeThread.signal
import java.net.Inet4Address
import java.net.NetworkInterface


fun getMyIpv4Address(interfaceName : String) : String?
{
    var ipv4 : String? = null
    val netIntfrc = NetworkInterface.getByName(interfaceName)
    val inetAddr = netIntfrc.inetAddresses
    var currAddr = inetAddr.nextElement()
    while( inetAddr.hasMoreElements())
    {
        currAddr = inetAddr.nextElement()
        if( currAddr is Inet4Address && !currAddr.isLoopbackAddress)
        {
            ipv4 = currAddr.toString()
            break
        }
    }
    if( ipv4 != null)
    {
        if( ipv4.startsWith("/")) ipv4 = ipv4.substring(1)
    }
    return ipv4
}

fun main() {


    runBlocking {
        val selectManager = SelectorManager(Dispatchers.IO)
        val ipv4Addr = getMyIpv4Address("wlp0s20f3")
        println(ipv4Addr)
        if( ipv4Addr == null)
        {
            println("no ip returned")
            return@runBlocking
        }
        val socket = aSocket(selectManager).udp().bind(InetSocketAddress(ipv4Addr,5001))

      /*  Signal.handle(Signal( "INT")){
            socket.close()
            println("Ending...")
        }*/
        while( true) {
            println(socket.incoming.receive().packet.readText())
        }
    }
}


