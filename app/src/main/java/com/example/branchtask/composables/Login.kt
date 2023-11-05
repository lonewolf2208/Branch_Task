package com.example.branchtask.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.branchtask.model.LoginBody
import com.example.branchtask.ui.theme.buttonColor
import com.example.branchtask.util.AppDatabase
import com.example.branchtask.util.Resource
import com.example.branchtask.util.showToast
import com.example.branchtask.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(viewmodel: LoginViewModel, navController: NavController)
{
    Column( verticalArrangement = Arrangement.Center,modifier= Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(start = 16.dp, end = 16.dp)
        ) {
        var context= LocalContext.current
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var resp = viewmodel.loginData.observeAsState().value
        LaunchedEffect(resp )
        {
            when (resp) {
                is Resource.Success -> {
                    AppDatabase.sharedPreferencesManager.putString(
                        "authToken",
                        resp.data?.auth_token.toString()
                    )
                    navController.popBackStack()
                    navController.navigate("home")
                }

                is Resource.Error -> {
                    showToast(context, resp.error.toString())
                }
                else -> {}
            }
        }
        Text("Username")
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter your Username") },
            modifier = Modifier
                .fillMaxWidth(),
        )
        Text(text = "Password", modifier = Modifier.padding(top=16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter your password") },
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                // Perform login logic here using 'username' and 'password'
                var data = LoginBody(password=password,username=username)
                viewmodel.login(data = data)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor
            )
        ) {
            Text("Login", color = Color.White)
        }

    }
}