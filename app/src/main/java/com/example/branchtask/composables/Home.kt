package com.example.branchtask.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.branchtask.model.GetHomeResponse
import com.example.branchtask.ui.theme.buttonColor
import com.example.branchtask.util.Resource
import com.example.branchtask.util.convertTimestampToDateTime
import com.example.branchtask.util.showToast
import com.example.branchtask.viewmodel.HomeViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(viewmodel: HomeViewModel,navController: NavController) {
    LaunchedEffect(Unit)
    {
        viewmodel.home()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Inbox",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 32.sp,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 6.dp)

            )
        }
        var resp = viewmodel.homeData.observeAsState().value
        when (resp) {
            is Resource.Success -> {
                var reqData= resp.data?.let { viewmodel.getSortedData(data = it) }
                LazyColumn {
                    if (reqData != null) {
                        for ((threadId, messages) in reqData) {
                            item {
                                HomeCard(data = messages, viewmodel = viewmodel, navController = navController)
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                showToast(LocalContext.current, resp.error.toString())
            }
            else -> {
                CircularProgressIndicator(color = buttonColor)
            }
        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeCard(data: List<GetHomeResponse>, viewmodel: HomeViewModel,navController: NavController) {
    var showBottomsheet = remember {
        mutableStateOf(false)
    }
    if (showBottomsheet.value) {
//        BottomSheet(showSheet = showBottomsheet, viewmodel, data)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        backgroundColor = Color.White,
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Text(text = "ID : " + data[0].id.toString())
            Text(text = data[0].body.toString())

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = convertTimestampToDateTime(data[0].timestamp.toString()).toString(),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(top = 6.dp)
                )
                Button(
                    onClick = {
                              viewmodel._sortedData.postValue(data)
                              navController.navigate("reply")},
                    colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor)
                )
                {
                    Text(text = "Reply", color = Color.White)
                }
            }
        }
    }
}