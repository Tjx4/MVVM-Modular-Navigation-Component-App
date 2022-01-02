package com.domain.myapplication.extensions

import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern


private fun evaluateRegex(valu: String, regex: String): Boolean {
    val inputStr = valu.trim { it <= ' ' }
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(inputStr)
    return matcher.matches()
}

fun String.isValidUsername() =  isValidName() || isValidMobileNumber() || isValidEmail()

fun String.isValidName(): Boolean =  !this.isEmpty() && this.length > 2

fun String.isValidMobileNumber(): Boolean =  evaluateRegex(this, "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")

fun String.isValidEmail(): Boolean =  evaluateRegex(this, "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")

fun String.isValidPassword(): Boolean =  evaluateRegex(this, "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")

fun String.isMatchPasswords(passwordConfirmation: String?): Boolean = this == passwordConfirmation

fun getUniqueString(): String {
    return UUID.randomUUID().toString()
}

fun String.getUniqueString(): String {
    return UUID.randomUUID().toString()
}

fun String.toSha1String(): String {
    try {
        val crypt = MessageDigest.getInstance("SHA-1")
        crypt.reset()
        crypt.update(this.toByteArray(charset("UTF-8")))
        return BigInteger(1, crypt.digest()).toString(16)
    } catch (ex: Exception) {
        return this
    }
}