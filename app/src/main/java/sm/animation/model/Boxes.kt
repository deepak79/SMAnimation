package sm.animation.model

import android.graphics.drawable.Drawable
//Model class for Boxes
data class Boxes(
    val image: Drawable,
    val shoe_type: String,
    val shoe_name: String,
    val shoe_subtitle: String,
    val shoe_price: String,
    val shoe_rating: String
)