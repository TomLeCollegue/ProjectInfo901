package com.entreprisecorp.projectinfo901.fastitem

import android.view.LayoutInflater
import android.view.ViewGroup
import com.entreprisecorp.projectinfo901.databinding.MessageSendItemLayoutBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MessageSendItem(
    var nameReceiver: String? = null,
    var message: String? = null
) : AbstractBindingItem<MessageSendItemLayoutBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): MessageSendItemLayoutBinding {
        return MessageSendItemLayoutBinding.inflate(inflater, parent, false)
    }

    override val type: Int = 123

    override fun bindView(binding: MessageSendItemLayoutBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.apply {
            nameSenderTextView.text = "to $nameReceiver"
            messageTextView.text = message
        }
    }

    override fun unbindView(binding: MessageSendItemLayoutBinding) {
        super.unbindView(binding)
        binding.apply {
            nameSenderTextView.text = null
            messageTextView.text = null
        }
    }


}