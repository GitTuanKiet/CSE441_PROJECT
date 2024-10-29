package com.cse_411_project.aigy.khanh.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.khanh.activity.RecoveryMethodActivity
import com.cse_411_project.aigy.khanh.activity.SignUpActivity

class PhoneNumberVerifyDialogFragment : DialogFragment() {

    interface OnDialogDismissListener {
        fun onDialogDismiss()
    }

    private var dismissListener: OnDialogDismissListener? = null
    private var timer: CountDownTimer? = null
    private lateinit var txtResend: TextView
    private lateinit var txtPhoneNumber: TextView
    private var isTimerRunning = false
    private lateinit var edtSlot1: EditText
    private lateinit var edtSlot2: EditText
    private lateinit var edtSlot3: EditText
    private lateinit var edtSlot4: EditText
    private lateinit var edtSlot5: EditText
    private lateinit var edtSlot6: EditText
    private lateinit var btnVerify: Button

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_phone_number_verify, container, false)
        txtPhoneNumber = view.findViewById(R.id.txt_phone_number)

        val phoneNumber = arguments?.getString("phone_number")
        txtPhoneNumber.text = phoneNumber ?: "Số điện thoại không có sẵn"

        edtSlot1 = view.findViewById(R.id.etn_slot_1)
        edtSlot2 = view.findViewById(R.id.etn_slot_2)
        edtSlot3 = view.findViewById(R.id.etn_slot_3)
        edtSlot4 = view.findViewById(R.id.etn_slot_4)
        edtSlot5 = view.findViewById(R.id.etn_slot_5)
        edtSlot6 = view.findViewById(R.id.etn_slot_6)
        txtResend = view.findViewById(R.id.txt_resend)
        btnVerify = view.findViewById(R.id.btn_verify)

        setupEditTextListeners()
        underlineText(txtResend, "Resend")

        txtResend.setOnClickListener {
            if (!isTimerRunning) {
                if (activity is SignUpActivity) {
                    (activity as? SignUpActivity)?.resendOtp()
                } else if (activity is RecoveryMethodActivity) {
                    (activity as? RecoveryMethodActivity)?.resendOtp()
                }
                startCountdown(60)
            }
        }

        btnVerify.setOnClickListener {
            val otpCode = listOf(edtSlot1, edtSlot2, edtSlot3, edtSlot4, edtSlot5, edtSlot6)
                .joinToString("") { it.text.toString() }
            verifyOtp(otpCode)
        }

        return view
    }

    private fun setupEditTextListeners() {
        val editTexts = listOf(edtSlot1, edtSlot2, edtSlot3, edtSlot4, edtSlot5, edtSlot6)

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && i < editTexts.size - 1) {
                        editTexts[i + 1].requestFocus()
                    } else if (s?.isEmpty() == true && i > 0) {
                        editTexts[i - 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun verifyOtp(code: String) {
        if (activity is SignUpActivity) {
            (activity as? SignUpActivity)?.verifyOtp(code)
        } else if (activity is RecoveryMethodActivity) {
            (activity as? RecoveryMethodActivity)?.verifyOtp(code)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.onDialogDismiss()
    }

    fun setOnDismissListener(listener: OnDialogDismissListener) {
        dismissListener = listener
    }

    private fun underlineText(textView: TextView, text: String) {
        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 0, text.length, 0)
        textView.text = spannableString
    }

    private fun startCountdown(seconds: Int) {
        timer?.cancel()
        isTimerRunning = true
        timer = object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (isAdded) {
                    val remainingSeconds = (millisUntilFinished / 1000).toInt()
                    val fullText = "Please wait $remainingSeconds seconds to resend"
                    val spannableString = SpannableString(fullText)

                    val start = fullText.indexOf(remainingSeconds.toString())
                    val end = start + remainingSeconds.toString().length

                    spannableString.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.red)),
                        start,
                        end,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    txtResend.text = spannableString
                }
            }

            override fun onFinish() {
                underlineText(txtResend, "Resend")
                isTimerRunning = false
            }
        }
        timer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isTimerRunning) {
            timer?.cancel()
        }
    }
}
