package com.example.assignment_02

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignment_02.ui.theme.Assignment_02Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment_02Theme {
                SecondScreen {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun SecondScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val challanges = remember { context.resources.getStringArray(R.array.challanges) }
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        BasicText("Mobile Software Engineering Challenges",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color(0xF5EE0067)
            ),
            modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(8.dp))

        challanges.forEachIndexed{index,challange ->
            Text(text= " ${index + 1}. $challange",
            modifier = Modifier.align(Alignment.Start)
            )
        }
        Spacer(modifier = Modifier.height(80.dp))

        Button(onClick = onBackClick) {
            Text("Main Activity")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSecondScreen() {
    Assignment_02Theme {
        SecondScreen {}
    }
}