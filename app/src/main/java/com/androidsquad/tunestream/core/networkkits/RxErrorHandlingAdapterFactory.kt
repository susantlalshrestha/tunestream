package com.androidsquad.tunestream.core.networkkits

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.rxjava3.core.Observable
import org.slf4j.LoggerFactory
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingAdapterFactory : CallAdapter.Factory() {
    private val mOriginalCallAdapterFactory: RxJava3CallAdapterFactory =
        RxJava3CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return RxCallAdapterWrapper(
            retrofit,
            mOriginalCallAdapterFactory.get(returnType, annotations, retrofit) as CallAdapter<*, *>
        )
    }

    private class RxCallAdapterWrapper<R : Any>(
        private val mRetrofit: Retrofit,
        private val mWrapperCallAdapter: CallAdapter<R, *>
    ) : CallAdapter<R, Observable<R>> {
        private val logger = LoggerFactory.getLogger(RxCallAdapterWrapper::class.java)

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Observable<R> {
            return (mWrapperCallAdapter.adapt(call) as Observable<R>)
                .onErrorResumeNext { Observable.error(asRetrofitException(it)) }
                .doOnError { t -> logger.error("accept:", t) }
        }

        override fun responseType(): Type = mWrapperCallAdapter.responseType()

        private fun asRetrofitException(throwable: Throwable): RetrofitException {
            if (throwable is IOException) return RetrofitException.networkError(throwable)

            if (throwable is HttpException) {
                val response: Response<*>? = throwable.response()

                val errorBody: String? = try {
                    response?.errorBody()?.string()
                } catch (e: IOException) {
                    return RetrofitException.networkError(e)
                }

                try {
                    val coreNetworkResponse =
                        Gson().fromJson(errorBody, ErrorBody::class.java)
                    return RetrofitException.httpError(
                        coreNetworkResponse.error_description,
                        response?.raw()?.request?.url.toString(),
                        response,
                        mRetrofit
                    )
                } catch (e: JsonSyntaxException) {
                    e.printStackTrace()
                }
            }

            return RetrofitException.unexpectedError(throwable)
        }
    }

    companion object {
        fun create(): CallAdapter.Factory = RxErrorHandlingAdapterFactory()
    }
}