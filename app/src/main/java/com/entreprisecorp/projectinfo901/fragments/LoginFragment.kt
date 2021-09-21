package com.entreprisecorp.projectinfo901.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.entreprisecorp.projectinfo901.R
import com.entreprisecorp.projectinfo901.databinding.FragmentLoginBinding

class LoginFragment:Fragment(R.layout.fragment_login) {
    private var binding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding?.loginButton?.setOnClickListener {
            val userName = binding?.loginEditText?.text.toString()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(userName))
        }
    }



}