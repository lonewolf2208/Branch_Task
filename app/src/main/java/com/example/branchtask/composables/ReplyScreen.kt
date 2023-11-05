package com.example.branchtask.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.branchtask.model.GetHomeResponse
import com.example.branchtask.ui.theme.buttonColor
import com.example.branchtask.util.Resource
import com.example.branchtask.util.convertTimestampToDateTime
import com.example.branchtask.util.showToast
import com.example.branchtask.viewmodel.HomeViewModel

@Composable
fun ReplyScreen(viewModel: HomeViewModel) {
    var text by remember { mutableStateOf("") }
    var data = viewModel._sortedData.observeAsState().value?.sortedBy { it.timestamp}
    var context = LocalContext.current
    var resp = viewModel.postData.observeAsState().value
    LaunchedEffect(resp)
    {
        when (resp) {
            is Resource.Success -> {
                text = ""
//                showToast(context, "Message Sent")
            }

            is Resource.Error -> {
                showToast(context, resp.error.toString())
            }

            else -> {}
        }
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
    ) {

        Column(modifier = Modifier.weight(2f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Messages ",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 6.dp)

                )
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.clickable {
                    viewModel.reset()
                })
            }
            LazyColumn {
                items(data?.size ?: 0)
                {
                    Text(text = data?.get(it)?.body.toString())
                    Text(
                        text = convertTimestampToDateTime(data?.get(it)?.timestamp.toString()).toString(),
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.End
                    )
                    Divider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text(text = "Enter reply ")
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f),

                )
            Button(
                onClick = {
                    if (text != "") {
                        viewModel.postMsg(data?.get(0)?.thread_id ?: "", text.toString())
                    } else {
                        showToast(context = context, "Enter something to proceed further")
                    }

                },
                modifier = Modifier
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor
                )
            ) {
                Text(text = "Send ", color = Color.White)
            }


        }


    }
}