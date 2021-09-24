package com.entreprisecorp.projectinfo901.fastitem

import android.view.LayoutInflater
import android.view.ViewGroup
import com.entreprisecorp.projectinfo901.R
import com.entreprisecorp.projectinfo901.databinding.MessageSendItemLayoutBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MessageSendItem(
    val nameReceiver: String? = null,
    val message: String? = null,
    val isPrivate: Boolean = false

) : AbstractBindingItem<MessageSendItemLayoutBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): MessageSendItemLayoutBinding {
        return MessageSendItemLayoutBinding.inflate(inflater, parent, false)
    }

    override val type: Int = R.id.message_send_item

    override fun bindView(binding: MessageSendItemLayoutBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.apply {
            messageTextView.text = message
            if(isPrivate){
                card.setCardBackgroundColor(root.context.getColor(android.R.color.darker_gray))
                nameSenderTextView.text = "À $nameReceiver (privé)"
            }
            else {
                nameSenderTextView.text = "À $nameReceiver"
            }
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