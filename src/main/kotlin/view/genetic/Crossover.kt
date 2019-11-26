package view.genetic

import view.Reproduction
import kotlin.random.Random

object Crossover {

    fun cross(reproductions: List<Reproduction>, crossoverProbability: Double): List<Chromosome> {
        val shuffled = reproductions.toMutableList().also { it.shuffle() }
        val crossedChromosomes = mutableListOf<Chromosome>()
        var i = 0
        while (i <= shuffled.lastIndex) {
            if (i < shuffled.lastIndex && crossoverProbability > Random.nextDouble()) {
                val result = cross(shuffled[i].chromosome, shuffled[i + 1].chromosome)
                crossedChromosomes.add(result.first)
                crossedChromosomes.add(result.second)
                i += 2
            } else {
                crossedChromosomes.add(shuffled[i].chromosome)
                i++
            }
        }
        return crossedChromosomes
    }

    private fun cross(parent1: Chromosome, parent2: Chromosome): Pair<Chromosome, Chromosome> {
        val size = parent1.nodes.size
        val firstPoint = Random.nextInt(0, size / 2)
        val secondPoint = Random.nextInt(size / 2, size - 1)
        val subTour1 = IntArray(size) { -1 }
        var i = 0
        val firstMappedNodes = mutableMapOf<Int, Int>()
        for (sub in parent2.nodes.subList(firstPoint, secondPoint)) {
            subTour1[firstPoint + i] = sub
            firstMappedNodes[sub] = parent1.nodes.subList(firstPoint, secondPoint)[i]
            i++
        }
        val secondMappedNodes = mutableMapOf<Int, Int>()
        i = 0
        val subTour2 = IntArray(size) { -1 }
        for (sub in parent1.nodes.subList(firstPoint, secondPoint)) {
            subTour2[firstPoint + i] = sub
            secondMappedNodes[sub] = parent2.nodes.subList(firstPoint, secondPoint)[i]
            i++
        }
        fillSubTour(parent1, subTour1, firstMappedNodes)
        fillSubTour(parent2, subTour2, secondMappedNodes)
        return Pair(Chromosome(subTour1.toList()), Chromosome(subTour2.toList()))
    }

    private fun fillSubTour(parent: Chromosome, subTour: IntArray, mappedValues: Map<Int, Int>) {
        for (j in parent.nodes.indices) {
            if (subTour[j] == -1 && !subTour.contains(parent.nodes[j])) {
                subTour[j] = parent.nodes[j]
            } else if (subTour[j] == -1) {
                var endValue: Int = parent.nodes[j]
                while (subTour.contains(mappedValues.getValue(endValue))) {
                    endValue = mappedValues.getValue(endValue)
                }
                subTour[j] = mappedValues.getValue(endValue)
            }
        }
    }
}