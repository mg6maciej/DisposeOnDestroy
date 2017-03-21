package pl.mg6.demo.disposeondestroy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Completable.complete
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.demo_fragment.*
import pl.mg6.rxjava2.disposeondestroy.disposeOnDestroyView
import java.util.concurrent.TimeUnit.SECONDS

class DemoFragment : Fragment() {

    interface Listener {

        fun onCancel()
        fun onDeeper()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.demo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startTimer()
        cancel.setOnClickListener { (activity as Listener).onCancel() }
        deeper.setOnClickListener { (activity as Listener).onDeeper() }
    }

    private fun startTimer() {
        complete().delay(3L, SECONDS, mainThread())
                .doOnDispose { Log.i("DemoFragment", "timer disposed") }
                .disposeOnDestroyView(this)
                .subscribe(this::onTimeout)
    }

    private fun onTimeout() {
        Log.i("DemoFragment", "disabling buttons")
        cancel.isEnabled = false
        deeper.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("DemoFragment", "after super.onDestroyView")
    }

    override fun onDestroy() {
        Log.i("DemoFragment", "before super.onDestroy")
        super.onDestroy()
    }
}
