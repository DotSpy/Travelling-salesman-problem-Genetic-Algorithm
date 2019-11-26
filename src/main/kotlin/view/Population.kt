package view

import view.genetic.Chromosome

data class Population(
    var population: List<Chromosome>,
    val bestFitnessValue: Double,
    val bestChromosome: Chromosome,
    val middleFitnessValue: Double
)