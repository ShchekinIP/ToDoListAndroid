package com.example.myapplication.fragments.apiFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.model.FormModel
import com.example.myapplication.model.LoginModel

class LoginFragment : Fragment() {

    private val formModel: FormModel by activityViewModels()
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.toRegisterButton.setOnClickListener {
            formModel.toRegisterClicked.value = true
        }

        binding.loginButton.setOnClickListener {
            if (canSignIn()) {
                formModel.loginForm.value = LoginModel(binding.login.text.toString(), binding.password.text.toString())
            }
        }
    }

    private fun canSignIn(): Boolean {
        binding.apply {
            if (login.text.toString().isEmpty()) {
                login.error = resources.getString(R.string.required)
            }
            if (password.text.toString().isEmpty()) {
                password.error = resources.getString(R.string.required)
            }
        }

        return binding.password.error == null &&
                binding.login.error == null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}