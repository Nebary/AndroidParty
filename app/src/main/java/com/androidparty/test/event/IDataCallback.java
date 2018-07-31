package com.androidparty.test.event;


public interface IDataCallback<DATA> {

    void onStart();

    void onResponse(DATA response);

    void onFail(Throwable throwable);
}
