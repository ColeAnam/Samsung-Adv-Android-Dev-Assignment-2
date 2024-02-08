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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.assignment2.datastore.MyContacts
import com.example.assignment2.datastore.StoreContacts
import com.example.assignment2.ui.theme.Assignment2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.future.future
import kotlinx.coroutines.launch

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

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreContacts(context)

    var cName by remember { mutableStateOf("") }
    var cNo by remember { mutableStateOf("") }
    var contactList by remember { mutableStateOf(listOf("")) }

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
                scope.future {
                    contactList = listOf("")
                    dataStore.getAllContacts()
                        .flowOn(Dispatchers.IO)
                        .collect {dataList ->
                            contactList = listOf("")
                            dataList.forEach { data ->
                                val dataName = data.name
                                val dataNum = data.num
                                contactList = contactList + listOf("$dataName $dataNum")
                            }
                        }
                }
            },
                modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp)
                ) {
                Text("Load")
            }
            Button(onClick = {
                scope.launch {
                    contactList = listOf("")
                    if (cName != "" && cNo != "") {
                        dataStore.saveInfo(MyContacts(cName, cNo))
                    }
                }
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
        Text("Student Name: Cole Anam\nStudent ID: 301009188", modifier = Modifier.padding(top = 60.dp))
    }
}

@Composable
fun ScrollableTextList(textList: List<String>) {
    LazyColumn(modifier = Modifier
        .padding(top = 20.dp)
        .height(200.dp)) {
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