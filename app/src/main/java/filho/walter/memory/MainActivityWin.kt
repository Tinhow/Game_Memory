package filho.walter.memory

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivityWin : AppCompatActivity() {

    private lateinit var buttonNewGame: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_win)

        buttonNewGame = findViewById(R.id.buttonNewGame)

        buttonNewGame.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
