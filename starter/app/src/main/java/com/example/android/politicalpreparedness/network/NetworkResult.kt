package com.example.android.politicalpreparedness.network

data class NetworkResult<out R>(val status: LocalStatus, val data: R?, val msg: String?) {

    companion object {
        fun <R> success(successData: R?): NetworkResult<R> {
            return NetworkResult(LocalStatus.SUCCESS, successData, null)
        }

        fun <R> error(errorMessage: String, errorData: R?): NetworkResult<R> {
            return NetworkResult(LocalStatus.ERROR, errorData, errorMessage)
        }

        fun <R> loading(loadingData: R?): NetworkResult<R> {
            return NetworkResult(LocalStatus.LOADING, loadingData, null)
        }

    }

}

enum class LocalStatus {
    SUCCESS,
    ERROR,
    LOADING
}