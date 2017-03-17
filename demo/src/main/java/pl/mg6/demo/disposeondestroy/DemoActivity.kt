package pl.mg6.demo.disposeondestroy

import android.os.Bundle
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable.interval
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.demo_activity.*
import pl.mg6.rxjava2.disposeondestroy.disposeOnDestroy
import java.util.concurrent.TimeUnit.SECONDS

class DemoActivity : AppCompatActivity(), DemoFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity)
        startCounter()
        start.setOnClickListener { showDemoFragment() }
    }

    private fun startCounter() {
        interval(1L, 7L, SECONDS, mainThread())
                .doOnDispose { Log.i("DemoActivity", "counter disposed") }
                .disposeOnDestroy(this)
                .subscribe(this::updateCounter)
    }

    private fun updateCounter(value: Long) {
        Log.i("DemoActivity", "counter value: $value")
        counter.text = value.toString()
    }

    private fun showDemoFragment() {
        supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, DemoFragment())
                .commit()
    }

    override fun onCancel() {
        supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentById(R.id.container))
                .commit()
    }

    override fun onDeeper() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, BlackHoleFragment())
                .addToBackStack(null)
                .commit()
    }
}
