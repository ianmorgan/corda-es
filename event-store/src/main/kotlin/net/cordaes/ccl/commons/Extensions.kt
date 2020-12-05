package net.corda.ccl.commons

import java.util.*

fun String.Companion.random(length : Int = 6) : String {
    val random = Random()
    val buffer = StringBuilder(length)
    for (i in 0 until length) {

        if (random.nextFloat() < 0.3) {
            // 0..9
            val randomLimitedInt = 48 + (random.nextFloat() * (57 - 48 + 1)).toInt()
            buffer.append(randomLimitedInt.toChar())
        } else {
            // a..z
            val randomLimitedInt = 97 + (random.nextFloat() * (122 - 97 + 1)).toInt()
            buffer.append(randomLimitedInt.toChar())
        }

    }
    return buffer.toString()
}




fun String.simpleEncrypt(): String {
    var code: Int
    var result = ""
    for (i in 0 until this.length) {
        code = Math.round(Math.random().toFloat() * 8 + 1)
        result += code.toString() + Integer.toHexString(this[i].toInt() xor code) + "-"
    }
    return result.substring(0, result.lastIndexOf("-"))
}


fun String.simpleDecrypt(): String {
    var str = this
    str = str.replace("-", "")
    var result = ""
    var i = 0
    while (i < str.length) {
        val hex = str.substring(i + 1, i + 3)
        result += (Integer.parseInt(hex, 16) xor Integer.parseInt(str[i].toString())).toChar()
        i += 3
    }
    return result
}
