package com.yoshio.test.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoshio.test.domain.LoginUserUseCase
import com.yoshio.test.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginUserViewModel @Inject constructor(
        private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"

    private val _userDetailModel = MutableLiveData<User>()
    val userDetailModel: LiveData<User>
        get() = _userDetailModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _showMessage = MutableLiveData<String>()
    val showMessage: LiveData<String>
        get() = _showMessage

    fun signIn(email: String, password: String) {
        _isLoading.value = true
        if (validateCredentials(email, password)) {
            viewModelScope.launch {
                val result = loginUserUseCase.signIn(email, password)
                if (result != null && result.isSuccess) {
                    _userDetailModel.value = result.getOrNull()
                    _isLoading.value = false

                } else {
                    _showMessage.value = result.exceptionOrNull()?.message
                    _isLoading.value = false
                }
            }
        } else {
            _showMessage.value = "Invalid credentials"
            _isLoading.value = false
        }
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        return (Pattern.compile(REGEX_EMAIL).matcher(email).matches() && !password.isNullOrEmpty())
    }

}