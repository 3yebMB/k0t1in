package dev.m13d.k0t1in.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import dev.m13d.k0t1in.R
import dev.m13d.k0t1in.model.Colour
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "dd.MMM.yy HH:mm"

fun Date.format(): String = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(this)
fun Colour.getColorInt(context: Context): Int =
        ContextCompat.getColor(context, when (this) {
            Colour.WHITE -> R.color.color_white
            Colour.VIOLET -> R.color.color_violet
            Colour.YELLOW -> R.color.color_yello
            Colour.RED -> R.color.color_red
            Colour.PINK -> R.color.color_pink
            Colour.GREEN -> R.color.color_green
            Colour.BLUE -> R.color.color_blue
        })