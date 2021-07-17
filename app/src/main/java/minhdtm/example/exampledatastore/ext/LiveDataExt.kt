package minhdtm.example.exampledatastore.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import minhdtm.example.exampledatastore.util.Event

fun <T> LiveData<T>.nonNullObserver(owner: LifecycleOwner, observer: (T) -> Unit) = this.observe(owner) {
    it?.let(observer)
}

fun <T> LiveData<Event<T>>.nonNullEventObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner) { it?.getContentIfNotHandled()?.let(observer) }
}