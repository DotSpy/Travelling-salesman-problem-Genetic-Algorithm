package view.genetic

import view.Plot
import view.Population
import view.Reproduction
import view.model.Point2D
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class FitnessFunction(
    private val mutateChance: Double,
    private val crossoverProbability: Double
) {

    fun fit(chromosomes: List<Chromosome>): Population =
        Function().produceNextGeneration(chromosomes, mutateChance, crossoverProbability)

    private class Function {

        private val reproductionTable = mutableListOf<Reproduction>()
        private var middleValue = 0.0
        private var sumOfFitness = 0.0
        private var bestChromosome: Chromosome? = null

        fun produceNextGeneration(
            chromosomes: List<Chromosome>,
            mutateChance: Double,
            crossoverProbability: Double
        ): Population {
            bestChromosome = null
            for (chromosome in chromosomes) {
                var distance = 0.0
                for (i in chromosome.nodes.indices) {
                    if (i == chromosome.nodes.lastIndex) {
                        val firstNode = Plot.nodes.getValue(chromosome.nodes[i])
                        val secondNode = Plot.nodes.getValue(chromosome.nodes[0])
                        distance += calculateDistance(firstNode, secondNode)
                    } else {
                        val firstNode = Plot.nodes.getValue(chromosome.nodes[i])
                        val secondNode = Plot.nodes.getValue(chromosome.nodes[i + 1])
                        distance += calculateDistance(firstNode, secondNode)
                    }
                }
                sumOfFitness += distance
                reproductionTable.add(Reproduction(chromosome, distance))
            }
            middleValue = sumOfFitness / chromosomes.size
            for (reproduction in reproductionTable) {
                val normalizedValue = reproduction.fitnessValue / sumOfFitness
                reproduction.normalizedValue = normalizedValue
                reproduction.expectedChromosomeCount = normalizedValue * chromosomes.size
            }
            val newGeneration = tournamentSelection()
            val crossedGeneration = Crossover.cross(newGeneration, crossoverProbability)
            val mutatedChromosomes = Mutator.mutate(crossedGeneration, mutateChance)
            val bestAndMiddleValueOfFitness = getBestAndMiddleValueOfFitness()
            return Population(
                mutatedChromosomes,
                bestAndMiddleValueOfFitness.first,
                bestChromosome!!,
                bestAndMiddleValueOfFitness.second
            )
        }

        private fun calculateDistance(firstNode: Point2D, secondNode: Point2D): Double {
            return sqrt(
                (firstNode.x.toDouble() - secondNode.x).pow(2.0) +
                        (firstNode.y.toDouble() - secondNode.y).pow(2.0)
            )
        }

        private fun getBestAndMiddleValueOfFitness(): Pair<Double, Double> {
            var bestFitness = Double.MAX_VALUE
            var middleFitness = 0.0
            for (reproduction in reproductionTable) {
                if (reproduction.fitnessValue < bestFitness) {
                    bestFitness = reproduction.fitnessValue
                    bestChromosome = reproduction.chromosome
                }
                middleFitness += reproduction.fitnessValue
            }
            return Pair(bestFitness, middleFitness / reproductionTable.size)
        }

        private fun tournamentSelection(candidateNumber: Int = 3): List<Reproduction> {
            if (reproductionTable.size < candidateNumber) {
                throw IllegalStateException("Candidate number $candidateNumber > than population size ${reproductionTable.size}")
            }
            val winners = mutableListOf<Reproduction>()
            var best: Reproduction
            for (index in reproductionTable.indices) {
                best = reproductionTable[Random().nextInt(reproductionTable.size)]
                for (i in 1 until candidateNumber) {
                    val candidate = reproductionTable[Random().nextInt(reproductionTable.size)]
                    if (best.fitnessValue > candidate.fitnessValue) {
                        best = candidate
                    }
                }
                winners.add(best)
            }
            return winners
        }
    }
}