package com.rgg.cryptolist.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.rgg.cryptolist.R
import com.rgg.cryptolist.databinding.CustomDialogBinding


class CommonDialog {

    companion object {
        fun showDialog(context: Context, title: String) {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            val binding = CustomDialogBinding
                .inflate(LayoutInflater.from(context))
            dialog.setContentView(binding.root)

            binding.tvBody.text = title
            binding.btnYes.setOnClickListener {
                dialog.dismiss()
            }
            val yesBtn = dialog.findViewById(R.id.btn_yes) as Button
            yesBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }
    }

}