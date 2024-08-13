package com.example.emvtlvparser

import com.example.emvtlvparser.utils.TlvParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class TlvParserTest {
    private val tlvParser = TlvParser()

    @Test
    fun `test single byte tag parsing`() {
        val tlvData = "5F2003313233"
        val parsed = tlvParser.parseTlv(tlvData)
        assertEquals(1, parsed.size)
        assertEquals("5F20", parsed[0].tag)
        assertEquals(3, parsed[0].length)
        assertEquals("313233", parsed[0].value)
    }

    @Test
    fun `test two byte tag parsing`() {
        val tlvData = "9F0206000000001000"
        val parsed = tlvParser.parseTlv(tlvData)
        assertEquals(1, parsed.size)
        assertEquals("9F02", parsed[0].tag)
        assertEquals(6, parsed[0].length)
        assertEquals("000000001000", parsed[0].value)
    }

    @Test(expected = NumberFormatException::class)
    fun `test invalid TLV data`() {
        val tlvData = "5F201Z123"
        tlvParser.parseTlv(tlvData)
    }
}