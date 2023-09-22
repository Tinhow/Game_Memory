package filho.walter.memory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import filho.walter.memory.Card
import filho.walter.memory.R

class MainActivity : AppCompatActivity() {

    private lateinit var buttonStart: Button
    private lateinit var gridLayout: GridLayout
    private lateinit var memoryGame: MemoryGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        buttonStart = findViewById(R.id.buttonStart)

        initializeGame()

        buttonStart.setOnClickListener {
            startNewGame()
        }
    }

    private fun initializeGame() {
        memoryGame = MemoryGame(this, gridLayout) {
            buttonStart.visibility = View.VISIBLE
        }
        memoryGame.initializeGame()
        memoryGame.assignImagesToImageViews()
        memoryGame.setupGridLayout()
    }

    private fun startNewGame() {
        buttonStart.visibility = View.GONE
        memoryGame.initializeGame()
        memoryGame.assignImagesToImageViews()
        memoryGame.setupGridLayout()
    }
}
