package sm

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class Box : View {
    private var paint = Paint()
    private var paint1 = Paint()
    var topRect = Rect()
    var bottomRect = Rect()
    var i = 10

    private fun init(set: AttributeSet?) {
        paint.color = Color.BLACK
        paint1.color = Color.BLUE
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.RED)
        topRect.set(10, i, width - 10, height / 2)
        bottomRect.set(10, height / 2, width - 10, height - 10)
        canvas.drawRect(topRect, paint)
        canvas.drawRect(bottomRect, paint1)
        i += 5
        if (i > height - 10) {

        }
        invalidate()
    }
}
