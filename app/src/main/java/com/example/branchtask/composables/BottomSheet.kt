package com.example.branchtask.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.branchtask.model.GetHomeResponse
import com.example.branchtask.ui.theme.buttonColor
import com.example.branchtask.util.Resource
import com.example.branchtask.util.showToast
import com.example.branchtask.viewmodel.HomeViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showSheet: MutableState<Boolean>,
    viewModel: HomeViewModel,
    data: GetHomeResponse
) {
//    ModalBottomSheet(
//        onDismissRequest = {
//            showSheet.value = false
//        }, containerColor = Color.White,
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//        var text by remember { mutableStateOf("") }
//        var resp = viewModel.postData.observeAsState(Resource.Loading()).value
//        var context = LocalContext.current
//        when (resp) {
//                is Resource.Success -> {
//                    showToast(context, "Reply Sent")
//                    viewModel.resetPostData()
//                    showSheet.value = false
//                }
//                is Resource.Error -> {
//                    showToast(context, resp.error.toString())
//                }
//                else -> {}
//            }
//
//
//        Column(
//            modifier = Modifier
//                .padding(start = 20.dp, end = 20.dp)
//                .verticalScroll(state = rememberScrollState())
//                ,
//            verticalArrangement = Arrangement.Center,
//        ) {
//
//            Text(
//                text = "Messages !",
//                fontFamily = FontFamily.SansSerif,
//                fontSize = 24.sp,
//                textAlign = TextAlign.Start,
//                fontWeight = FontWeight.Medium
//            )
//            LazyColumn{
//                items(data.size)
//                {
//                    Text(text = data[it].body.toString())
//                    Divider(thickness = 1.dp, modifier = Modifier.padding(top=4.dp, bottom = 4.dp))
//                }
//            }
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 4.dp)
//                    .background(Color.White),
//                elevation = 8.dp,
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Row {
//                    TextField(
//                        value = text,
//                        onValueChange = {
//                            text = it
//                        },
//                        placeholder = {
//                            Text(text = "Enter your message here ")
//                        },
//                        keyboardOptions = KeyboardOptions.Default.copy(
//                            keyboardType = KeyboardType.Text
//                        ),
//                        modifier = Modifier
//                            .fillMaxWidth(0.9f)
//                            .height(80.dp),
//                        colors = TextFieldDefaults.textFieldColors(
//                            backgroundColor = Color.White,
//                            focusedIndicatorColor = Color.Transparent,
//                            unfocusedIndicatorColor = Color.Transparent,
//                            disabledIndicatorColor = Color.Transparent
//                        )
//                    )
//                    Button(onClick = {
//                        if (text != "") {
//                            viewModel.postMsg(data[0].thread_id,text.toString())
//                        }
//                        else{
//                            showToast(context = context,"Enter something to proceed further")
//                        }
//
//                    },
//                        modifier = Modifier
//                            .padding(top = 8.dp, bottom = 48.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = buttonColor
//                        )
//                    ) {
//                        Text(text = "Send Reply", color = Color.White)
//                    }
//
//                }
//            }
//    }
}
