package com.mani.idonethis.ui.login

import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.mani.idonethis.R

@BindingAdapter("bindUserNameErrorText")
fun bindUserNameErrorText(view: TextInputLayout, isValidCitizenId: Boolean?) {
    if (isValidCitizenId == false) {
        view.error = view.context.getString(R.string.something_wrong)
        view.isHintEnabled = view.editText?.length() != 0
    } else {
        view.error = null
        view.isErrorEnabled = false
    }
}

@BindingAdapter("bindPasswordErrorText")
fun bindPasswordErrorText(view: TextInputLayout, isValidMobileNumber: Boolean?) {
    if (isValidMobileNumber == false) {
        view.error = view.context.getString(R.string.something_wrong)
        view.isHintEnabled = view.editText?.length() != 0
    } else {
        view.error = null
        view.isErrorEnabled = false
    }
}

@BindingAdapter("bindLoginButtonStatus")
fun bindLoginButtonStatus(view: MaterialButton, loginButtonStatus: LoginButtonStatus?) {
    val context = view.context
    when (loginButtonStatus) {
        LoginButtonStatus.ENABLED -> {
            view.text = "Login"
            view.isEnabled = true
            view.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
        }
        LoginButtonStatus.IN_PROGRESS -> {
            view.text = "Logging In"
            view.isEnabled = false
            view.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorAccent)
        }
    }
}