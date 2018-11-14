package com.xwjr.staple.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.KeyEvent
import android.view.View
import com.xwjr.staple.R
import com.xwjr.staple.extension.loadGif
import com.xwjr.staple.extension.logI
import kotlinx.android.synthetic.main.staple_progress_hint.view.*

class ProgressDialogFragment : DialogFragment() {

    private var updateView: View? = null
    private var hint = ""
    private var resId = 0

    companion object {
        fun newInstance(resId: Int? = 0, hint: String? = ""): ProgressDialogFragment {
            return ProgressDialogFragment().apply {
                if (hint.isNullOrEmpty()) {
                    this.hint = "加载中..."
                } else {
                    this.hint = hint!!
                }
                if (resId == null || resId == 0) {
                    this.resId = R.mipmap.staple_loading
                } else {
                    this.resId = resId
                }
            }
        }
    }


    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(activity)
        updateView = activity?.layoutInflater?.inflate(R.layout.staple_progress_hint, null, false)
        dialogBuilder.setView(updateView)
        val alertDialog = dialogBuilder.create()
        updateView?.iv_loading?.loadGif(resId)
        updateView?.tv_hint?.text = hint

        //点击空白处不可取消
        alertDialog.setCanceledOnTouchOutside(false)

        //拦截返回键点击事件
        alertDialog.setOnKeyListener { _, keyCode, _ ->
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    logI("返回事件已经被拦截")
                    return@setOnKeyListener true
                }
                else -> return@setOnKeyListener false
            }
        }
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return alertDialog
    }

    fun show(fragmentManager: FragmentManager) {
        try {
            show(fragmentManager, "ProgressDialogFragment")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
