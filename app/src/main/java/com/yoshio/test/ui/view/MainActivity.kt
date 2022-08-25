package com.yoshio.test.ui.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.yoshio.test.R
import com.yoshio.test.databinding.ActivityMainBinding
import com.yoshio.test.ui.viewmodel.LoginUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val loginUserViewModel: LoginUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObsevers()
        initListeners()
    }

    private fun initObsevers() {
        loginUserViewModel.showMessage.observe(this, Observer { message ->
            showAlert(getString(R.string.txt_error), message)
            binding.signInButton.isEnabled = true
        })
        loginUserViewModel.isLoading.observe(this, Observer { loading ->
            binding.progress.isVisible = loading
        })
        loginUserViewModel.userDetailModel.observe(this, Observer { user ->
            binding.signInButton.isEnabled = true
            showAlert(getString(R.string.txt_login_success), "${getString(R.string.txt_name)} ${user.firstName} ${user.lastName}" +
                    "\n${getString(R.string.txt_email)} ${user.email}\n${getString(R.string.txt_token)} ${user?.jwtToken?.substring(0, 15)}...")
        })
    }

    private fun initListeners() {
        binding.signInButton.setOnClickListener {
            binding.signInButton.hideKeyboard()
            binding.signInButton.isEnabled = false
            loginUserViewModel.signIn(binding.mailEditText.text.toString().trim(), binding.passwordEditText.text.toString())
        }
    }

    fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setPositiveButton(getString(R.string.txt_accept), null)
            show()
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}