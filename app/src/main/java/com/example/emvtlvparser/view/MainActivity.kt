package com.example.emvtlvparser.view

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.emvtlvparser.R
import com.example.emvtlvparser.databinding.ActivityMainBinding
import com.example.emvtlvparser.utils.TlvParser
import com.example.emvtlvparser.viewmodel.TlvViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TlvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Initialize ViewModel
        viewModel = ViewModelProvider(this).get(TlvViewModel::class.java)

        //Set up UI elements
        val tlvInput: EditText = binding.tlvInput
        val parseButton: Button = binding.parseButton
        val parsedOutput: TextView = binding.parsedOutput

        //Set up button click listener
        parseButton.setOnClickListener {
            val  tlvData = tlvInput.text.toString()
            viewModel.parsedTlvData(tlvData)
        }

        //Observe ViewModel data
        viewModel.parsedData.observe(this, Observer { parsedData ->
            parsedOutput.text = formatParsedData(parsedData)
        })

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show()
        }
    }
    private fun formatParsedData(parsedData: List<TlvParser.TlvData>): String {
        val output = StringBuilder()
        parsedData.forEach {
            output.append("Tag: ${it.tag}, Length: ${it.length}, Value: ${it.value}\n")
        }
        return output.toString()
    }

override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}