package com.example.todolistapp

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Delete
import com.example.todolistapp.ui.theme.Pink80
import com.example.todolistapp.ui.theme.Purple40
import com.example.todolistapp.ui.theme.Purple80
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage(viewModel: TodoViewModel) {

    val todoList by viewModel.todoList.observeAsState()

    var inputText by remember {
        mutableStateOf("")
    }//End

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-do List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }

    )//End Scaffold
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it }) //Takes the user input
                Button(onClick = {
                    viewModel.addTodo(inputText)
                    inputText = ""
                })
                {
                    Text(text = "Add")
                }
            }

            todoList?.let {
                LazyColumn {
                    itemsIndexed(it) { index: Int, item: Todo ->
                        TodoItem(item = item, onDelete = {
                            viewModel.deleteTodo(item.id)
                        })
                    }
                }
            }
                ?: Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "CURRENTLY EMPTY",
                    fontSize = 16.sp
                )
        }

    }
}//End todolistPage


    //For the bottom Navigation bar
    @Composable
    fun BottomNavigationBar(){
        NavigationBar(
            containerColor = Purple40,
            contentColor = Color.White
        ){
            //home
            NavigationBarItem(
                icon = { Icon(painterResource(id = R.drawable.baseline_home_24), contentDescription = "Home") },
                label = { Text("Home") },
                selected = true,
                onClick = {}
            )//End og home

            //Calendar
            NavigationBarItem(
                icon = { Icon(painterResource(id = R.drawable.baseline_calendar_month_24), contentDescription = "Calendar") },
                label = { Text("Calendar") },
                selected = true,
                onClick = {}
            )//End of Calendar


            NavigationBarItem(
                icon = { Icon(painterResource(id = R.drawable.baseline_info_24), contentDescription = "info") },
                label = { Text("Info") },
                selected = true,
                onClick = { }
            )//End of home

        }
    }


//Composable for the info screen
//Info screen when user press the info on the navigation bar
/*@Composable
fun InfoScreen(navController: NavController){
    Scaffold (
        topBar = { ScreenTopBar("Info", navController) },
    ){ innerPadding ->
        Text(text = "This is the Info Screen",
            modifier = Modifier
                .background(Pink80) //Changes background color
                .fillMaxSize()
                .padding(innerPadding))
    }
}*/


/*@Composable
fun ScaffoldApp(){
    val navController = rememberNavController() //This will handle screen for navigation
    NavHost(
        navController = navController,
        startDestination = "TodoListPage" //the main screen{
    ){
        //add more composable here for the navigation
        composable(route = "home"){ TodoListPage(viewModel = TodoViewModel(),navController)}
        composable(route = "info"){ InfoScreen(navController) }
    }
}*/




    @Composable
    fun TodoItem(item : Todo, onDelete:()->Unit){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically


        )//End of row
        {
            Column(
                modifier = Modifier.weight(1f)
            )
            {
                Text(
                    text = SimpleDateFormat("HH:mm:aa, dd/mm", Locale.ENGLISH).format(item.made),
                    fontSize = 10.sp,
                    color = Purple80
                )

                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    color = Pink80
                )
            }//End of Column

            IconButton(onClick = onDelete)
            {
                Icon(painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = Color.White)
            }//end of icon btn
        }

    }//End of todoitem










