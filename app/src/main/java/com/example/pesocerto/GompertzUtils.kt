package com.example.pesocerto

import org.apache.commons.math3.analysis.ParametricUnivariateFunction
import org.apache.commons.math3.fitting.CurveFitter
import org.apache.commons.math3.fitting.WeightedObservedPoint
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer
import kotlin.math.exp

fun ajustarGompertz(tData: DoubleArray, yData: DoubleArray): DoubleArray? {
    val pontos = tData.indices.map { i ->
        WeightedObservedPoint(1.0, tData[i], yData[i])
    }

    val funcaoGompertz = object : ParametricUnivariateFunction {
        override fun value(t: Double, params: DoubleArray): Double {
            val (a, b, c) = params
            return a * exp(-b * exp(-c * t))
        }

        override fun gradient(t: Double, params: DoubleArray): DoubleArray {
            val (a, b, c) = params
            val expCT = exp(-c * t)
            val expBexpCT = exp(-b * expCT)
            val da = expBexpCT
            val db = -a * expBexpCT * expCT
            val dc = a * b * t * expBexpCT * expCT
            return doubleArrayOf(da, db, dc)
        }
    }

    val initialGuess = doubleArrayOf(5.9183, 0.0476, 32.7461)
    val fitter = CurveFitter<ParametricUnivariateFunction>(LevenbergMarquardtOptimizer())
    pontos.forEach { fitter.addObservedPoint(it) }

    return try {
        fitter.fit(funcaoGompertz, initialGuess)
    } catch (e: Exception) {
        null
    }
}