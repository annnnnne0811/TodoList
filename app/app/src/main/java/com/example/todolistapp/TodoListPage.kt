package com.example.todolistapp

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.todolistapp.db.TodoDao_Impl
import com.example.todolistapp.ui.appbars.TabItem
import com.example.todolistapp.ui.theme.Pink80
import com.example.todolistapp.ui.theme.Purple40
import com.example.todolistapp.ui.theme.Purple80
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage(viewModel: TodoViewModel, navController: NavController) {

    val todoList by viewModel.todoList.observeAsState()

    var inputText by remember {
        mutableStateOf("")
    }//End

    Scaffold() { paddingValues ->
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

//for when the user enters a list
@Composable
fun TodoItem(item : Todo, onDelete:()->Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF6495ED))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically


    )//End of row
    {
        Column(
            modifier = Modifier.weight(1f)
        )
        {
            //adds date and time to the note added
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

        //delete btn
        IconButton(onClick = onDelete)
        {
            Icon(painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete",
                tint = Color.White)
        }//end of icon btn
    }

}//End of todoitem


//for the top header and the bottom navigator
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldApp(viewModel: TodoViewModel){

    val navController = rememberNavController()

    Scaffold(
        containerColor = Color(0xFFE3F2FD),
        topBar = {
            TopAppBar(
                title = { Text("To-do List App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ){
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home", //main page
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = "home") { HomeScreen(viewModel = viewModel, navController) }
            composable(route = "calendar") { CalendarScreen() }
            composable(route = "info") { InfoScreen() }
        }
    }
}

//Composables for the screens

//home aka main
@Composable
fun HomeScreen(viewModel: TodoViewModel, navController: NavController) {
    TodoListPage(viewModel, navController)
}


//Calendar screen
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxSize(),
        text = "put calendar api"
    )
}


//info screen
@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxSize(),
        text = "Free topic assignment: A to do list app with a google calendar api due 16th March"
    )
}




//for the bottom navigation bar
@Composable
fun BottomNavigationBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    val tabs = listOf(
        TabItem("Home", Icons.Filled.Home, "home"),
        TabItem("Calendar", Icons.Filled.DateRange, "calendar"),
        TabItem("Info", Icons.Filled.Info, "info")
    )

    NavigationBar (
        containerColor = Purple40
    ){
        tabs.forEach { tab -> // used for looping in the nav bar

            // checks if current tab is the selected one
            val selected = tab.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(tab.label) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label
                    )
                }
            )
        }
    }
}





































