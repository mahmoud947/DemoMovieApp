package com.example.domain.errors


import com.example.core.utils.Resource
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {
    companion object {
        fun resolveError(e: Throwable): Resource.Error {
            var error = e

            when (e) {

                is SocketTimeoutException -> {
                    error = NetworkErrorException(errorMessage = "connection error!")
                }

                is ConnectException -> {
                    error = NetworkErrorException(errorMessage = "no internet access!")
                }

                is UnknownHostException -> {
                    error = NetworkErrorException(errorMessage = "no internet access!")
                }

                is retrofit2.HttpException -> {
                    when (e.response()?.code()) {
                        502 -> {
                            error = NetworkErrorException(e.code(), "internal error!")
                        }

                        401 -> {
                            error = AuthenticationException("authentication error!")
                        }

                        400 -> {
                            error = NetworkErrorException.parseException(e)
                        }

                        404 -> {
                            error = NotFoundException(message = "resource not found!")
                        }
                    }
                }

            }
            return Resource.Error(error)
        }
    }
}
