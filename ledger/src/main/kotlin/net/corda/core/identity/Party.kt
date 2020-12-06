package net.corda.core.identity

import java.security.PublicKey
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.KeyPair


abstract class AbstractParty(val owningKey: PublicKey)

class Party(val name: CordaX500Name, owningKey: PublicKey) : AbstractParty(owningKey) {
    constructor(organisation: String) : this(CordaX500Name(organisation, "London", "GB"),
            net.corda.core.identity.KeyPairGenerator.doit().public)

}


object KeyPairGenerator {

    fun doit(): KeyPair {

        val keyGen = KeyPairGenerator.getInstance("DSA", "SUN")
        val random = SecureRandom.getInstance("SHA1PRNG", "SUN")

        keyGen.initialize(1024, random);

        val pair = keyGen.generateKeyPair()
        val priv = pair.private
        val pub = pair.public
        return pair

    }

}