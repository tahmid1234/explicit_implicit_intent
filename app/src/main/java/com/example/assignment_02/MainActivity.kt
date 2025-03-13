package com.example.assignment_02

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment_02.ui.theme.Assignment_02Theme
import com.example.assignment_02.ui.theme.netflixButtonColors
import com.example.assignment_02.ImageCaptureActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment_02Theme(true) {
                MainScreen { action ->
                    when (action) {
                        "explicit" -> {
                            val intent = Intent(this, SecondActivity::class.java)
                            startActivity(intent)
                        }
                        "imageActivity" -> {
                            val intent = Intent(this, ImageCaptureActivity::class.java)
                            startActivity(intent)
                        }
                        "implicit" -> {
//                            val intent = Intent("com.example.assignment_02.SECOND_ACTIVITY")
//                            startActivity(intent)
                              val imIntent = Intent()
                              imIntent.setAction("com.example.assignment_02.Start_Second_Activity")

                              if (imIntent.resolveActivity(packageManager)!=null){
                                startActivity(imIntent)
                              }
                              else{
                                  Toast.makeText(this,"No app can handle this action",Toast.LENGTH_SHORT).show()
                              }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(onButtonClick: (String) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text= "Name: ${context.getString(R.string.full_name)}", color = Color.White)
        Text(text = "Student ID: ${context.getString(R.string.studnet_id)}",color =Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onButtonClick("explicit") },
            colors = netflixButtonColors(),
            modifier = Modifier.padding(8.dp)
            ) {
            Text("Start Activity Explicitly")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onButtonClick("implicit") },
                colors = netflixButtonColors(),
                modifier = Modifier.padding(8.dp)
            ) {
            Text("Start Activity Implicitly")
        }

        Button(onClick = { onButtonClick("imageActivity") },
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