package com.example.emvtlvparser.utils


class TlvParser {
    data class TlvData(val tag: String, val length: Int, val value: String)

    fun parseTlv(tlvData: String) : List<TlvData> {
        val parsedData = mutableListOf<TlvData>()
        var index = 0

        while (index < tlvData.length) {
            //Extract Tag
            val tag = extractTag(tlvData, index)
            index += tag.length

            //Extract Length
            val lengthHex = tlvData.substring(index, index + 2)
            val length = lengthHex.toInt(16)
            index += 2

            //Extract Value
            val value = tlvData.substring(index, index + length * 2)
            index += length * 2

            parsedData.add(TlvData(tag, length, value))
        }
        return parsedData
    }

    private fun extractTag(data: String, startIndex: Int): String {
        //EMV Tags can be variable in length
        val firstByte = data.substring(startIndex, startIndex +2).toInt(16)
        return if ((firstByte and 0x1F) == 0x1F) {
            //Two-byte tag
            data.substring(startIndex, startIndex + 4)
        }else{
            //One-byte tag
            data.substring(startIndex, startIndex + 2)
        }
    }

}