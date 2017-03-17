package pl.mg6.rxjava2.disposeondestroy

import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

fun disposeOnComplete(completable: Completable) = Consumer<Disposable> { disposable ->
    completable.subscribe(disposable::dispose)
}
