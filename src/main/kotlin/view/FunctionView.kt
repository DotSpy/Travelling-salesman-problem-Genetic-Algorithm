package view

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.scene.paint.Color
import tornadofx.*
import view.controller.GeneticController
import view.genetic.Chromosome

class FunctionView : View() {

    private val populationSize = SimpleIntegerProperty()
    private val generationCount = SimpleIntegerProperty()
    private val probabilityOfCrossing = SimpleDoubleProperty()
    private val probabilityOfMutation = SimpleDoubleProperty()
    private val middleFitnessValue = SimpleDoubleProperty()
    private val bestFitnessValue = SimpleDoubleProperty()
    private val solution = SimpleDoubleProperty()
    private val paths = FXCollections.observableArrayList<Path>()

    override fun onBeforeShow() {
        this.primaryStage.minWidth = 1500.0
        this.primaryStage.minHeight = 1000.0
    }

    override val root = form {
        fieldset {
            field("Population size") {
                textfield(populationSize).text = "5"
            }
            field("Generation count") {
                textfield(generationCount).text = "5"
            }
            field("Probability of crossing") {
                textfield(probabilityOfCrossing).text = "0.9"
            }
            field("Probability of mutation") {
                textfield(probabilityOfMutation).text = "0.1"
            }
            stackpane {
                setPrefSize(1500.0, 700.0)
                group {
                    Plot.nodes.forEach { (t, u) ->
                        circle {
                            centerX = u.x / Plot.scale
                            centerY = u.y / Plot.scale
                            radius = 5.0
                            fill = Color.CORAL
                        }
                    }
                }
                group {
                    bindChildren(paths) { path ->
                        line {
                            startX = path.x / Plot.scale
                            startY = path.y / Plot.scale
                            endX = path.x2 / Plot.scale
                            endY = path.y2 / Plot.scale
                        }
                    }
                }
            }
            button("Start") {
                action {
                    runAsync {
                        var generation = emptyList<Chromosome>()
                        val generationCount = generationCount.value
                        for (i in 1..generationCount) {
                            val uiData = getNextGeneration(generation)
                            generation = uiData.generation
                            runLater {
                                bestFitnessValue.value = uiData.bestFitnessValue
                                middleFitnessValue.value = uiData.middleFitnessValue
                                paths.clear()
                                for (j in 0..uiData.generationPoints.nodes.indices.last) {
                                    if (j == uiData.generationPoints.nodes.lastIndex) {
                                        val firstNode = Plot.nodes.getValue(uiData.generationPoints.nodes[j])
                                        val secondNode = Plot.nodes.getValue(uiData.generationPoints.nodes[0])
                                        paths.add(
                                            Path(
                                                firstNode.x,
                                                firstNode.y,
                                                secondNode.x,
                                                secondNode.y
                                            )
                                        )
                                    } else {
                                        val firstNode = Plot.nodes.getValue(uiData.generationPoints.nodes[j])
                                        val secondNode = Plot.nodes.getValue(uiData.generationPoints.nodes[j + 1])
                                        paths.add(
                                            Path(
                                                firstNode.x ,
                                                firstNode.y,
                                                secondNode.x,
                                                secondNode.y
                                            )
                                        )
                                    }
                                }
                                solution.value = uiData.bestFitnessValue
                            }
//                            Thread.sleep(800)
                        }
                    }
                }
            }
            hbox {
                label("Best Fitness Value = ")
                label(bestFitnessValue)
            }
            hbox {
                label("Middle Fitness Value = ")
                label(middleFitnessValue)
            }
            hbox {
                label("Solution\\original = ")
                label(solution)
                label(" \\ ${Plot.min}")
            }
        }
    }

    private fun getNextGeneration(generation: List<Chromosome>): UiData {
        val population = GeneticController.calculate(
            populationSize.value,
            probabilityOfCrossing.value,
            probabilityOfMutation.value,
            generation
        )
        return UiData(
            population.bestChromosome,
            population.bestFitnessValue,
            population.middleFitnessValue,
            population.population
        )
    }
}

data class UiData(
    val generationPoints: Chromosome,
    val bestFitnessValue: Double,
    val middleFitnessValue: Double,
    val generation: List<Chromosome>
)

data class City(val x: Double, val y: Double) : Shape

data class Path(val x: Double, val y: Double, val x2: Double, val y2: Double) : Shape

interface Shape