package com.example.branchtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.branchtask.model.LoginBody
import com.example.branchtask.ui.theme.BranchTaskTheme
import com.example.branchtask.util.Resource
import com.example.branchtask.util.showToast
import com.example.branchtask.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BranchTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var navController= rememberNavController()
                    NavHost(navController = navController, startDestination = "login")
                    {
                        composable("login")
                        {
                            var viewmodel = hiltViewModel<LoginViewModel>()
                            Login(viewmodel, navController = navController)
                        }
                        composable("home")
                        {
                            Home()
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Home()
{

}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Login(viewmodel: LoginViewModel,navController: NavController)
{
    Column(modifier= Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var resp = viewmodel.loginData.observeAsState().value
        when(resp)
        {
            is Resource.Success ->{navController.navigate("home")}
            is Resource.Error->{
                showToast(LocalContext.current,resp.error.toString())
            }
            else ->{}
        }
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                // Perform login logic here using 'username' and 'password'
                var data = LoginBody(password=password,username=username)
                      viewmodel.login(data=data)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BranchTaskTheme {
        Greeting("Android")
    }
}