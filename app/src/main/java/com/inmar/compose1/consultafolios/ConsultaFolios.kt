package com.inmar.compose1.consultafolios

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.graphics.fonts.FontStyle
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.inmar.compose1.R
import com.inmar.compose1.data.PensionesApplication
import com.inmar.compose1.ui.theme.Purple200
import com.inmar.compose1.ui.theme.Purple500
import com.inmar.compose1.consultafolios.ConsultaFoliosViewModel.State
import com.inmar.compose1.data.Book
import org.jetbrains.annotations.Contract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaFolios(navController: NavController){

    val searchInput = rememberSaveable{mutableStateOf("")}
    val showTextFieldSearch = rememberSaveable { mutableStateOf(false) }
    val showlabelBuscar = rememberSaveable { mutableStateOf(true) }
    val consultaFoliosViewModel : ConsultaFoliosViewModel =
        viewModel(factory = ConsultaFoliosViewModel.ConsultaViewModelFactory(app = Application()))
//    val consultaFoliosViewModel = rememberSaveable {
//        ConsultaFoliosViewModel(application = PensionesApplication())
//    }

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
                        onClick = {
                            consultaFoliosViewModel
                                .booklist
                                .tryEmit(
                                    ConsultaFoliosViewModel.Result.ResultBooks(
                                        state = State.Loading,
                                        categoryWithBooks = listOf()
                                    )
                                )
                            consultaFoliosViewModel.fetchBooks()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Refresh the list items"
                        )
                    }
                }
            )
        },
        content = {
            val booklist = consultaFoliosViewModel.booklist.collectAsState()
            when(booklist.value.state){
                State.Loading->{
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            progress = 0.8f,
                            color = Color.Magenta,
                            strokeWidth = 4.dp,
                        )
                    }
                }
                State.Success->{
                    Column(modifier = Modifier.padding(it)) {
                        Spacer(modifier = Modifier
                            .height(8.dp)
                            .padding(it))
                        LazyColumn{
//                            items(15){ item->
//                                ConsultaFoliositem(consultaFoliosViewModel)
//                                Spacer(modifier = Modifier
//                                    .height(4.dp)
//                                    .background(Purple500))
//                            }
                            booklist.value.categoryWithBooks.forEach{
                                itemsIndexed(
                                    items = it.bookList,
                                    itemContent = {pos,book->
                                        //ConsultaFoliositem(book)
                                        BookListItem(book = book)
                                    }
                                )
                            }
                        }
                    }
                }
                State.Failed ->{
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Failed to load list")
                    }
                }
            }
        }
    ) 
}

@Composable
@Preview
fun ConsultaFoliosItemPreview(){
    //ConsultaFoliositem()
}

//@Contract(pure = true)
@Composable
fun BookListItem(book : Book){
    Card(
        elevation = 4.dp,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp), content = {
                    val bitmap = remember { mutableStateOf(BitmapFactory.decodeFile(book.img)) }
                    //val bitmap = remember {mutableStateOf(null)}
                    val context = LocalContext.current
                    Glide.with(context)
                        .asBitmap()
                        .load(book.thumbNail)
                        .into(
                            object : CustomTarget<Bitmap>() {
                                override fun onLoadCleared(placeholder: Drawable?) {}
                                 override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                                ) {
                                    bitmap.value = resource
                                }
                            }
                        )
                    val value = bitmap.value
                    if (value != null)
                        Image(
                            bitmap = value.asImageBitmap(),
                            contentDescription = "image",
                            Modifier
                                .width(80.dp)
                                .height(100.dp)
                        )
                    else
                        Box(
                            content = {
                                Text(
                                    text = "Loading",
                                    fontSize = 16.sp,
                                )
                            }, modifier = Modifier
                                .width(80.dp)
                                .height(100.dp)
                                .border(
                                    width = 1.2.dp,
                                    color = Color.White,
                                    shape = RectangleShape
                                ),
                            contentAlignment = Alignment.Center
                        )

                    Spacer(modifier = Modifier.size(16.dp))
                    Column(
                        modifier = Modifier.weight(2F),
                        content = {
                            Text(
                                text = book.title,
                                fontSize = 20.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Price: $${book.price}",
                                fontSize = 16.sp,
                                maxLines = 1,
                                color = Color.LightGray,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Author: ${book.author}",
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        })
                })
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    )
}

@Composable
fun ConsultaFoliositem(book: Book){
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