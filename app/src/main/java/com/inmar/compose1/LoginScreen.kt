package com.inmar.compose1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.inmar.compose1.ui.theme.Compose1Theme



@Composable
fun LoginScreen(navController: NavController){

    //val navController = rememberNavController()
    var enableButtonEmailAndPasswordText by rememberSaveable{mutableStateOf(false)}
    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }

    Compose1Theme() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextWithIcon(isPassword = false,"Email",
                onTextOutput = {emailText = it})
            Spacer(modifier = Modifier.height(8.dp))
            TextWithIcon(isPassword = true,"Password",
            onTextOutput = {passwordText = it})

            enableButtonEmailAndPasswordText = emailText.length!=0 && passwordText.length!=0

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { navController.navigate("menuprocprim")},
                enabled = enableButtonEmailAndPasswordText,
                modifier = Modifier.padding(start = 8.dp)) {
                Text(text = "Log in")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    val navController = rememberNavController()
    LoginScreen(navController)
}


//@SuppressLint("SuspiciousIndentation")
@Composable
fun TextWithIcon(isPassword:Boolean, labelText:String, onTextOutput : (String) ->Unit){

    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }
    var showPasswordText by rememberSaveable { mutableStateOf(true) }

    OutlinedTextField(
        value = if (isPassword) passwordText else emailText,
        onValueChange ={
            if (isPassword) passwordText = it else emailText = it
            onTextOutput(it)
        },
        label = {Text(labelText)},
        visualTransformation = if(isPassword && showPasswordText && labelText.equals("Password")) PasswordVisualTransformation() else  VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val imageIcon = if(isPassword && showPasswordText)Icons.Filled.VisibilityOff else Icons.Filled.Visibility
            if(isPassword){
                IconButton(onClick = {
                    showPasswordText = !showPasswordText
                }) {
                    Icon(
                        imageVector = imageIcon,
                        contentDescription = "hide password"
                    )
                }
            }
        },
        modifier = Modifier.padding(8.dp)
    )
}