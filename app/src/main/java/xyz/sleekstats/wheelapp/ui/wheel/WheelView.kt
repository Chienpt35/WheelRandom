package xyz.sleekstats.wheelapp.ui.wheel

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import xyz.sleekstats.wheelapp.R
import xyz.sleekstats.wheelapp.model.WheelChoice
import xyz.sleekstats.wheelapp.util.ColorUtils


class WheelView(context: Context, attributeSet: AttributeSet?) : AppCompatImageView(context, attributeSet) {

    private val colorUtils = ColorUtils()
    private val wheelTextSize = resources.getDimension(R.dimen.wheelTextSize)
    private val wheelBorder = resources.getInteger(R.integer.wheelBorder)

    //Draw wheel based on width of screen and colors given
    fun drawWheel(choices: List<WheelChoice>, screenWidth: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(screenWidth, screenWidth, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        val rectFull = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())

        val rectF = RectF(wheelBorder.toFloat(), wheelBorder.toFloat(), bitmap.width.toFloat() - wheelBorder.toFloat(), bitmap.height.toFloat() - wheelBorder.toFloat())

        createBitmapArrow(rectF)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#404040")
        canvas.drawArc(rectFull, 0f, 360f, true, paint)

        var startAngle = 0f
        val angleDegreesPerSection: Float = WheelActivity.DEGREES_IN_CIRCLE / choices.size

        choices.forEach {
            paint.color = it.colorIndex
            canvas.drawArc(rectF, startAngle, angleDegreesPerSection, true, paint)

            val endAngle = startAngle + angleDegreesPerSection
            val medianAngle = (startAngle + endAngle) / 2

            drawChoiceText(it.text, canvas, paint, medianAngle, angleDegreesPerSection)

            startAngle = endAngle
        }
        setImageBitmap(bitmap)
        return bitmap
    }

    private fun createBitmapArrow(rectF: RectF) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_wheel_arrow_black_24dp)

        val bitmapArrow = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)

        val canvasArrow = Canvas(bitmapArrow)

        val paintArrow = Paint(Paint.ANTI_ALIAS_FLAG)
        paintArrow.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

        canvasArrow.drawBitmap(bitmapArrow, 0f, 0f, paintArrow)
    }

    //Create text for choice around the center and along the angle of its section of the wheel
    private fun drawChoiceText(choiceText: String, canvas: Canvas, paint: Paint, medianAngle: Float, angleDegreesPerSection: Float) {
        paint.color = colorUtils.wheelToTextColorsMap[paint.color] ?: Color.WHITE
//        when {
//            choiceText.length < 10 -> paint.textSize = wheelTextSize
//            choiceText.length < 15 -> paint.textSize = wheelTextSize * .75f
//            else -> paint.textSize = wheelTextSize * 10/choiceText.length
//        }

        val rect = Rect()
        paint.getTextBounds(choiceText, 0, choiceText.length, rect)

        canvas.save()
        canvas.rotate(
            medianAngle,
            canvas.width / 2f,
            canvas.height / 2f
        )
        canvas.drawText(
            choiceText,
            canvas.width - 3f*wheelBorder,
            canvas.height / 2f,
            paint
        )
        canvas.restore()
    }
}