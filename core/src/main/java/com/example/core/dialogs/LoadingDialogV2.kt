//package com.example.core.presentaion.dialogs
//
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.view.*
//import androidx.fragment.app.DialogFragment
//import com.example.core.R
//import com.example.core.databinding.LoadingDialogBinding
//
//class LoadingDialogV2 : DialogFragment() {
//    lateinit var binding: LoadingDialogBinding
//
//
//    companion object {
//        private lateinit var INSTANCE: LoadingDialogV2
//        fun getInstance(): LoadingDialogV2 {
//            if (!::INSTANCE.isInitialized) {
//                INSTANCE = LoadingDialogV2()
//            }
//            return INSTANCE
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = LoadingDialogBinding.inflate(inflater, container, false)
//
//        dialog?.setCanceledOnTouchOutside(false)
//
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//        setStyle(STYLE_NO_FRAME, R.style.WrapContentDialog)
//
//        return binding.root
//    }
//
//
//}