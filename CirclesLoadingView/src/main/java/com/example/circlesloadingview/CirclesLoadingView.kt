package com.example.circlesloadingview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator

class CirclesLoadingView : View {

    companion object {
        val COLOR_BLUE = Color.parseColor("#4285F4")
        val COLOR_RED = Color.parseColor("#DB4437")
        val COLOR_YELLOW = Color.parseColor("#F4B400")
        val COLOR_GREEN = Color.parseColor("#0F9D58")

        /**
         * numbers of wave circle, default is four.
         */
        const val CIRCLE_COUNT = 4

        /**
         * the radius of circle
         */
        const val DEFAULT_CIRCLE_RADIUS = 20f

        /**
         * space between circle
         */
        const val DEFAULT_CIRCLE_MARGIN = 20f

        /**
         * animation distance from up and down for each circle
         */
        const val DEFAULT_ANIM_DISTANCE = 50f

        /**
         * animation duration for each circle up and down.
         */
        const val DEFAULT_ANIM_DURATION = 500L

        /**
         * delay time of each circle compared with the previous animation
         */
        const val DEFAULT_ANIM_DELAY = 150L

        /**
         * interpolator for circle's wave animation, default is [AccelerateInterpolator]
         */
        const val DEFAULT_ANIM_INTERPOLATOR = 0
    }

    /**
     * radius of circle
     */
    private var circleRadius = DEFAULT_CIRCLE_RADIUS

    private var circleCount = CIRCLE_COUNT

    private var circleMargin = DEFAULT_CIRCLE_MARGIN
    private var animDistance = DEFAULT_ANIM_DISTANCE

    private var animDuration: Long = DEFAULT_ANIM_DURATION

    private var animDelay: Long = DEFAULT_ANIM_DELAY

    private var animInterpolator: Int = DEFAULT_ANIM_INTERPOLATOR

    /**
     * all circle's color , which size should equal with [CIRCLE_COUNT]
     */
    private var colors = listOf(COLOR_BLUE, COLOR_RED, COLOR_YELLOW, COLOR_GREEN)

    /**
     * vertical offset for each circle animation, which size should equal with [CIRCLE_COUNT]
     */
    private val positions = MutableList(circleCount) {
        0f
    }

    /**
     * hold animations for all circle
     */
    private val animatorSet = AnimatorSet()

    /**
     * [Paint] to draw circles
     */
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Style.FILL
        color = COLOR_RED
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        val typedValue = context?.theme?.obtainStyledAttributes(attrs, R.styleable.CirclesLoadingView, 0, 0)
        typedValue?.let {
            circleCount = it.getInt(R.styleable.CirclesLoadingView_circleCount, this.circleCount)
            circleRadius = it.getDimension(R.styleable.CirclesLoadingView_circleRadius, this.circleRadius)
            circleMargin = it.getDimension(R.styleable.CirclesLoadingView_circleMargin, this.circleMargin)
            animDistance = it.getDimension(R.styleable.CirclesLoadingView_animDistance, this.animDistance)
            animDuration = it.getInt(R.styleable.CirclesLoadingView_animDuration, this.animDuration.toInt()).toLong()
            animDelay = it.getInt(R.styleable.CirclesLoadingView_animDelay, this.animDelay.toInt()).toLong()
            animInterpolator = it.getInt(R.styleable.CirclesLoadingView_animInterpolator, this.animInterpolator)
            it.recycle()
        }

        val animators = mutableListOf<Animator>()
        for (i in 0 until CIRCLE_COUNT) {
            animators.add(ObjectAnimator.ofFloat(-animDistance / 2f, animDistance / 2f).apply {
                this.duration = animDuration
                this.startDelay = i * animDelay
                this.repeatCount = INFINITE
                this.repeatMode = REVERSE
                this.interpolator = when (animInterpolator) {
                    0 -> AccelerateInterpolator()
                    1 -> DecelerateInterpolator()
                    2 -> AccelerateDecelerateInterpolator()
                    3 -> AnticipateInterpolator()
                    4 -> AnticipateOvershootInterpolator()
                    5 -> LinearInterpolator()
                    6 -> OvershootInterpolator()
                    else -> AccelerateDecelerateInterpolator()
                }
                this.addUpdateListener {
                    positions[i] = it.animatedValue as Float
                    invalidate()
                }
            })
        }
        animatorSet.playTogether(animators)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var startPoint = width / 2 - (CIRCLE_COUNT - 1) * (circleRadius + circleMargin / 2)
        for (i in 0 until CIRCLE_COUNT) {
            paint.color = colors[i % colors.size]
            canvas?.drawCircle(startPoint, height / 2f + positions[i], circleRadius, paint)
            startPoint += 2 * circleRadius + circleMargin
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animatorSet.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet.end()
    }

}