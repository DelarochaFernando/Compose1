package com.inmar.compose1.consultafolios

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaFolios(navController: NavController){

    val searchInput = rememberSaveable{mutableStateOf("")}
    val showTextFieldSearch = rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Consulta de Folios",
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back to MenuProcPrim")
                    }
                },
                actions = {
                    

                    IconButton(onClick = {/*
                    open the textfield for the input to search
                    */
                        showTextFieldSearch.value = !showTextFieldSearch.value
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Open search textfield"
                        )
                    }
                    if(showTextFieldSearch.value){
                        TextField(
                            value = searchInput.value,
                            label = { Text(text = "Buscar", color = Color.White)},
                            trailingIcon = {
                                IconButton(
                                    onClick = {showTextFieldSearch.value = false}) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        tint = Color.White,
                                        contentDescription = "close the search textfield"
                                    )
                                }
                            },
                            onValueChange = {searchInput.value = it}
                        )
                    }
                    IconButton(
                        onClick = {/*refresh the data showed in the list*/}) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Refresh the list items"
                        )
                    }
                }
            )
        },
        content = {
            Column {
                Spacer(modifier = Modifier
                    .height(8.dp)
                    .padding(it))
                LazyColumn{
                    items(15){ item->
                        Text(text = "Item #${item}")
                    }
                }
            }
        }
    ) 
}