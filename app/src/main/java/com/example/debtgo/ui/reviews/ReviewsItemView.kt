package com.example.debtgo.ui.reviews

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.example.debtgo.data.model.Review

class ReviewItemView(context: Context) : LinearLayout(context) {
    // Declara componentes como propiedades
    private val userName: TextView
    private val ratingBar: RatingBar
    private val comment: TextView
    private val date: TextView

    init {
        orientation = VERTICAL
        setPadding(32.dpToPx(), 16.dpToPx(), 32.dpToPx(), 16.dpToPx())

        // Inicializa componentes
        userName = TextView(context).apply {
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
        }

        ratingBar = RatingBar(context, null, android.R.attr.ratingBarStyleSmall).apply {
            isEnabled = false
            stepSize = 0.5f
        }

        comment = TextView(context).apply {
            textSize = 14f
            setPadding(0, 8.dpToPx(), 0, 0)
        }

        date = TextView(context).apply {
            textSize = 12f
            setTextColor(Color.GRAY)
        }

        // Añadir vistas al layout
        addView(userName)
        addView(ratingBar)
        addView(comment)
        addView(date)
    }

    // Función para actualizar datos
    fun bind(review: Review) {
        userName.text = review.userName
        ratingBar.rating = review.rating
        comment.text = review.comment
        date.text = review.date
    }

    // Extensión para convertir dp a px
    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}