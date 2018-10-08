package com.xwjr.staple.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.text.Html
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.xwjr.staple.R
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.extension.MyFileProvider
import com.xwjr.staple.extension.err
import com.xwjr.staple.extension.logI
import com.xwjr.staple.presenter.StapleHttpContract
import com.xwjr.staple.presenter.StapleHttpPresenter
import kotlinx.android.synthetic.main.staple_update_hint.view.*
import java.io.File

class UpdateDialogFragment : DialogFragment(), StapleHttpContract {


    private var cancelAble = false
    private var downloadUrl = ""
    private var version = ""
    private var content = ""
    private var httpPresenter: StapleHttpPresenter? = null
    private var updateView: View? = null


    companion object {
        fun newInstance(cancelAble: Boolean, downloadUrl: String, version: String, content: String): UpdateDialogFragment {
            return UpdateDialogFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("cancelAble", cancelAble)
                    putString("downloadUrl", downloadUrl)
                    putString("version", version)
                    putString("content", content)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancelAble = arguments?.getBoolean("cancelAble")!!
        downloadUrl = arguments?.getString("downloadUrl")!!
        version = arguments?.getString("version")!!
        content = arguments?.getString("content")!!
        httpPresenter = StapleHttpPresenter(context!!, this)
    }


    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(activity)
        updateView = activity?.layoutInflater?.inflate(R.layout.staple_update_hint, null, false)
        dialogBuilder.setView(updateView)
        val alertDialog = dialogBuilder.create()

        //立即更新点击事件
        updateView?.tv_updateNow?.setOnClickListener {
            httpPresenter?.downLoadApk(downloadUrl)
        }

        //稍后更新点击事件
        updateView?.tv_updateLater?.setOnClickListener {
            alertDialog.dismiss()
            cancelUpdateListener?.cancel()
        }

        //是否显示稍后更新按钮
        if (cancelAble) {
            updateView?.tv_updateLater?.visibility = View.VISIBLE
        } else {
            updateView?.tv_updateLater?.visibility = View.GONE
        }
        //版本号
        updateView?.tv_version?.text = version

        //升级内容
        @Suppress("DEPRECATION")
        updateView?.tv_content?.text = Html.fromHtml(content)

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
        return alertDialog
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, "UpdateViewDialog")
    }


    override fun statusBack(i: String, data: Any) {
        when (i) {
            downloadUrl -> {
                updateView?.tv_updateNow?.isEnabled = false
                data as Int
                updateView?.pb?.progress = data
                val percent = "$data%"
                updateView?.tv_updateNow?.text = percent
                updateView?.tv_updateNow?.setOnClickListener { }
                if (data == 100) {
                    updateView?.tv_updateNow?.isEnabled = true
                    updateView?.tv_updateNow?.text = "安装"
                    updateView?.tv_updateNow?.setOnClickListener {
                        install()
                    }
                    install()

                }

            }
            downloadUrl.err() -> {
                updateView?.tv_updateNow?.isEnabled = true
                updateView?.tv_updateNow?.text = "重新下载"
                updateView?.tv_updateNow?.setOnClickListener {
                    httpPresenter?.downLoadApk(downloadUrl)
                }
            }
        }
    }

    /**
     * 安装apk
     */
    private fun install() {
        try {
            val file = File(StapleConfig.getAppFilePath(), StapleConfig.getAppFileName())
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyFileProvider.setIntentDataAndType(context!!, intent, "application/vnd.android.package-archive", file, true)
            context?.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, StapleConfig.getAppInstallFailHint(), Toast.LENGTH_SHORT).show()
        }

    }

    /**取消更新回调*/
    private var cancelUpdateListener: CancelUpdate? = null

    interface CancelUpdate {
        fun cancel()
    }

    fun setCancelUpdateListener(cancelUpdate: CancelUpdate) {
        cancelUpdateListener = cancelUpdate
    }
}
