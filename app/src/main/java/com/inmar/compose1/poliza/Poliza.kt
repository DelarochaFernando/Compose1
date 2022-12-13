package com.inmar.compose1.poliza

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.inmar.compose1.menuprocprim.MenuProcPrimPreview
import com.inmar.compose1.menuprocprim.MenuProcPrimViewModel
import java.time.format.TextStyle
import kotlin.math.max

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Poliza(navController: NavController,
           _procprimText : String?,
           idsubproc : String?,
           subproctext : String?){

    val menuProcPrimViewModel = MenuProcPrimViewModel()
    var procprimText = rememberSaveable { mutableStateOf(_procprimText)}
    val polizanumber = rememberSaveable { mutableStateOf("")}
//    var polizanumber by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue("Póliza", TextRange(0,5)))
//    }
    var nucleo = rememberSaveable{ mutableStateOf("")}
    var regimen = rememberSaveable { mutableStateOf("")}
    var regimenSelected = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = "Validación Poliza",
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
                            contentDescription = "back to SubProcesos")
                    }
                }
            )
        },
        content = {
            Column() {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${procprimText.value!!} >",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                    //Modifier.fillMaxWidth().padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${subproctext!!}",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 45.dp))
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Elige el régimen adecuado.",
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                polizaRegimenYNucleo(
                    onTextOutPut = {polizanumber.value = it},
                    polizaNumber = polizanumber,
                    nucleo = nucleo,
                    regimen = regimen,
                    regimenSelected = regimenSelected
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
//@Preview
fun polizaRegimenYNucleo(onTextOutPut : (String) ->Unit,
polizaNumber: MutableState<String>, nucleo : MutableState<String>,
regimen : MutableState<String>,regimenSelected : MutableState<Boolean>
){

    var enableChipIMSS = rememberSaveable { mutableStateOf(false) }
    var enableChipISSSTE = rememberSaveable { mutableStateOf(false) }
    var maxLengthPoliza = 6
    var maxLengthNucleo = 2
    val focusManager = LocalFocusManager.current

    var chipcolor = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colors.onSecondary)


    Column() {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            //verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            Chip(
                onClick = {
                    regimen.value = "IMSS"
                    regimenSelected.value = true
                    enableChipIMSS.value = true
                    enableChipISSSTE.value = !enableChipIMSS.value
                },
                enabled = enableChipIMSS.value,
                leadingIcon ={
                    if(enableChipIMSS.value){
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "IMSS Chip",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                },
                colors = chipcolor,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "IMSS")
            }
            Chip(
                onClick = {
                    regimen.value = "ISSSTE"
                    regimenSelected.value = true
                    enableChipISSSTE.value = true
                    enableChipIMSS.value = !enableChipISSSTE.value
                },
                enabled = enableChipISSSTE.value,
                leadingIcon = {
                    if(enableChipISSSTE.value){
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "ISSSTE Chip",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                }
            ) {
                Text(text = "ISSSTE")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                //.wrapContentSize(Alignment.CenterStart,unbounded = false)

        ) {
            OutlinedTextField(
                value = polizaNumber.value,
                onValueChange = {
                    polizaNumber.value = it.take(maxLengthPoliza)
                    if(it.length == maxLengthPoliza){
                        focusManager.moveFocus(FocusDirection.Right)
                    }
                },
                label = { Text(text = "Póliza")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(30f, true)
                    .padding(start = 20.dp)
            )
            Text(text = "-",fontSize = 45.sp,
                modifier = Modifier
                    //.weight(15f,true)
                    .padding(horizontal = 12.dp))
            OutlinedTextField(
                value = nucleo.value,
                onValueChange = {
                    nucleo.value = it.take(maxLengthNucleo)
                    if(it.length == maxLengthNucleo){
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                                },
                label = { Text(text = "Núcleo")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(20f,true)
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(20f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp, top = 8.dp, end = 10.dp)
            ) {
                Text(text = "Verificar")
            }
            
        }
    }
    
}