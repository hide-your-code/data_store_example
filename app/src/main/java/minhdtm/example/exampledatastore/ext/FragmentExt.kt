package minhdtm.example.exampledatastore.ext

import androidx.fragment.app.Fragment
import minhdtm.example.exampledatastore.util.AutoClearValue

fun <T : Any> Fragment.autoClear(): AutoClearValue<T> = AutoClearValue(this)
