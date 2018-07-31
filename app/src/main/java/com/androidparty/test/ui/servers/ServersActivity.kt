package com.androidparty.test.ui.servers

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.androidparty.test.AndroidPartyApp
import com.androidparty.test.R
import com.androidparty.test.event.IDataCallback
import com.androidparty.test.model.ServerResponse
import com.androidparty.test.remote.helper.RxHelper
import com.androidparty.test.repository.NetworkRepository
import com.androidparty.test.ui.login.LoginActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_servers.*
import javax.inject.Inject

class ServersActivity : AppCompatActivity() {

    @Inject
    lateinit var provider: NetworkRepository

    private var servers: List<ServerResponse>? = null
    private var serverAdapter: ServerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as AndroidPartyApp).appComponent.inject(this)
        setContentView(R.layout.activity_servers)
        serverAdapter = ServerAdapter()
        refresh.setOnRefreshListener {
            loadServers()
        }
        rvServers.apply {
            layoutManager = LinearLayoutManager(this@ServersActivity)
            adapter = serverAdapter
        }

        if (servers == null) {
            loadServers()
        }
        logout.setOnClickListener {
            AndroidPartyApp.app?.clearToken()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun loadServers() {
        RxHelper.executeSingle(provider.servers(AndroidPartyApp.app?.getToken()),
                CompositeDisposable(), object : IDataCallback<List<ServerResponse>> {
            override fun onStart() {
                refresh.visibility = View.GONE
                progress.visibility = View.VISIBLE
                tvInfo.text = this@ServersActivity.getString(R.string.progress_text)
            }

            override fun onResponse(response: List<ServerResponse>?) {
                refresh.isRefreshing = false
                refresh.visibility = View.VISIBLE
                serverAdapter?.addServers(response)
            }

            override fun onFail(throwable: Throwable?) {
                refresh.isRefreshing = false
                refresh.visibility = View.GONE
                progress.visibility = View.GONE
                tvInfo.text = this@ServersActivity.getString(R.string.error_fetching_list)
            }
        })
    }
}
