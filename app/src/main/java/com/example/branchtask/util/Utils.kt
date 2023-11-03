package com.example.branchtask.util

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, text:String)
{
    Toast.makeText(context, text.toString(), Toast.LENGTH_SHORT).show()
}