package com.example.emvtlvparser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emvtlvparser.utils.TlvParser

class TlvViewModel : ViewModel() {
    private val tlvParser = TlvParser()
    private val _parsedData = MutableLiveData<List<TlvParser.TlvData>>()
    val parsedData: LiveData<List<TlvParser.TlvData>> get() = _parsedData
    
    fun parsedTlvData(tlvData: String) {
        try {
            _parsedData.value = tlvParser.parseTlv(tlvData)
        }catch (e: Exception) {
            //Handing parsing errors if any
            _parsedData.value = listOf(TlvParser.TlvData("Error", 0, "Invalid TLV Data"))
        }
    }
}