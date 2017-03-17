package pl.mg6.demo.disposeondestroy

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}
