package id.com.android.laundry.controller

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


open class OnSwipeTouchListener(ctx: Context?) : View.OnTouchListener {
    private val gestureDetector: GestureDetector
    private var mHandler: Handler? = null

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        return gestureDetector.onTouchEvent(event)
    }

    var mAction: Runnable = object : Runnable {
        override fun run() {
            Log.d("Performing action...","")
            mHandler!!.postDelayed(this, 500)
        }
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }


        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > 100 && Math.abs(
                            velocityX
                        ) > 100
                    ) {
                        if (diffX > 0) {
                            onSwipeLeft()
                        } else {
                            onSwipeRight()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > 100 && Math.abs(
                        velocityY
                    ) > 100
                ) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }


    }

    open fun onSwipeRight() {}
    open fun onSwipeLeft() {}
    fun onSwipeTop() {}
    fun onSwipeBottom() {}

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }
}