package pilko.bliopo.klop.game.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import pilko.bliopo.klop.R
import kotlin.math.pow
import kotlin.math.sqrt

class CustomGameView(context: Context, attributes: AttributeSet) : View(context,attributes) {
    data class CstBall(
        var x: Float,
        var y: Float,
        val radius: Float,
        val paint: Paint,
        var spped: Float = 5f
    )

    data class CstBallRed(
        var x: Float,
        var y: Float,
        val radius: Float,
        var spped: Float = 5f,
        val id: Int
    )

    private lateinit var result: GameResult
    private val points = mutableListOf<CstBall>()
    private val balls = arrayListOf<CstBallRed>()
    private var ballRadius = 19f
    private var numbOfBalls = 0
    private var numbOfBallsRandom = 1
    private var poStartGame = false
    private var timering = 0
    private var listOfBalls = arrayListOf<CstBallRed>()
    private var ballPaint : Bitmap? = null
    private var ballPaintColor : Bitmap? = null
    private val listOfBallls = listOf(
        R.drawable.ball_blue,
        R.drawable.ball_green,
        R.drawable.ball_orange,
        R.drawable.ball_purple,
        R.drawable.ball_red,
        R.drawable.ball_yellow,
        R.drawable.ball_lightblue,
        R.drawable.ball_lightpurple
    )


    init {
        val rawBallBitmap = BitmapFactory.decodeResource(resources, R.drawable.ball_white)
        ballPaint = Bitmap.createScaledBitmap(rawBallBitmap, 50, 50, false)
        val rawBallBitmapColor =  BitmapFactory.decodeResource(resources, R.drawable.ball_orange)

        ballPaintColor = Bitmap.createScaledBitmap(rawBallBitmapColor, 50, 50, false)
    }

    fun startGame(resulter: GameResult, numbBalls:Int) {
        balls.clear()
        numbOfBallsRandom = numbBalls
        result = resulter
        poStartGame = true
        timering = 0
        val rawBallBitmapColor =  BitmapFactory.decodeResource(resources, listOfBallls.random())
        ballPaintColor=Bitmap.createScaledBitmap(rawBallBitmapColor, 50, 50, false)
    }

    @SuppressLint("DrawAllocation", "CanvasSize")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (points.isEmpty()) {
            generatePoints(canvas.width)
        }
        if (points.isNotEmpty()) {
            for (point in points) {
                canvas.drawBitmap(ballPaint!!,point.x - ballRadius, point.y - ballRadius, null)
            }
        }
        if (poStartGame) {
            if (balls.size < numbOfBallsRandom) {
                timering++
                if (timering % 8 == 0) balls.add(
                    CstBallRed(
                        width / 2f,
                        height * 0.04f,
                        ballRadius,
                        6f,
                        timering
                    )
                )
            } else poStartGame = false
        }

        if (balls.isNotEmpty()) {
            for (ball in balls) {
                canvas.drawBitmap(ballPaintColor!!,ball.x - ballRadius, ball.y - ballRadius, null)
                ball.y += ball.spped
            }
        }

        for (ball in balls) {
            for (point in points) {
                if (isBallCollidingWithPoint(point, ball)) {
                    if (ball.x > point.x) {
                        ball.x += 5
                    } else if (ball.x == point.x) {
                        if ((0..1).random() == 1) {
                            ball.x += 5
                        } else {
                            ball.x -= 5
                        }
                    } else {
                        ball.x -= 5
                    }
                }
            }
        }

        val targetY = height.toFloat()

        for (ball in balls) {
            if (ball.y >= targetY) {
                ball.spped = 0f
                if (!listOfBalls.contains(ball)) {
                    result.oneBall(ball.x)
                    listOfBalls.add(ball)
                }
            }
        }
        numbOfBalls = listOfBalls.size
        if (numbOfBalls >= numbOfBallsRandom) {
            numbOfBalls = 0
            result.endGame()
            balls.clear()
            listOfBalls.clear()
        }
        invalidate()
    }

    private fun generatePoints(ealWidth: Int) {
        val radius = 30f
        val paint = Paint().apply {
            color = Color.rgb(252, 225, 1)
        }
        val startX = ealWidth / 2f
        points.addAll(
            listOf(
                CstBall(startX, height * 0.1f, radius, paint),
                CstBall(startX - 50, height * 0.17f, radius, paint),
                CstBall(startX + 50, height * 0.17f, radius, paint),
                CstBall(startX, height * 0.25f, radius, paint),
                CstBall(startX - 100, height * 0.25f, radius, paint),
                CstBall(startX + 100, height * 0.25f, radius, paint),
                CstBall(startX - 150, height * 0.33f, radius, paint),
                CstBall(startX - 50, height * 0.33f, radius, paint),
                CstBall(startX + 50, height * 0.33f, radius, paint),
                CstBall(startX + 150, height * 0.33f, radius, paint),
                CstBall(startX, height * 0.42f, radius, paint),
                CstBall(startX - 100, height * 0.42f, radius, paint),
                CstBall(startX + 100, height * 0.42f, radius, paint),
                CstBall(startX - 200, height * 0.42f, radius, paint),
                CstBall(startX + 200, height * 0.42f, radius, paint),
                CstBall(startX - 50, height * 0.52f, radius, paint),
                CstBall(startX + 50, height * 0.52f, radius, paint),
                CstBall(startX - 150, height * 0.52f, radius, paint),
                CstBall(startX + 150, height * 0.52f, radius, paint),
                CstBall(startX - 250, height * 0.52f, radius, paint),
                CstBall(startX + 250, height * 0.52f, radius, paint),
                CstBall(startX, height * 0.63f, radius, paint),
                CstBall(startX + 100, height * 0.63f, radius, paint),
                CstBall(startX + 200, height * 0.63f, radius, paint),
                CstBall(startX + 300, height * 0.63f, radius, paint),
                CstBall(startX - 100, height * 0.63f, radius, paint),
                CstBall(startX - 200, height * 0.63f, radius, paint),
                CstBall(startX - 300, height * 0.63f, radius, paint),
                CstBall(startX + 50, height * 0.75f, radius, paint),
                CstBall(startX + 150, height * 0.75f, radius, paint),
                CstBall(startX + 250, height * 0.75f, radius, paint),
                CstBall(startX + 350, height * 0.75f, radius, paint),
                CstBall(startX - 150, height * 0.75f, radius, paint),
                CstBall(startX - 250, height * 0.75f, radius, paint),
                CstBall(startX - 350, height * 0.75f, radius, paint),
                CstBall(startX - 50, height * 0.75f, radius, paint),
                CstBall(startX + 100, height * 0.88f, radius, paint),
                CstBall(startX + 200, height * 0.88f, radius, paint),
                CstBall(startX + 300, height * 0.88f, radius, paint),
                CstBall(startX + 400, height * 0.88f, radius, paint),
                CstBall(startX - 100, height * 0.88f, radius, paint),
                CstBall(startX - 200, height * 0.88f, radius, paint),
                CstBall(startX - 300, height * 0.88f, radius, paint),
                CstBall(startX - 400, height * 0.88f, radius, paint),
                CstBall(startX, height * 0.88f, radius, paint)
            )
        )
    }

    private fun isBallCollidingWithPoint(point: CstBall, ball: CstBallRed): Boolean {
        val distance = sqrt((point.x - ball.x).pow(2) + (point.y - ball.y).pow(2))
        return distance <= point.radius + ballRadius
    }
}

interface GameResult{
    fun endGame()
    fun oneBall(posBall:Float)
}
