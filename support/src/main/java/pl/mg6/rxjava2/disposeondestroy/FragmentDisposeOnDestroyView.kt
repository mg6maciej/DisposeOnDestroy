package pl.mg6.rxjava2.disposeondestroy

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.CompletableSubject

fun <T> Observable<T>.disposeOnDestroyView(fragment: Fragment): Observable<T> = doOnSubscribe(fragment.disposeOnDestroyView())

fun <T> Single<T>.disposeOnDestroyView(fragment: Fragment): Single<T> = doOnSubscribe(fragment.disposeOnDestroyView())

fun <T> Maybe<T>.disposeOnDestroyView(fragment: Fragment): Maybe<T> = doOnSubscribe(fragment.disposeOnDestroyView())

fun Completable.disposeOnDestroyView(fragment: Fragment): Completable = doOnSubscribe(fragment.disposeOnDestroyView())

private fun Fragment.disposeOnDestroyView() = disposeOnComplete(onDestroyViewComplete())

private fun Fragment.onDestroyViewComplete(): Completable {
    val subject = CompletableSubject.create()
    fragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewDestroyed(fm: FragmentManager, fragment: Fragment) {
            if (this@onDestroyViewComplete == fragment) {
                subject.onComplete()
                fm.unregisterFragmentLifecycleCallbacks(this)
            }
        }
    }, true)
    return subject
}
