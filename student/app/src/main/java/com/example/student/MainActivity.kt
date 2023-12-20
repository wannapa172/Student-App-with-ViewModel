package com.example.student

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.student.ui.theme.StudentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentTheme {
                // A surface container using the 'background' color from the theme
                StudentApp()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp() {
    // Remember the ViewModel to maintain its state across recompositions
    var viewModel = remember { StudentViewModel() }

    // State to control the visibility of the input dialog
    var isShowDialog by remember { mutableStateOf(false) }

    // Show the input dialog when isShowDialog is true
    if (isShowDialog) {
        InputDialog(
            onCancel = {
                // Hide the dialog if canceled
                isShowDialog = false
            },
            onAddButton = { name, id ->
                // Hide the dialog and add the student to the ViewModel
                isShowDialog = false
                viewModel.addStudent(name, id)
            }
        )
    }

    // Scaffold displaying the top app bar, FAB, and the student list
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student App") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.slate,
                    titleContentColor = Color.silver
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isShowDialog = true },
                containerColor = Color.slate,
                contentColor = Color.silver
            ) {
                Icon(Icons.Filled.Add, "Add new student")
            }
        }
    ) {
        // LazyColumn displaying the list of students from the ViewModel
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(viewModel.data) { item ->
                // Display both student name and ID
                Column {
                    Text("Name: ${item.name}")
                    Text("ID: ${item.studentId}")
                    Divider(
                        color = Color.Gray,
                        thickness = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(
    onCancel: () -> Unit,
    onAddButton: (String, String) -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        var inputName by remember {
            mutableStateOf("")
        }
        var inputId by remember {
            mutableStateOf("")
        }
        Card(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                OutlinedTextField(
                    value = inputId,
                    onValueChange = { inputId = it },
                    label = { Text("Student id") }
                )
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Student name") }
                )
                TextButton(
                    onClick = {
                        onAddButton(inputName, inputId)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    StudentTheme {
        StudentApp()
    }
}