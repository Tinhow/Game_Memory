package filho.walter.memory

data class Card(
    val id: Int,
    val imageResId: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false,
    var viewId: Int = -1
)
