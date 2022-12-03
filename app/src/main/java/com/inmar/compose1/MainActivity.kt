package com.inmar.compose1

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.inmar.compose1.data.SampleData
import com.inmar.compose1.navigation.NavigationHost
import com.inmar.compose1.ui.theme.Compose1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Compose1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Compose1Theme() {
                        //Surface(modifier = Modifier.fillMaxSize()) {
                            //Conversation(SampleData.conversationSample  )
                            //runninTheApp()
                            val navController = rememberNavController()
                            navController.enableOnBackPressed(false)
                            NavigationHost(navController = navController)
                            //LoginScreen()
                        //}
                    //}
                }
            //}
        }
    }
}

@Preview(showBackground = true,
    widthDp = 350,
    heightDp = 620,
    uiMode = UI_MODE_NIGHT_NO)
@Composable
fun runLogin(){
    val navController = rememberNavController()
    LoginScreen(navController)
}

data class Message(val author:String, val body:String)

@Composable
fun MessageCard(msg : Message){
    Row(modifier = Modifier.padding(all = 8.dp)) {
        //var visibility by ani { mutableStateOf(false)}
        /*Image(
            painter = painterResource(R.drawable.ic_account_dark),
            contentDescription = "autor No image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )*/
        Image(
            painter = painterResource(R.drawable.ic_account_light),
            contentDescription = "autor No image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false)}
        val surfaceColor by animateColorAsState(
            if(isExpanded)MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        val extraPadding by animateDpAsState(
            if (isExpanded) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Column(modifier = Modifier
            .clickable { isExpanded = !isExpanded }
            .weight(1f))
            {
            Text(text = "Author ${msg.author}",
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                //color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(all = 4.dp)
                //.padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text ="${msg.body}",
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        IconButton(onClick = { isExpanded = !isExpanded }) {
            Icon(
                imageVector = if(isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if(isExpanded) "Show less" else "Show more"
            )
        }
    }
}

//@Preview(name = "Light mode" )
@Composable
fun DefaultPreview() {
    Compose1Theme {
        //Greeting("Fernando")
        Surface() {
            MessageCard(Message("Katzenbach","El Psicoanalista"))
        }
    }
}

//@Preview(
//    name = "Dark mode",
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true
//)
//@Preview
//@Composable
//fun PreviewConversation(){
//    Compose1Theme() {
//        Conversation(SampleData.conversationSample)
//    }
//}

@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun listOfCardsPreview(){
    Compose1Theme {
        var listOfCards = listOf<String>("Hola","Mundo","de nuevo")
        ListOfCardsWithButton(listOfCards)
    }
}

@Composable
fun ListOfCardsWithButton(cards : List<String>){
    LazyColumn{
        items(cards){card ->
            CardWithButton(card)
        }
    }
}


@Composable
fun CardWithButton(card:String){
    var expanded = remember { mutableStateOf(false)}
    var extraPadding = if(expanded.value) 48.dp else 0.dp
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp,horizontal = 8.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)) {
                Text(text = card)
            }
            OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                Text(text = if(expanded.value)"Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnboardingScreen(OnContinueClicked: () -> Unit){
//    var shouldShowOnBoarding by remember {
//        mutableStateOf(true)
//    }
    Surface() {
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome")
            Button(modifier = Modifier.padding(vertical = 24.dp),
                onClick = OnContinueClicked) {
                Text(text = "Continue")
            }
        }
    }
}


@Composable
fun OnBoardingPreview(){
    Compose1Theme {
        OnboardingScreen(OnContinueClicked = {})
    }
}

@Preview(showBackground = true,
    widthDp = 350,
    heightDp = 620,
    uiMode = UI_MODE_NIGHT_NO)
@Composable
fun runninTheApp(){
    Compose1Theme() {
        var shouldShowOnBoarding by rememberSaveable {
            mutableStateOf(true)
        }
        if(shouldShowOnBoarding){
            //OnBoardingPreview()
            OnboardingScreen(OnContinueClicked = {shouldShowOnBoarding = false})
        }else{

            var listOfCards = listOf<String>("Hola","Mundo","de nuevo")
            //ListOfCardsWithButton(listOfCards)
            Conversation(SampleData.conversationSample)
        }
    }

}