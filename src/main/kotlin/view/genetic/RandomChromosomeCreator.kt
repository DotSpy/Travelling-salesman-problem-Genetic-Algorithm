package view.genetic

import view.Plot

object RandomChromosomeCreator {

    fun createRandomChromosome(): Chromosome = Chromosome(Plot.nodes.keys.shuffled())
}