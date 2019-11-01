package com.mani.idonethis.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mani.idonethis.R
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mani.idonethis.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login, container, false
        )

        binding.viewmodel = loginViewModel
        binding.lifecycleOwner = this
        this.lifecycle.addObserver(loginViewModel)

        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        loginViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer {
            when(it.uid) {
                "-1" -> {
                    AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
                        .setMessage("Please check email and password")
                        .setCancelable(true)
                        .setPositiveButton("OK", null)
                        .show()
                }
                else -> {
                    val imm =
                        context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.textInputEditTextPassword.windowToken, 0)

                        findNavController().navigate(R.id.action_nav_send_to_nav_home)
                }
            }
        })

        return binding.root
    }
}