package com.example.debtgo.ui.reviews

import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.debtgo.data.model.Review

class ReviewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración del contenedor principal
        val rootLayout = LinearLayout(this).apply {
            orientation = VERTICAL
            setPadding(16.dpToPx(), 24.dpToPx(), 16.dpToPx(), 0)
        }

        // Título
        val title = TextView(this).apply {
            text = "Reviews"
            textSize = 24f
            setTypeface(null, Typeface.BOLD)
        }
        rootLayout.addView(title)

        // RecyclerView
        val recycler = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@ReviewsActivity)
            adapter = ReviewsAdapter(getSampleReviews())
        }
        rootLayout.addView(recycler, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        setContentView(rootLayout)
    }

    private fun getSampleReviews(): List<Review> = listOf(
        Review("1", "Carlos López", 4.5f, "¡Excelente servicio!", "10 May 2024"),
        Review("2", "Ana Torres", 5f, "DebtGo salvó mi negocio", "8 May 2024")
    )

    private fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    class ReviewsAdapter(private val reviews: List<Review>) :
        RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            return ReviewViewHolder(ReviewItemView(parent.context))
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            holder.bind(reviews[position])
        }

        override fun getItemCount(): Int = reviews.size

        inner class ReviewViewHolder(private val view: ReviewItemView) :
            RecyclerView.ViewHolder(view) {
            fun bind(review: Review) {
                view.bind(review) // Llama al método bind de ReviewItemView
            }
        }
    }
}
