package org.team9432.scoutingapp.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.team9432.scoutingapp.io.json.MatchScoutingData
import qrcode.QRCode

object QRCodes {
    fun fromString(string: String): ImageBitmap {
        val test = QRCode.ofSquares()
        if (config.darkMode) {
            test.withGradientColor(0xFF356943.toInt(), 0xFF675A92.toInt())
            test.withBackgroundColor(0x000000)
        } else {
            test.withGradientColor(0xFF22462C.toInt(), 0xFF493D70.toInt())
            test.withBackgroundColor(0x000000)
        }

        test.withSize(10)
        test.withInnerSpacing(0)

        return convertImageByteArrayToBitmap(test.build(string).renderToBytes()).asImageBitmap()
    }

    private fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    }
}