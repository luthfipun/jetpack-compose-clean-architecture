package github.luthfipun.domain.util

sealed class DataState<out T>{
    data class Success<out R>(val data: R): DataState<R>()
    data class Error(val exception: Exception): DataState<Nothing>()
    object Loading: DataState<Nothing>()
}