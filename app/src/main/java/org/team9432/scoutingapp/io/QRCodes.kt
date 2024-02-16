package org.team9432.scoutingapp.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import qrcode.QRCode

object QRCodes {
    fun fromString(string: String): ImageBitmap {
        val test = QRCode.ofSquares()

        test.withSize(10)
        test.withInnerSpacing(0)

        return convertImageByteArrayToBitmap(test.build(string).renderToBytes()).asImageBitmap()
    }

    private fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    }
}