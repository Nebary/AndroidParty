package com.androidparty.test.remote.helper


import com.androidparty.test.event.IDataCallback
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable


object RxHelper {

    /**
     * Create from [Single] from function
     *
     * @param func Callable<T>
    </T> */
    fun <DATA> makeSingle(func: Callable<DATA>): Single<DATA> {
        return Single.fromCallable(func)
    }

    /**
     * Method for starting to perform asynchronous tasks
     *
     * @param single          @see [Single]
     * @param compositeDisposable @see [CompositeDisposable]
     * @param callback            @see [IDataCallback]
     */
    fun <DATA> executeSingle(single: Single<DATA>,
                             compositeDisposable: CompositeDisposable,
                             callback: IDataCallback<DATA>): Disposable {
        return single.compose(applySchedulers<DATA>())
                .doOnSubscribe { disposable ->
                    callback.onStart()
                    compositeDisposable.add(disposable)
                }.subscribe({ response -> callback.onResponse(response) }, { throwable -> callback.onFail(throwable) })
    }

    private fun <T> applySchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
