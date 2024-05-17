package com.example.core.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.core.dialogs.ErrorDialog
import com.example.startupproject.ui.base.NavigationCommand
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel
    lateinit var errorDialog: ErrorDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorDialog = ErrorDialog(activity = requireActivity(), buttonTitle = "Retry")
    }

    override fun onStart() {
        super.onStart()

        errorDialog.onClick {
            errorDialog.dismissDialog()
        }


        viewModel.showErrorMessage.observe(this) {
            if (it != null) {
                errorDialog.updateMessage(it.message)
                errorDialog.updateButtonTitle(it.button)
                errorDialog.startDialog()
            }
        }


        viewModel.showToast.observe(this) {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }
        viewModel.showSnackBar.observe(this) {
            Snackbar.make(this.requireView(), it, Snackbar.LENGTH_LONG).show()
        }
        viewModel.showSnackBarInt.observe(this) {
            Snackbar.make(this.requireView(), getString(it), Snackbar.LENGTH_LONG).show()
        }

        viewModel.navigationCommand.observe(this) { command ->
            when (command) {
                is NavigationCommand.To -> findNavController().navigate(command.directions)
                is NavigationCommand.Back -> findNavController().popBackStack()
                is NavigationCommand.BackTo -> findNavController().popBackStack(
                    command.destinationId, false
                )
                is NavigationCommand.UnAuthorized<*> -> {
                    val intent = Intent(requireContext(), command.activity)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is NavigationCommand.ToActivity<*> -> {
                    val intent = Intent(requireContext(), command.activity)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }


    fun Context.hasPermissions( permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}
