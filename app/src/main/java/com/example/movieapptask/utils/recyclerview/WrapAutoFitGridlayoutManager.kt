package com.example.fakestore.utils.recyclerview

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapptask.utils.recyclerview.AutoFitGridLayoutManager

class WrapAutoFitGridlayoutManager(context: Context?, columnWidth: Int) :
    AutoFitGridLayoutManager(context,columnWidth){

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("TAG", "meet a IOOBE in RecyclerView")
        }
    }
}