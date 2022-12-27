package com.inmar.compose1.consultafolios

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.inmar.compose1.R
import com.inmar.compose1.data.PensionesApplication
import com.inmar.compose1.ui.theme.Purple200
import com.inmar.compose1.ui.theme.Purple500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaFolios(navController: NavController){

    val searchInput = rememberSaveable{mutableStateOf("")}
    val showTextFieldSearch = rememberSaveable { mutableStateOf(false) }
    val showlabelBuscar = rememberSaveable { mutableStateOf(true) }
    val consultaFoliosViewModel = rememberSaveable {
        ConsultaFoliosViewModel(application = PensionesApplication())
    }

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
                            label = {
                                if (showlabelBuscar.value){
                                    Text(
                                        text = "Buscar",
                                        color = Color.White
                                    )
                                }
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        showTextFieldSearch.value = false
                                        searchInput.value = ""
                                        showlabelBuscar.value = true
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        tint = Color.White,
                                        contentDescription = "close the search textfield"
                                    )
                                }
                            },
                            onValueChange = {
                                searchInput.value = it
                                showlabelBuscar.value = false
                            }
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
            Column(modifier = Modifier.padding(it)) {
                consultaFoliosViewModel.getFoliosFromServer()
                Spacer(modifier = Modifier
                    .height(8.dp)
                    .padding(it))
                LazyColumn{
                    items(15){ item->
                        ConsultaFoliositem()
                        Spacer(modifier = Modifier
                            .height(4.dp)
                            .background(Purple500))
                    }
                }
            }
        }
    ) 
}

@Composable
@Preview
fun ConsultaFoliosItemPreview(){
    ConsultaFoliositem()
}

@Composable
fun ConsultaFoliositem(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {})
    ){
        CircularShapeWithLetterInside(textInside = "F",
            modifier = Modifier.weight(1f,true))
        Column(modifier = Modifier
                .weight(1f,true)
            //.padding(end = 12.dp)
        ) {
            Text(text = "Folio : 00000026939",fontSize = 20.sp, color = Purple500)
            Text(text= "PRESTAMO" ,color= Color.Black, fontWeight = FontWeight.Bold)
            Text(text = "RENOVACION DE PRESTAMO PGB",color= Color.Black,fontWeight = FontWeight.Bold)
            Text(text = "Póliza: 001254")
            Text(text = "Núcleo: 01")
            Text(text = "IMSS")
            Text(text = "MANUELA SERRANO SERRANO")
        }
        Column(modifier = Modifier
            .weight(1f,true)
            //.padding(end = 8.dp)
        ) {
            Text(text = "Fecha Alta ", color = Purple500)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text= "2022-12-16 11:05:55" ,color= Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Estatus",color= Purple500)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "ENVIADO A RDE PA...")
        }
    }
}

@Composable
fun CircularShapeWithLetterInside(textInside : String,modifier: Modifier){

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Box(
            modifier= modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Purple200)
        ){
            Box(modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Purple500)
                .align(Alignment.Center)
            ){
                Text(
                    text = textInside,
                    color = Purple200,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.align(Alignment.Center)
                )
            }
        }
    }
}