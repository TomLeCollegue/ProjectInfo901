package com.entreprisecorp.projectinfo901.fastitem

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.entreprisecorp.projectinfo901.R
import com.entreprisecorp.projectinfo901.databinding.MessageItemLayoutBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class MessageItem(
    val nameSender: String? = null,
    val message: String? = null,
    val isPrivate: Boolean = false
) : AbstractBindingItem<MessageItemLayoutBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): MessageItemLayoutBinding {
        return MessageItemLayoutBinding.inflate(inflater, parent, false)
    }

    override val type: Int = R.id.message_item

    override fun bindView(binding: MessageItemLayoutBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.apply {
            messageTextView.text = message
            if(isPrivate){
                card.setCardBackgroundColor(root.context.getColor(android.R.color.holo_orange_light))
                nameSenderTextView.text = "$nameSender (privé)"
            }
            else {
                nameSenderTextView.text = nameSender
            }
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