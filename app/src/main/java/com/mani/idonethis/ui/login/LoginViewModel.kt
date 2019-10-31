package com.mani.idonethis.ui.login

import androidx.lifecycle.*
import com.mani.idonethis.extensions.isValidUserName
import com.mani.idonethis.extensions.isValidPassword
import kotlinx.coroutines.launch

enum class LoginButtonStatus { ENABLED, IN_PROGRESS }

class LoginViewModel : ViewModel(), LifecycleObserver {
    val userName = MutableLiveData("")
    val password = MutableLiveData("")

    private val _isValidUserName = MutableLiveData<Boolean>()
    private val _isValidPassword = MutableLiveData<Boolean>()

    private val _loginButtonStatus = MutableLiveData(LoginButtonStatus.ENABLED)
    val loginButtonStatus: LiveData<LoginButtonStatus> get() = _loginButtonStatus

    val isValidUserName: LiveData<Boolean>
        get() = _isValidUserName
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    fun login() {
        _isValidUserName.value = userName.value?.isValidUserName()
        _isValidPassword.value = password.value?.isValidPassword()

        if (_isValidUserName.value == true &&
            isValidPassword.value == true
        ) {
            _loginButtonStatus.value =
                LoginButtonStatus.IN_PROGRESS

            viewModelScope.launch {

            }
        }
    }
}