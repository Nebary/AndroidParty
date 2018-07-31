package com.androidparty.test.event

import android.widget.Toast
import com.androidparty.test.AndroidPartyApp


open class SimpleDataCallback<DATA> :IDataCallback<DATA>{
    override fun onStart() {

    }

    override fun onResponse(response: DATA) {

    }

    override fun onFail(throwable: Throwable?) {
        if (throwable != null) {
            Toast.makeText(AndroidPartyApp.app, throwable.message, Toast.LENGTH_LONG).show()
        }
    }
}