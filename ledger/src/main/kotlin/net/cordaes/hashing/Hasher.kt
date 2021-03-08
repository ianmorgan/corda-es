package net.cordaes.hashing

import net.cordaes.serializers.Serializer
import org.bouncycastle.util.encoders.Base64
import java.security.NoSuchAlgorithmException
import java.security.MessageDigest
import sun.misc.BASE64Encoder





class Hasher {

    fun hash(value : Any) : String {
        val bytes = Serializer().toJson(value).toByteArray()
        val hash = toSHA1(bytes)
        return hash
    }

    fun toSHA1(convertme: ByteArray): String {
        var md: MessageDigest? = null
        try {
            md = MessageDigest.getInstance("SHA-1")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        val digest = md!!.digest(convertme)
        val hash = BASE64Encoder().encode(digest)
        return HexString().bytesToHex(digest)
    }


}

fun main (args : Array<String> ) {
    println(Hasher().hash("hdjdf"))
}