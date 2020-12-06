package net.corda.core.node

import net.corda.core.identity.Party

interface ServiceHub {

    /** The [NodeInfo] object corresponding to our own entry in the network map. */
    val myInfo: NodeInfo
}


class DefaultServiceHub : ServiceHub {

    override val myInfo: NodeInfo
        get() = NodeInfo(Party("Alice"))

}