package com.fakher.multimodule.mvvm.usecase.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.fakher.multimodule.mvvm.R
import com.fakher.multimodule.mvvm.di.injector
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, activity?.injector?.getLoginVMFactory())
            .get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener { onLogin(it) }
        gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loggedUser.observe(this, Observer { user ->
            Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()
            val action =
                LoginFragmentDirections.actionGoToMain(
                    user
                )
            Navigation.findNavController(loginUsername).navigate(action)
        })

        viewModel.loading.observe(this, Observer { loading ->
            loginLoading.visibility = if (loading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onLogin(v: View) {
        val username = loginUsername.text.toString()
        val password = loginPassword.text.toString()
        viewModel.login(username, password)
    }

    private fun onGotoSignup(v: View) {
        val action =
            LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }

}
