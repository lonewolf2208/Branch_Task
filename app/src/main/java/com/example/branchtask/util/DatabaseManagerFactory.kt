package com.example.branchtask.util

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.branchtask.util.DataBaseManager


@RequiresApi(Build.VERSION_CODES.M)
class DatabaseManagerFactory {

    fun create(context: Context, sharedPreferencesName: String): StoreData {
        val keyGenSpec = KeyGenParameterSpec.Builder(
            sharedPreferencesName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            sharedPreferencesName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return DataBaseManager(encryptedSharedPreferences)
    }
}