package com.example.myshoppinglist.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myshoppinglist.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInput")
fun bindErrorInput(textInputLayout: TextInputLayout, error: Boolean){
    if (error) textInputLayout.error = textInputLayout.context.getString(R.string.invalid_input)
    else textInputLayout.error = null

}

@BindingAdapter("resetErrorInputCount")
fun bindResetErrorInputCount(textInputEditText: TextInputEditText, viewModel: AddShopItemViewModel) {
    textInputEditText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.resetErrorInputCount()
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

@BindingAdapter("resetErrorInputName")
fun bindResetErrorInputName(textInputEditText: TextInputEditText, viewModel: AddShopItemViewModel) {
    textInputEditText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.resetErrorInputName()
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

@BindingAdapter("numberAsString")
fun bindingNumberAsString(textView: TextView, number: Number){
    textView.text = number.toString()
}


