package com.entreprisecorp.projectinfo901.fastitem

import android.view.LayoutInflater
import android.view.ViewGroup
import com.entreprisecorp.projectinfo901.databinding.MessageItemLayoutBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MessageItem(
    var nameSender: String? = null,
    var message: String? = null
) : AbstractBindingItem<MessageItemLayoutBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): MessageItemLayoutBinding {
        return MessageItemLayoutBinding.inflate(inflater, parent, false)
    }

    override val type: Int = 123

    override fun bindView(binding: MessageItemLayoutBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.apply {
            nameSenderTextView.text = nameSender
            messageTextView.text = message
        }
    }

    override fun unbindView(binding: MessageItemLayoutBinding) {
        super.unbindView(binding)
        binding.apply {
            nameSenderTextView.text = null
            messageTextView.text = null
        }
    }


}