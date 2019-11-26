package view.genetic

import org.junit.jupiter.api.Test
import view.Reproduction
import java.util.*


internal class CrossoverTest {

    @Test
    fun cross() {
        val parent1 =
            Chromosome(
                listOf(
                    36,
                    19,
                    30,
                    9,
                    5,
                    56,
                    52,
                    47,
                    26,
                    57,
                    12,
                    18,
                    24,
                    66,
                    59,
                    28,
                    44,
                    4,
                    29,
                    58,
                    2,
                    25,
                    15,
                    73,
                    71,
                    46,
                    51,
                    31,
                    20,
                    70,
                    35,
                    62,
                    40,
                    54,
                    23,
                    48,
                    10,
                    38,
                    75,
                    1,
                    32,
                    43,
                    69,
                    22,
                    45,
                    55,
                    41,
                    21,
                    60,
                    3,
                    74,
                    63,
                    37,
                    34,
                    68,
                    6,
                    72,
                    27,
                    8,
                    39,
                    65,
                    61,
                    64,
                    42,
                    76,
                    14,
                    49,
                    16,
                    17,
                    50,
                    33,
                    67,
                    13,
                    7,
                    53,
                    11
                )
            )
        val parent2 =
            Chromosome(
                listOf(
                    36,
                    19,
                    30,
                    9,
                    5,
                    56,
                    52,
                    47,
                    26,
                    57,
                    12,
                    18,
                    24,
                    66,
                    59,
                    28,
                    44,
                    4,
                    29,
                    58,
                    2,
                    25,
                    15,
                    73,
                    71,
                    46,
                    51,
                    31,
                    20,
                    70,
                    35,
                    62,
                    40,
                    54,
                    23,
                    48,
                    10,
                    38,
                    75,
                    1,
                    32,
                    43,
                    69,
                    22,
                    45,
                    55,
                    41,
                    21,
                    60,
                    3,
                    74,
                    63,
                    37,
                    34,
                    68,
                    6,
                    72,
                    27,
                    8,
                    39,
                    65,
                    61,
                    64,
                    42,
                    76,
                    14,
                    49,
                    16,
                    17,
                    50,
                    33,
                    67,
                    13,
                    7,
                    53,
                    11
                )
            )
        println(hasDuplicate(parent1.nodes))
        val reproduction1 = Reproduction(parent1, 0.0)
        val reproduction2 = Reproduction(parent2, 0.0)
        println(Crossover.cross(listOf(reproduction1, reproduction2), crossoverProbability = 100.0))
    }

    @Test
    fun test() {
        println(
            hasDuplicate(
                listOf(
                    19,
                    38,
                    7,
                    15,
                    48,
                    3,
                    63,
                    49,
                    66,
                    26,
                    60,
                    72,
                    2,
                    13,
                    21,
                    27,
                    53,
                    39,
                    44,
                    75,
                    20,
                    32,
                    76,
                    73,
                    70
                )
            )
        )
    }

    private fun <T> hasDuplicate(all: Iterable<T>): Boolean {
        val set: MutableSet<T> = HashSet()
        for (each in all) if (!set.add(each)) {
            println(each)
            return true
        }
        return false
    }
}