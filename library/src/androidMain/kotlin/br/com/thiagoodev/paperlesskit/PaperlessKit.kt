package br.com.thiagoodev.paperlesskit

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc


actual class PaperlessKit : PaperlessKitInterface {
    lateinit var cameraView: CameraBridgeViewBase
    lateinit var context: Context

    init {
        initializeOpenCV()
    }

    fun initializeOpenCV() {
        val isInitialize: Boolean = OpenCVLoader.initLocal()
        if(isInitialize) {
            log("OpenCV is initialized")
        } else {
            log("OpenvCV is not initialized")
        }
    }

    actual override suspend fun scan(options: Options): PDF {
        cameraView = CameraBridgeViewBase(context, CameraBridgeViewBase.CAMERA_ID_ANY)

        TODO("Not yet implemented")
    }

    /**
     * Detects a document in the given input bitmap. If a document is found, it aligns and returns
     * the transformed document. Otherwise, it returns the original image.
     *
     * @param inputBitmap The input image as a [Bitmap].
     * @return A [Bitmap] containing the aligned document or the original image if no document is found.
     */
    fun detectDocument(inputBitmap: Bitmap): Bitmap {
        val inputMat = Mat()
        Utils.bitmapToMat(inputBitmap, inputMat)

        val processedMat: Mat = preprocessImage(inputMat)
        val largestQuad: MatOfPoint2f? = findLargestQuadrilateral(processedMat)

        return if (largestQuad != null) {
            warpDocument(inputMat, largestQuad)
        } else {
            inputBitmap
        }
    }

    /**
     * Preprocesses the input image by converting it to grayscale, applying Gaussian blur,
     * and detecting edges using the Canny algorithm.
     *
     * @param inputMat The input image as a [Mat].
     * @return A [Mat] containing the edges detected in the image.
     */
    private fun preprocessImage(inputMat: Mat): Mat {
        val grayMat = Mat()

        Imgproc.cvtColor(inputMat, grayMat, Imgproc.COLOR_RGBA2GRAY)

        Imgproc.GaussianBlur(grayMat, grayMat, Size(5.0, 5.0), 0.0)

        val edges = Mat()
        Imgproc.Canny(grayMat, edges, 75.0, 200.0)

        return edges
    }

    /**
     * Identifies the largest quadrilateral in the provided edge-detected image.
     * This quadrilateral is assumed to represent the document.
     *
     * @param edges A [Mat] containing the edge-detected image.
     * @return A [MatOfPoint2f] representing the largest detected quadrilateral, or null if none is found.
     */
    private fun findLargestQuadrilateral(edges: Mat): MatOfPoint2f? {
        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()

        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE)

        var largestQuad: MatOfPoint2f? = null
        var maxArea = 0.0

        for (contour in contours) {
            val contourArea = Imgproc.contourArea(contour)

            if (contourArea > 1000) {
                val approx = MatOfPoint2f()
                val contour2f = MatOfPoint2f(*contour.toArray())

                Imgproc.approxPolyDP(contour2f, approx, 0.02 * Imgproc.arcLength(contour2f, true), true)

                if (approx.total() == 4L && contourArea > maxArea) {
                    largestQuad = approx
                    maxArea = contourArea
                }
            }
        }

        return largestQuad
    }

    /**
     * Applies a perspective transformation to the input image to align and isolate
     * the detected quadrilateral (document). The resulting document is returned as a bitmap.
     *
     * @param inputMat The original input image as a [Mat].
     * @param quad A [MatOfPoint2f] representing the detected quadrilateral.
     * @return A [Bitmap] containing the aligned and isolated document.
     */
    private fun warpDocument(inputMat: Mat, quad: MatOfPoint2f): Bitmap {
        val quadPoints = quad.toArray()

        val targetPoints: Array<Point> = arrayOf(
            Point(0.0, 0.0),
            Point(inputMat.cols().toDouble(), 0.0),
            Point(inputMat.cols().toDouble(), inputMat.rows().toDouble()),
            Point(0.0, inputMat.rows().toDouble())
        )

        val perspectiveTransform: Mat = Imgproc.getPerspectiveTransform(MatOfPoint2f(*quadPoints), MatOfPoint2f(*targetPoints))
        val transformedMat = Mat()

        Imgproc.warpPerspective(inputMat, transformedMat, perspectiveTransform, inputMat.size())

        val resultBitmap = Bitmap.createBitmap(transformedMat.cols(), transformedMat.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(transformedMat, resultBitmap)

        return resultBitmap
    }

    actual override suspend fun pick(): PDF {
        TODO("Not yet implemented")
    }

    actual override suspend fun open(pdf: PDF) {
        TODO("Not yet implemented")
    }

    actual override suspend fun open(path: String) {
        TODO("Not yet implemented")
    }

    fun log(message: String) {
        Log.println(Log.INFO, "PaperlessKit", message)
    }
}