package com.example.vector.access

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.vector.data.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserSession(context: Context) {
    private lateinit var sharedPreferences: SharedPreferences
    private var mUserViewModel: UserViewModel? = null
    private var context: Context? = context
    private var vmso: ViewModelStoreOwner? = null

    constructor(context: Context, viewModelStoreOwner: ViewModelStoreOwner) : this(context) {
        this.vmso = viewModelStoreOwner
        mUserViewModel = ViewModelProvider(vmso!!).get(UserViewModel::class.java)
    }

    fun saveSession() {
        sharedPreferences.edit().putBoolean("USER_DEFINED", true).apply()
    }

    fun isSignIn(): Boolean {
        sharedPreferences = context!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("USER_DEFINED", false)
    }

    fun signOut() {
        sharedPreferences = context!!.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("USER_DEFINED", false).apply()
    }

    suspend fun userDefined(loginText: String, passwordText: String): Boolean =
        withContext(Dispatchers.IO) {
            val user = mUserViewModel?.findUser(loginText, passwordText)
            return@withContext user != null
        }
}