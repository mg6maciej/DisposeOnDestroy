package pl.mg6.rxjava2.disposeondestroy

import android.app.Activity
import io.reactivex.*
import io.reactivex.subjects.CompletableSubject

fun <T> Observable<T>.disposeOnDestroy(activity: Activity): Observable<T> = doOnSubscribe(activity.disposeOnDestroy())

fun <T> Single<T>.disposeOnDestroy(activity: Activity): Single<T> = doOnSubscribe(activity.disposeOnDestroy())

fun <T> Maybe<T>.disposeOnDestroy(activity: Activity): Maybe<T> = doOnSubscribe(activity.disposeOnDestroy())

fun Completable.disposeOnDestroy(activity: Activity): Completable = doOnSubscribe(activity.disposeOnDestroy())

private fun Activity.disposeOnDestroy() = disposeOnComplete(onDestroyComplete())

private fun Activity.onDestroyComplete(): Completable {
    val subject = CompletableSubject.create()
    application.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {
        override fun onActivityDestroyed(activity: Activity) {
            if (this@onDestroyComplete == activity) {
                subject.onComplete()
                application.unregisterActivityLifecycleCallbacks(this)
            }
        }
    })
    return subject
}
