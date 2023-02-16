package com.aconcepcion.respiriassessment.logs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import com.aconcepcion.respiriassessment.databinding.DialogLogEventBinding


/**
 * Use the [LogEventDialog.newInstance] factory method to
 * create an instance of this dialog.
 */
class LogEventDialog : BottomSheet() {

    private lateinit var binding: DialogLogEventBinding

    companion object {
        fun newInstance(): LogEventDialog {
            return LogEventDialog()
        }
    }

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLogEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}