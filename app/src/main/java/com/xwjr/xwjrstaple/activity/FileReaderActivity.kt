package com.xwjr.xwjrstaple.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.LinearLayout
import com.tencent.smtt.sdk.TbsReaderView
import com.xwjr.staple.extension.showToast
import com.xwjr.xwjrstaple.R
import kotlinx.android.synthetic.main.activity_file_reader.*

class FileReaderActivity : AppCompatActivity(), TbsReaderView.ReaderCallback {
    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {

    }

    private var filePath = ""
    private var fileName = ""
    private var mTbsReaderView: TbsReaderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_reader)

        defaultData()

    }

    private fun defaultData() {

        filePath = "/storage/emulated/0/XIAODAI/file/尹露542422199305278440.pdf"
        fileName = "尹露542422199305278440"


        mTbsReaderView = TbsReaderView(this, this)
        ll.addView(mTbsReaderView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        displayFile()
    }

    private fun displayFile() {
        try {
            if (!TextUtils.isEmpty(filePath)) {
                val bundle = Bundle()
                bundle.putString("filePath", filePath)
                bundle.putString("tempPath", "/storage/emulated/0/XIAODAI/file/")
                val result = mTbsReaderView?.preOpen("pdf", false)
                if (result != null && result) {
                    mTbsReaderView?.openFile(bundle)
                }

            } else {
                showToast("无法读取文件")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mTbsReaderView?.onStop()
    }

}
