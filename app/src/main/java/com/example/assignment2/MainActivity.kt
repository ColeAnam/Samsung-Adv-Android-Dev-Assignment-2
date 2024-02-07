package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.assignment2.ui.theme.Assignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    var cName by remember { mutableStateOf("") }
    var cNo by remember { mutableStateOf("") }

    val contactList = listOf("text","text","text","text","text","text","text","text","text","text","text","text","text","text","text","text","text","text","text","text","text")

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 100.dp)) {
            TextField(
                value = cName,
                onValueChange = { cName = it },
                label = {
                    Text("Contact Name")
                },
                modifier = Modifier.padding(bottom = 20.dp)
            )
            TextField(
                value = cNo,
                onValueChange = { cNo = it },
                label = {
                    Text("Contact No")
                },
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Button(onClick = {

            },
                modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp)
                ) {
                Text("Load")
            }
            Button(onClick = {

            },
                modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp)
            ) {
                Text("Save")
            }
        }
        Text("Contacts", modifier = Modifier.offset(x = -100.dp))
        ScrollableTextList(contactList)
    }
}

@Composable
fun ScrollableTextList(textList: List<String>) {
    LazyColumn(modifier = Modifier.padding(top = 20.dp).height(200.dp)) {
        items(textList) {text ->
            Text(text)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment2Theme {
        MainScreen()
    }
}