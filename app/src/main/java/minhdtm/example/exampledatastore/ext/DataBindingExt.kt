package minhdtm.example.exampledatastore.ext

import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}
