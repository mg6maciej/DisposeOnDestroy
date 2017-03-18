# DisposeOnDestroy
RxJava2 helper to dispose Disposables before leaking stuff

## Usage

```
Observable::disposeOnDestroy(Activity)
Single::disposeOnDestroy(Activity)
Maybe::disposeOnDestroy(Activity)
Completable::disposeOnDestroy(Activity)
```

```kotlin
class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity)
        makeApiCall()
    }

    private fun makeApiCall() {
        myRetrofitApi.call()
                .subscribeOn(io())
                .observeOn(mainThread())
                .disposeOnDestroy(this)
                .subscribe(this::displayResults, this::displayError)
    }

    // ...
}
```

Also works with `Fragment`s from support-v4.

```
Observable::disposeOnDestroyView(Fragment)
Single::disposeOnDestroyView(Fragment)
Maybe::disposeOnDestroyView(Fragment)
Completable::disposeOnDestroyView(Fragment)
```

```kotlin
class MyFragment : Fragment() {

    // ...

    private fun onSearchClick(query: String) {
        searchApi.call(query)
                .subscribeOn(io())
                .observeOn(mainThread())
                .disposeOnDestroyView(this)
                .subscribe(this::displayResults, this::displayError)
    }

    // ...
}
```

See [demo module](https://github.com/mg6maciej/DisposeOnDestroy/tree/master/demo/src/main/java/pl/mg6/demo/disposeondestroy) for working examples
or [some random commit](https://github.com/mg6maciej/fluffy-octo-rotary-phone/commit/86baeabec1dffa6794f9065901a9807e29a5190d) to see how to apply this little lib.

TODO: add examples with logic extracted into plain classes.

## Gradle

```groovy
dependencies {
    compile 'pl.mg6.rxjava2.disposeondestroy:core:0.1.0'
    compile 'pl.mg6.rxjava2.disposeondestroy:support:0.1.0'
}
```
