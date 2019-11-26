package view.genetic

import java.util.*
import kotlin.random.Random

object Mutator {

    fun mutate(chromosomes: List<Chromosome>, chanceToMutate: Double): List<Chromosome> {
        val mutatedChromosomes = mutableListOf<Chromosome>()
        for (chromosome in chromosomes) {
            val mutated = Chromosome(mutateValue(chanceToMutate, chromosome.nodes))
            mutatedChromosomes.add(mutated)
        }
        return mutatedChromosomes
    }

    private fun mutateValue(chanceToMutate: Double, nodes: List<Int>): List<Int> {
        val mutatedNodes = nodes.toMutableList()
        if (chanceToMutate > Random.nextDouble()) {
            Collections.swap(mutatedNodes, Random.nextInt(0, nodes.lastIndex), Random.nextInt(0, nodes.lastIndex))
        }
        return mutatedNodes
    }
}