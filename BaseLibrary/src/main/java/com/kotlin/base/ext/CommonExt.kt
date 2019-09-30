package com.kotlin.base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.jakewharton.rxbinding2.view.RxView
import com.kotlin.base.data.protocol.BaseBean
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.*
import com.kotlin.base.widgets.DefaultTextWatcher
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//Kotlin通用扩展


/*
    扩展Observable执行
 */
fun <T> Observable<T>.execute(
    subscriber: BaseSubscriber<T>,
    lifecycleProvider: LifecycleProvider<*>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribe(subscriber)
}

/*
    扩展Observable执行,不用mvp时使用
 */
fun <T> Observable<T>.execute(
    subscriber: SimpleSubscriber<T>,
    lifecycleProvider: LifecycleProvider<*>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
//            .timeout(30,TimeUnit.SECONDS)    //重连间隔时间
//            .retry(5)                          //重连次数
        .subscribe(subscriber)
}


/*
    扩展数据转换
 */
fun Observable<BaseBean>.convertBaseBean(): Observable<String> {
    return this.flatMap(BaseBeanFunc())
}

/*
    扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}

///*
//    扩展数据转换Test
// */
//fun <T> Observable<BaseResp<BaseRefreshInfo<T>>>.convertTest(): Observable<T> {
//    return this.flatMap(BaseTestFunc())
//}


/*
    扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

/*
    扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/*
    扩展Button可用性
 */
fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
//    GlideUtils.loadUrlImage(context, url, this)
}


/*
    扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

// 按钮除第一次延时2秒响应
fun View.throttleFirst(block: () -> Unit): Disposable {
    return RxView.clicks(this)
        .throttleFirst(2, TimeUnit.SECONDS)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe { block() }
}


//使用rxjava 代替Handler 延迟发送消息
fun delayTime(delayTime: Long, block: () -> Unit) {
     Single.timer(delayTime, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally { block() }
        .subscribe()
}

