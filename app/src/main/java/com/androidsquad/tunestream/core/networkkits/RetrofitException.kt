package com.androidsquad.tunestream.core.networkkits

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RetrofitException(
    message: String?,
    exception: Throwable?,
    private val url: String?,
    val response: Response<*>?,
    private val kind: Kind,
    private val retrofit: Retrofit?
) : RuntimeException(message, exception) {

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified {@code type}.
     */
    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        return response?.errorBody()?.let { errorBody ->
            val converter: Converter<ResponseBody, T>? =
                retrofit?.responseBodyConverter(type, arrayOfNulls(0))
            converter?.convert(errorBody)
        }
    }

    /**
     * Identifies the event kind which triggered a {@link RetrofitException}.
     */
    enum class Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,

        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,

        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    companion object {

        fun httpError(
            message: String? = null,
            url: String,
            response: Response<*>?,
            retrofit: Retrofit
        ): RetrofitException =
            RetrofitException(
                message ?: "${response?.code()} ${response?.message()}",
                null,
                url,
                response,
                Kind.HTTP,
                retrofit
            )

        fun networkError(exception: IOException) = when (exception) {
            is SocketTimeoutException, is UnknownHostException -> RetrofitException(
                "We are unable to communicate with server. Please make sure you are connected to the internet and try again.",
                exception,
                null,
                null,
                Kind.NETWORK,
                null
            )
            else -> RetrofitException(
                exception.message,
                exception,
                null,
                null,
                Kind.UNEXPECTED,
                null
            )
        }

        fun unexpectedError(exception: Throwable) = RetrofitException(
            exception.message,
            exception,
            null,
            null,
            Kind.UNEXPECTED,
            null
        )
    }

}