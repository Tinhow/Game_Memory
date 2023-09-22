package filho.walter.memory

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import filho.walter.memory.Card
import filho.walter.memory.R

class MemoryGame(
    private val context: Context,
    private val gridLayout: GridLayout,
    private val onGameComplete: () -> Unit
) {

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
    private val handler = Handler(Looper.getMainLooper())

    init {
        initializeGame()
    }

    fun initializeGame() {
        cardList.clear()
        imageResources.shuffle()

        for (i in 0 until 16) {
            val card = Card(i, imageResources[i % imageResources.size])
            cardList.add(card)
        }
        cardList.shuffle()
    }

    fun assignImagesToImageViews() {
        for (i in 1..16) {
            val imageViewId = context.resources.getIdentifier("imageView$i", "id", context.packageName)
            val imageView = gridLayout.findViewById<ImageView>(imageViewId)
            val card = cardList[i - 1]

            if (card.isFaceUp) {
                imageView.setImageResource(card.imageResId)
            } else {
                imageView.setImageResource(R.drawable.costas_cartas)
            }

            imageView.setOnClickListener {
                onCardClicked(card, imageView)
            }
        }
    }

    private fun onCardClicked(clickedCard: Card, imageView: ImageView) {
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
                    checkIfAllCardsMatched()
                } else {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        flippedCards.forEach {
                            it.isFaceUp = false
                            val cardImageView = findCardImageViewById(it.id)
                            cardImageView?.setImageResource(R.drawable.costas_cartas)
                        }
                        flippedCards.clear()
                        setupGridLayout()
                    }, 500)
                }
            }
        }
    }

    private fun findCardImageViewById(cardId: Int): ImageView? {
        val imageViewId = context.resources.getIdentifier("imageView$cardId", "id", context.packageName)
        return gridLayout.findViewById(imageViewId)
    }

    fun setupGridLayout() {
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

        checkIfAllCardsMatched()
    }

    private fun checkIfAllCardsMatched() {
        if (matchedPairs == cardList.size / 2) {
            // Todas as cartas estão emparelhadas, o jogador ganhou.
            val intent = Intent(context, MainActivityWin::class.java)
            context.startActivity(intent)
        } else if (flippedCards.size == 2 && !flippedCards[0].isMatched && !flippedCards[1].isMatched) {
            // O jogador perdeu, inicie a tela de derrota.
            val intent = Intent(context, MainActivityLose::class.java)
            context.startActivity(intent)
        }
    }


}
