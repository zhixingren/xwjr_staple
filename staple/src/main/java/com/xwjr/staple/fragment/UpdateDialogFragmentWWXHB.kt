package com.xwjr.staple.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.text.Html
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xwjr.staple.R
import com.xwjr.staple.constant.StapleConfig
import com.xwjr.staple.manager.MyFileProvider
import com.xwjr.staple.extension.err
import com.xwjr.staple.extension.getFile
import com.xwjr.staple.extension.logI
import com.xwjr.staple.extension.showToast
import com.xwjr.staple.permission.PermissionUtils
import com.xwjr.staple.presenter.StapleHttpContract
import com.xwjr.staple.presenter.StapleHttpPresenter
import kotlinx.android.synthetic.main.staple_update_hint_wwxhb.view.*

class UpdateDialogFragmentWWXHB : DialogFragment(), StapleHttpContract {


    private var forceUpdate = true
    private var downloadUrl = ""
    private var version = ""
    private var content = ""
    private var httpPresenter: StapleHttpPresenter? = null
    private var updateView: View? = null


    companion object {
        fun newInstance(forceUpdate: Boolean, downloadUrl: String, version: String, content: String): UpdateDialogFragmentWWXHB {
            return UpdateDialogFragmentWWXHB().apply {
                arguments = Bundle().apply {
                    putBoolean("forceUpdate", forceUpdate)
                    putString("downloadUrl", downloadUrl)
                    putString("version", version)
                    putString("content", content)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forceUpdate = arguments?.getBoolean("forceUpdate")!!
        downloadUrl = arguments?.getString("downloadUrl")!!
        version = arguments?.getString("version")!!
        content = arguments?.getString("content")!!
        httpPresenter = StapleHttpPresenter(context!!, this)
    }


    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(activity)
        updateView = activity?.layoutInflater?.inflate(R.layout.staple_update_hint_wwxhb, null, false)
        dialogBuilder.setView(updateView)
        val alertDialog = dialogBuilder.create()


        //默认隐藏进度条,显示按钮
        updateView?.group_progress?.visibility = View.GONE
        updateView?.tv_updateNow?.visibility = View.VISIBLE
        updateView?.tv_updateLater?.visibility = View.VISIBLE

        //立即更新点击事件
        updateView?.tv_updateNow?.setOnClickListener {
            if (PermissionUtils.checkPermission(context,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                            "读写权限")) {
                httpPresenter?.downLoadApk(downloadUrl)
                updateView?.group_progress?.visibility = View.VISIBLE
                updateView?.tv_updateNow?.visibility = View.GONE
                updateView?.tv_updateLater?.visibility = View.GONE
            }

        }

        //是否显示稍后更新按钮
        if (forceUpdate) {
            updateView?.tv_updateLater?.visibility = View.GONE
            updateView?.view_bottomBlank?.visibility = View.VISIBLE
        } else {
            updateView?.tv_updateLater?.visibility = View.VISIBLE
            updateView?.view_bottomBlank?.visibility = View.GONE
        }

        //稍后更新点击事件
        updateView?.tv_updateLater?.setOnClickListener {
            alertDialog.dismiss()
            cancelUpdateListener?.cancel()
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
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return alertDialog
    }

    override fun onStart() {
        super.onStart()
        //设置dialog宽
        if (dialog != null) {
            val dm = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
            dialog.window?.setLayout((dm.widthPixels * 0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, "UpdateViewDialog")
    }


    override fun statusBack(i: String, data: Any) {
        when (i) {
            downloadUrl -> {
                val deltaTrans = (updateView?.pb?.width!! / 100.0)
                data as Bundle
                val percent = data.getInt("progress")
                val percentDisplay = "${data.getInt("progress")}%"
                updateView?.tv_progress?.translationX = ((percent) * deltaTrans).toFloat()
                updateView?.pb?.progress = percent
                updateView?.tv_progress?.text = percentDisplay
                if (percent == 100) {
                    updateView?.group_progress?.visibility = View.GONE
                    updateView?.tv_updateNow?.visibility = View.VISIBLE
                    updateView?.tv_updateLater?.visibility = View.GONE
                    updateView?.view_bottomBlank?.visibility = View.VISIBLE
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
            val file = getFile(StapleConfig.getAppFilePath() + "/" + StapleConfig.getAppFileName())
            if (file!=null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                MyFileProvider.setIntentDataAndType(context!!, intent, "application/vnd.android.package-archive", file, true)
                context?.startActivity(intent)
            }else{
                showToast("未获取到安装文件")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(StapleConfig.getAppInstallFailHint())
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
