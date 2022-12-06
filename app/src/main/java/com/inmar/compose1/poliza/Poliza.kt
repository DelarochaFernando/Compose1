package com.inmar.compose1.poliza

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.inmar.compose1.menuprocprim.MenuProcPrimPreview
import com.inmar.compose1.menuprocprim.MenuProcPrimViewModel
import kotlin.math.max

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Poliza(navController: NavController,idsubproc : String?,subproctext : String?){

    val menuProcPrimViewModel = MenuProcPrimViewModel()
    var procprimText = rememberSaveable { mutableStateOf("")}
    var polizanumber = rememberSaveable { mutableStateOf("")}
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

                procprimText.value = menuProcPrimViewModel.textoProcPrim.value.toString()
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${procprimText.value!!}>",
                    Modifier
                        .size(25.dp)
                        .padding(16.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${subproctext!!}>", Modifier.size(12.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Elige el régimen adecuado.", Modifier.size(12.dp))
                polizaRegimenYNucleo(
                    onTextOutPut = {polizanumber.value = it},
                    polizaNumber = polizanumber,
                    nucleo = nucleo,
                    regimen = regimen,
                    regimenSelected = regimenSelected)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview
fun polizaRegimenYNucleo(onTextOutPut : (String) ->Unit,
polizaNumber: MutableState<String>, nucleo : MutableState<String>,
regimen : MutableState<String>,regimenSelected : MutableState<Boolean>
){

    Column() {
        Spacer(modifier = Modifier.height(4.dp))
        Row() {
            AssistChip(
                onClick = {
                    regimen.value = "IMSS"
                    regimenSelected.value = true
                },
                label = { Text(text = "IMSS") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Localized description",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            AssistChip(
                onClick = {
                    regimen.value = "ISSSTE"
                    regimenSelected.value = true
                },
                label = { Text(text = "ISSSTE") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Localized description",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(){
            //OutlinedTextField(value = {polizaNumber.value.toString()}, onValueChange ={onTextOutPut(it)} )
            Text(text = "hola")
            Text(text = "-", modifier = Modifier
                .size(15.dp)
                .padding(start = 8.dp, end = 8.dp))
            Text(text = "hola")
        }
    }
    
}