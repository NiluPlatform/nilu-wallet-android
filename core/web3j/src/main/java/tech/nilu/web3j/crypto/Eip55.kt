package tech.nilu.web3j.crypto

import org.web3j.crypto.Hash

object Eip55 {

    fun convertToEip55Address(addr: String): String {
        val address = addr.substring(2).toLowerCase()
        val ret = StringBuilder("0x")
        val hash = Hash.sha3String(address).substring(2)
        for (i in address.indices) {
            val a: String = address[i].toString() + ""
            val h = (hash[i].toString() + "").toInt(16)
            if (h > 7) {
                ret.append(a.toUpperCase())
            } else {
                ret.append(a)
            }
        }
        return ret.toString()
    }

    fun isValidAddress(eip55: String): Boolean {
        var pattern = "(0x)?([0-9a-f]{40}|[0-9A-F]{40})"
        if (eip55.matches(Regex(pattern))) return true

        pattern = "(0x)?([0-9a-fA-F]{40})"
        if (!eip55.matches(Regex(pattern))) return false

        return convertToEip55Address(eip55) == eip55
    }
}
