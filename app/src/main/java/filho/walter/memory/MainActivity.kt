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
    private val imageResources = arrayOf(
        R.drawable.a1,
        R.drawable.a2,
        R.drawable.a3,
        R.drawable.a4,
        R.drawable.a5,
        R.drawable.a6,
        R.drawable.a7,
        R.drawable.a8
    )

    private val cardList = mutableListOf<Card>()
    private val flippedCards = mutableListOf<Card>()
    private var matchedPairs = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        buttonStart = findViewById(R.id.buttonStart)

        initializeGame()
        assignImagesToImageViews(gridLayout)
        setupGridLayout(gridLayout)

        buttonStart.setOnClickListener {
            buttonStart.visibility = View.GONE
            initializeGame()
            assignImagesToImageViews(gridLayout)
            setupGridLayout(gridLayout)
        }
    }

    private fun initializeGame() {
        cardList.clear()
        imageResources.shuffle()

        for (i in 0 until 16) {
            val card = Card(i, imageResources[i % imageResources.size])
            cardList.add(card)
        }
        cardList.shuffle()
    }

    private fun checkIfAllCardsMatched(): Boolean {
        return matchedPairs == cardList.size / 2
    }

    private fun assignImagesToImageViews(gridLayout: GridLayout) {
        for (i in 1..16) {
            val imageViewId = resources.getIdentifier("imageView$i", "id", packageName)
            val imageView = findViewById<ImageView>(imageViewId)
            val card = cardList[i - 1]

            if (card.isFaceUp) {
                imageView.setImageResource(card.imageResId)
            } else {
                imageView.setImageResource(R.drawable.costas_cartas)
            }

            imageView.setOnClickListener {
                onCardClicked(card, imageView, gridLayout)
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    private fun onCardClicked(clickedCard: Card, imageView: ImageView, gridLayout: GridLayout) {
        if (!clickedCard.isFaceUp && flippedCards.size < 2) {
            clickedCard.isFaceUp = true
            imageView.setImageResource(clickedCard.imageResId)
            flippedCards.add(clickedCard)

            if (flippedCards.size == 2) {
                val (card1, card2) = flippedCards

                if (card1.imageResId == card2.imageResId) {
                    card1.isMatched = true
                    card2.isMatched = true
                    flippedCards.clear()

                    matchedPairs++
                    if (checkIfAllCardsMatched()) {
                        // Implemente a lógica de vitória aqui
                    }
                } else {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        flippedCards.forEach {
                            it.isFaceUp = false
                            val cardImageView = findCardImageViewById(it.id)
                            cardImageView?.setImageResource(R.drawable.costas_cartas)
                        }
                        flippedCards.clear()
                        setupGridLayout(gridLayout)
                    }, 100)
                }
            }
        }
    }

    private fun findCardImageViewById(cardId: Int): ImageView? {
        val imageViewId = resources.getIdentifier("imageView$cardId", "id", packageName)
        return findViewById(imageViewId)
    }

    private fun setupGridLayout(gridLayout: GridLayout) {
        for (i in 1..16) {
            val imageView = findCardImageViewById(i)
            if (imageView != null) {
                val card = cardList[i - 1]

                if (card.isFaceUp) {
                    imageView.setImageResource(card.imageResId)
                } else {
                    imageView.setImageResource(R.drawable.costas_cartas)
                }
            }
        }
    }
}
