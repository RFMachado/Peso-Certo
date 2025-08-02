package com.example.pesocerto

import org.apache.commons.math3.analysis.ParametricUnivariateFunction
import org.apache.commons.math3.fitting.CurveFitter
import org.apache.commons.math3.fitting.WeightedObservedPoint
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer
import kotlin.math.exp

fun ajustarGompertz(tData: DoubleArray, yData: DoubleArray): Triple<Double, Double, Double>? {
    // Normalização de tData
    val tMin = tData.minOrNull() ?: return null
    val tMax = tData.maxOrNull() ?: return null
    val tNorm = tData.map { (it - tMin) / (tMax - tMin) }

    val pontos = tNorm.indices.map { i ->
        WeightedObservedPoint(1.0, tNorm[i], yData[i])
    }

    // Função de Gompertz paramétrica
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
            val db = -a * expCT * expBexpCT
            val dc = a * b * t * expCT * expCT * expBexpCT
            return doubleArrayOf(da, db, dc)
        }
    }

    // Chute inicial adequado após testes
    val initialGuess = doubleArrayOf(5.9, -0.5, 0.5)

    val fitter = CurveFitter<ParametricUnivariateFunction>(LevenbergMarquardtOptimizer())
    pontos.forEach { fitter.addObservedPoint(it) }

    return try {
        val result = fitter.fit(funcaoGompertz, initialGuess)
        Triple(result[0], result[1], result[2]) // a, b, c
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}