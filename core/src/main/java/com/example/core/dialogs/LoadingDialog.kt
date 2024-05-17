//package com.example.core.presentaion.dialogs
//
//
//import android.app.Activity
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.drawable.BitmapDrawable
//import android.renderscript.Allocation
//import android.renderscript.Element
//import android.renderscript.RenderScript
//import android.renderscript.ScriptIntrinsicBlur
//import android.view.KeyEvent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.WindowManager
//import androidx.appcompat.app.AlertDialog
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.navigation.NavController
//import com.example.core.R
//import com.example.core.databinding.LoadingDialogBinding
//import com.example.core.extensions.hideKeyboard
//
//
//class LoadingDialog constructor(
//    private val activity: Activity,
//    private val navController: NavController
//) {
//    private lateinit var binding: LoadingDialogBinding
//    private lateinit var dialog: AlertDialog
//    private var isShow: Boolean = false
//
//
//    fun startDialog() {
//        if (isShow)
//            return
//        isShow = true
//        val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.WrapContentDialog)
//        val inflater: LayoutInflater = activity.layoutInflater
//        val view = inflater.inflate(R.layout.loading_dialog, null)
//        builder.setView(view)
//        //addBlurEffect(view)
//
//        builder.setCancelable(false)
//
//        dialog = builder.create()
//        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//        dialog.show()
//
//        dialog.setOnKeyListener { dialog, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
//                activity.hideKeyboard()
//                navController.popBackStack()
//                dialog.dismiss()
//            }
//
//            true
//        }
//    }
//
//
//    fun dismissDialog() {
//        if (isShow) {
//            dialog.dismiss()
//            isShow = false
//        }
//    }
//
//    fun addBlurEffect(view: View) {
//        // Get the view to be blurred
//        val viewToBlur: ConstraintLayout = view.findViewById(R.id.cl_loading_background)
//
//        // Set up the view to be drawn into a bitmap
//        val bitmap = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        viewToBlur.draw(canvas)
//
//        // Create a RenderScript context
//        val rs = RenderScript.create(view.context)
//
//        // Create an input allocation for the bitmap
//        val input = Allocation.createFromBitmap(rs, bitmap)
//
//        // Create an output allocation with the same dimensions as the input allocation
//        val output = Allocation.createTyped(rs, input.type)
//
//        // Create a blur script and set the blur radius
//        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
//        blurScript.setRadius(25f)
//
//        // Apply the blur effect to the input allocation and store the result in the output allocation
//        blurScript.setInput(input)
//        blurScript.forEach(output)
//
//        // Copy the result from the output allocation to a new bitmap
//        val blurredBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
//        output.copyTo(blurredBitmap)
//
//        // Set the blurred bitmap as the background of the view
//        val blurredDrawable = BitmapDrawable(view.resources, blurredBitmap)
//        viewToBlur.background = blurredDrawable
//    }
//
//}