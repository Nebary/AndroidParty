package com.androidparty.test.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.androidparty.test.AndroidPartyApp
import com.androidparty.test.R
import com.androidparty.test.event.IDataCallback
import com.androidparty.test.model.LoginRequest
import com.androidparty.test.model.TokenResponse
import com.androidparty.test.remote.helper.RxHelper
import com.androidparty.test.repository.NetworkRepository
import com.androidparty.test.ui.servers.ServersActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var provider: NetworkRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as AndroidPartyApp).appComponent.inject(this)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun validateEmptyFields(): Boolean {
        return etUserName.text.toString().isEmpty() && etPassword.text.toString().isEmpty()
    }

    private fun login() {
        if (validateEmptyFields()) {
            Toast.makeText(this, getString(R.string.warning_empty_fields), Toast.LENGTH_LONG).show()
            return
        }
        val loginRequest = LoginRequest(etUserName.text.toString(), etPassword.text.toString())
        RxHelper.executeSingle(provider.authorization(loginRequest),
                CompositeDisposable(), object : IDataCallback<TokenResponse> {
            override fun onStart() {
                progress.visibility = View.VISIBLE
                loginScreen.visibility = View.GONE
            }

            override fun onResponse(response: TokenResponse?) {
                AndroidPartyApp.app?.saveToken(response?.token)
                startActivity(Intent(this@LoginActivity, ServersActivity::class.java))
                finish()
            }

            override fun onFail(throwable: Throwable?) {
                Toast.makeText(this@LoginActivity, getString(R.string.incorrect_credentials), Toast.LENGTH_LONG).show()
                progress.visibility = View.GONE
                loginScreen.visibility = View.VISIBLE
            }
        })
    }
}
