package com.example.movieapptask.utils.recyclerview

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class AutoFitGridLayoutManager(context: Context?, columnWidth: Int) :
    GridLayoutManager(context, 1) {
    private var columnWidth = 0
    private var columnWidthChanged = true

    init {
        setColumnWidth(columnWidth)
    }

    fun setColumnWidth(newColumnWidth: Int) {
        if (newColumnWidth > 0 && newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth
            columnWidthChanged = true
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        try {
            if (columnWidthChanged && columnWidth > 0) {
                val totalSpace: Int
                totalSpace = if (orientation == VERTICAL) {
                    width - paddingLeft-paddingRight
                } else {
                    height - paddingTop - paddingBottom
                }
                val spanCount = 1.coerceAtLeast(totalSpace / columnWidth)
                setSpanCount(spanCount)
                columnWidthChanged = false
            }
            super.onLayoutChildren(recycler, state)
        }catch (e: IndexOutOfBoundsException){
            Log.e("TAG", "meet a IOOBE in RecyclerView")
        }

    }
}