package io.my.data.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal class CryptoManager(
    private val keysToStringSeparator: String = "||",
    private val bytesToStringSeparator: String = "|"
){
    private val provider = "AndroidKeyStore"
    private val cipher by lazy { Cipher.getInstance("AES/GCM/NoPadding") }
    private val charset by lazy { charset("UTF-8") }
    private val keyStore by lazy { KeyStore.getInstance(provider).apply { load(null) } }
    private val keyGenerator by lazy { KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, provider) }

    suspend fun encryptData(keyAlias: String, text: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(keyAlias))
        val keyIv = Base64.encodeToString(cipher.iv, Base64.DEFAULT)
        return "$keyIv$keysToStringSeparator${cipher.doFinal(text.toByteArray(charset)).joinToString(bytesToStringSeparator)}"
    }

    suspend fun decryptData(keyAlias: String, encryptedData: String): String {
        val values = encryptedData.split(keysToStringSeparator)
        val keyIv = Base64.decode(values[0], Base64.DEFAULT)
        val encryptedText = values[1]
            .split(bytesToStringSeparator)
            .map { listStr -> listStr.toByte() }
            .toByteArray()
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keyAlias), GCMParameterSpec(128, keyIv))
        return cipher.doFinal(encryptedText).toString(charset)
    }

    private fun generateSecretKey(keyAlias: String): SecretKey {
        return keyGenerator.apply {
            init(
                KeyGenParameterSpec
                    .Builder(keyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
        }.generateKey()
    }

    private fun getSecretKey(keyAlias: String) =
        (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
}