package com.example.assignment_02

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.assignment_02.ui.theme.Assignment_02Theme
import com.example.assignment_02.ui.theme.netflixButtonColors

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Start the second activity.
                startSecondActivityExplicitly()
            } else {
                // Explain to the user that the feature is unavailable because the
                // permission has not been granted.
                Toast.makeText(this, "Permission to view challenges is required.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment_02Theme(true) {
                MainScreen { action ->
                    when (action) {
                        "explicit" -> {
                            // Check if permission is already granted
                            if (ContextCompat.checkSelfPermission(
                                    this,
                                    "com.example.assignment_02.MSE412"
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                startSecondActivityExplicitly()
                            } else {
                                // Request the permission
                                requestPermissionLauncher.launch("com.example.assignment_02.MSE412")
                            }
                        }
                        "imageActivity" -> {
                            val intent = Intent(this, ImageCaptureActivity::class.java)
                            startActivity(intent)
                        }
                        "implicit" -> {
                            val imIntent = Intent()
                            imIntent.setAction("com.example.assignment_02.Start_Second_Activity")

                            if (imIntent.resolveActivity(packageManager) != null) {
                                startActivity(imIntent)
                            } else {
                                Toast.makeText(this, "No app can handle this action", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startSecondActivityExplicitly() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun MainScreen(onButtonClick: (String) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Name: ${context.getString(R.string.full_name)}", color = Color.White)
        Text(text = "Student ID: ${context.getString(R.string.studnet_id)}", color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onButtonClick("explicit") },
            colors = netflixButtonColors(),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Start Activity Explicitly")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onButtonClick("implicit") },
            colors = netflixButtonColors(),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Start Activity Implicitly")
        }

        Button(
            onClick = { onButtonClick("imageActivity") },
            colors = netflixButtonColors(),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("View Image Activity")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Assignment_02Theme {
        MainScreen {}
    }
}