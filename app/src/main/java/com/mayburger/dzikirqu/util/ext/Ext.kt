package com.mayburger.dzikirqu.util.ext

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*


fun Int.toStringJson(mContext: Context): String {
    val `is`: InputStream = mContext.resources.openRawResource(this)
    val writer: Writer = StringWriter()
    val buffer = CharArray(1024)
    `is`.use { `is` ->
        val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
        var n: Int
        while (reader.read(buffer).also { n = it } != -1) {
            writer.write(buffer, 0, n)
        }
    }
    return writer.toString()
}

fun View.setOnClickAnimate(runnable:Runnable){
    val view = this
    view.setOnTouchListener { p0, p1 ->
        when (p1?.action) {
            MotionEvent.ACTION_DOWN -> {
                val scaleDownX = ObjectAnimator.ofFloat(
                    view,
                    "scaleX", 0.9f
                );
                val scaleDownY = ObjectAnimator.ofFloat(
                    view,
                    "scaleY", 0.9f
                );
                scaleDownX.duration = 150;
                scaleDownY.duration = 150;
                val scaleDown = AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);
                scaleDown.start();
            }
            MotionEvent.ACTION_UP -> {
                val scaleDownX2 = ObjectAnimator.ofFloat(
                    view, "scaleX", 1f
                );
                val scaleDownY2 = ObjectAnimator.ofFloat(
                    view, "scaleY", 1f
                );
                scaleDownX2.duration = 300;
                scaleDownY2.duration = 300;

                val scaleDown2 = AnimatorSet();
                scaleDown2.play(scaleDownX2).with(scaleDownY2);
                scaleDown2.start();
                runnable.run()
                view.performClick()
            }
            MotionEvent.ACTION_CANCEL -> {
                val scaleDownX2 = ObjectAnimator.ofFloat(
                    view, "scaleX", 1f
                );
                val scaleDownY2 = ObjectAnimator.ofFloat(
                    view, "scaleY", 1f
                );
                scaleDownX2.duration = 300;
                scaleDownY2.duration = 300;

                val scaleDown2 = AnimatorSet();
                scaleDown2.play(scaleDownX2).with(scaleDownY2);
                scaleDown2.start();
            }
        }
        true
    }
}

fun <T> String.jsonToArraylistObject():ArrayList<T>{
    val json = this
    return Gson().fromJson(json, object: TypeToken<ArrayList<T>>(){}.type)
}

fun <T> LiveData<T>.getObserverValue(owner: LifecycleOwner):T?{
    var returnValue:T? = null
    this.observe(owner, {
        returnValue = it
    })
    return returnValue
}

fun BottomSheetBehavior<LinearLayout>.hide(){
    this.state = BottomSheetBehavior.STATE_HIDDEN
}
fun BottomSheetBehavior<LinearLayout>.collapse(){
    this.state = BottomSheetBehavior.STATE_COLLAPSED
}
fun BottomSheetBehavior<LinearLayout>.isShowing():Boolean{
    return (this.state == BottomSheetBehavior.STATE_COLLAPSED || this.state == BottomSheetBehavior.STATE_EXPANDED)
}
fun BottomSheetBehavior<LinearLayout>.show(){
    this.state = BottomSheetBehavior.STATE_EXPANDED
}



