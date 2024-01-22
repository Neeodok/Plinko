package pilko.bliopo.klop.game.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import pilko.bliopo.klop.databinding.FragmentGameBinding
import pilko.bliopo.klop.game.view.GameResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Game : Fragment(), GameResult {
    private lateinit var game: FragmentGameBinding
    private var balanceGame = 1000
    private var megaWin = false
    private var numbBalls = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        game = FragmentGameBinding.inflate(inflater)
        return game.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        balanceGame = Hawk.get("BalanceValue",1000)
        game.balanceText.text = balanceGame.toString()
        game.btnStartGame.setOnClickListener {
            if (numbBalls>0) {
                val costsGame = numbBalls*10
                if (balanceGame>=costsGame) {
                    balanceGame-=costsGame
                    game.gameView.startGame(this,numbBalls)
                    game.btnStartGame.alpha = 0.71f
                    game.btnStartGame.isEnabled = false
                    game.balanceText.text = balanceGame.toString()
                } else {
                    Toast.makeText(context,"Not enough balance",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context,"Need more balls",Toast.LENGTH_SHORT).show()
            }
        }
        game.imgMinus.setOnClickListener {
            if (numbBalls>0) {
                numbBalls--
            }
            game.tvNumbBalls.text = numbBalls.toString()
        }
        game.imgPlus.setOnClickListener {
            numbBalls++
            game.tvNumbBalls.text = numbBalls.toString()
        }
    }

    override fun endGame() {
        if (megaWin) {
            lifecycleScope.launch {
                game.imgMegaWin.visibility = View.VISIBLE
                game.imgMegaWin.startAnimation(ScaleAnimation(0.8f,1.3f,0.8f,1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 700
                    repeatCount = 0
                    interpolator = OvershootInterpolator()
                })
                delay(2500)
                balanceGame+=500
                game.balanceText.text = balanceGame.toString()
                game.imgMegaWin.visibility = View.GONE
                game.btnStartGame.alpha = 1f
                game.btnStartGame.isEnabled = true
                megaWin=false
            }
        } else {
            if ((0..3).random()==0) {
                lifecycleScope.launch {
                    game.imgEpicWin.visibility = View.VISIBLE
                    game.imgEpicWin.startAnimation(ScaleAnimation(0.8f,1.3f,0.8f,1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
                        duration = 700
                        repeatCount = 0
                        interpolator = OvershootInterpolator()
                    })
                    game.gameView.startGame(this@Game,15)
                    delay(2500)
                    game.imgEpicWin.visibility = View.GONE
                    game.btnStartGame.alpha = 1f
                    game.btnStartGame.isEnabled = true
                }
            } else {
                game.btnStartGame.alpha = 1f
                game.btnStartGame.isEnabled = true
            }
        }
    }

    override fun oneBall(posBall: Float) {
        val width = game.root.width
        when(posBall.toInt()) {
            in 0..width/5 -> {winOpened(50,game.imgMaxLeft)
                megaWin=true}
            in width/5..width*2/5 -> {winOpened(10,game.imgModdleLeft2)}
            in width*2/5..width*3/5 -> {winOpened(5,game.imgModdleLeft)}
            in width*3/5..width*4/5 -> {winOpened(10,game.imgMiddleRight2)}
            in width*4/5..width -> {
                winOpened(50,game.imgMaxRight)
                megaWin = true}
        }

    }

    private fun winOpened(valWin:Int,imgForAnim: ImageView) {
        balanceGame+=valWin
        game.balanceText.text = balanceGame.toString()
        imgForAnim.clearAnimation()
        imgForAnim.startAnimation(ScaleAnimation(1f,1.3f,1f,1.3f, imgForAnim.width/2f,imgForAnim.height/2f).apply {
            duration = 200
            repeatCount = 0
            fillAfter = false
            interpolator = OvershootInterpolator()
        })
    }

    override fun onPause() {
        super.onPause()
        Hawk.put("BalanceValue",balanceGame)
    }
}
