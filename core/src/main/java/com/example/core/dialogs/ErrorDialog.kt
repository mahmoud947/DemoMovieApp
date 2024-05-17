package com.example.core.dialogs

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.core.R


class ErrorDialog constructor(
    private val activity: Activity,
    private val errorMessage: String = "check your internet connection",
    private val buttonTitle: String,
    ) {
    private lateinit var dialog: AlertDialog
    private var isShow: Boolean = false
    private var _errorMessage: String = errorMessage
    private var _buttonTitle: String = buttonTitle
    private var _onClick: () -> Unit = {}

    fun updateMessage(message: String) {
        _errorMessage = message
    }

    fun updateButtonTitle(title: String) {
        _buttonTitle = title
    }

    fun onClick(onClick: () -> Unit) {
        _onClick = onClick
    }

    fun startDialog() {
        if (isShow)
            return
        isShow = true
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.WrapContentDialog)
        val view: View? = activity.layoutInflater.inflate(R.layout.error_dialog, null)
        builder.setView(view)
        builder.setCancelable(false)
        dialog = builder.create()
        val tvDialogTitle = view?.findViewById<TextView>(R.id.tv_dialog_message)
        val retryButton = view?.findViewById<Button>(R.id.btn_error_dialog)
        tvDialogTitle?.text = _errorMessage
        retryButton?.text = _buttonTitle

        retryButton?.setOnClickListener {
            _onClick.invoke()
        }
        dialog.show()

    }

    fun dismissDialog() {
        if (isShow) {
            dialog.dismiss()
            isShow = false
        }
    }
}