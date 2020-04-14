package com.fakher.multimodule.mvvm.usecase.signup

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
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    private val viewModel: SignupViewModel by lazy {
        ViewModelProviders.of(this, activity?.injector?.getSignupVMFactory())
            .get(SignupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signupBtn.setOnClickListener { onSignup(it) }
        gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.newUser.observe(this, Observer { user ->
            Toast.makeText(activity, "Signup complete!", Toast.LENGTH_SHORT).show()
            val action =
                SignupFragmentDirections.actionGoToMain(
                    user
                )
            Navigation.findNavController(signupUsername).navigate(action)
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer { spinner ->
            signupLoading.visibility = if (spinner) View.VISIBLE else View.GONE
        })
    }

    private fun onSignup(v: View) {
        val username = signupUsername.text.toString()
        val password = signupPassword.text.toString()
        val info = otherInfo.text.toString()

        viewModel.signup(username, password, info)
    }

    private fun onGotoLogin(v: View) {
        val action =
            SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }

}
