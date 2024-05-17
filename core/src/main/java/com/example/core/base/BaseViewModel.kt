package com.example.core.base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.utils.ErrorModel
import com.example.core.utils.SingleLiveEvent
import com.example.startupproject.ui.base.NavigationCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val showErrorMessage: SingleLiveEvent<ErrorModel?> = SingleLiveEvent()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBarInt: SingleLiveEvent<Int> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val showNoData: MutableLiveData<Boolean> = MutableLiveData()


    override fun onCleared() {
        super.onCleared()
    }
}