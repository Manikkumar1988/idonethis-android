package com.mani.idonethis.ui.login

import androidx.lifecycle.*
import com.mani.idonethis.UserSharedPreference
import com.mani.idonethis.extensions.isValidPassword
import com.mani.idonethis.extensions.isValidUserName
import com.mani.idonethis.ui.login.model.User
import com.mani.idonethis.ui.login.repository.UserRepository
import kotlinx.coroutines.launch

enum class LoginButtonStatus { ENABLED, IN_PROGRESS }

class LoginViewModel(private val userRepository: UserRepository,
                     val userSharedPreference: UserSharedPreference) : ViewModel(), LifecycleObserver {
    val userName = MutableLiveData("")
    val password = MutableLiveData("")

    val userMutableLiveData = MutableLiveData<User>()

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
                val user = userRepository.login(userName.value!!,password.value!!)

                if(user.isSuccessful) {
                    val userResponseObject = user.body()

                    userMutableLiveData.value = userResponseObject
                    userResponseObject?.let {
                        userSharedPreference.addUser(it.uid)
                    }
                }

                _loginButtonStatus.value =
                    LoginButtonStatus.ENABLED
            }

        }
    }
}