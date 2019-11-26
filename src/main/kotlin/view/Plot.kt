package view

import view.model.Point2D
import java.io.FileNotFoundException
import java.io.InputStream

object Plot {

    val nodes: MutableMap<Int, Point2D>
    val calculatedRoads: MutableMap<Point2D, Pair<Point2D, Double>> = mutableMapOf()
    val min: Int = 108159
    val scale = 20

    init {
        val nodes = mutableMapOf<Int, Point2D>()
        val inputStream: InputStream = this::class.java.classLoader.getResourceAsStream("pr76/pr76.tsp")
            ?: throw FileNotFoundException("Not Found pr76/pr76.tsp")
        val lineList = mutableListOf<String>()
        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }
        lineList.asSequence()
            .filter { it.first().isDigit() }
            .forEach {
                val split = it.split(" ")
                nodes[split[0].toInt()] = Point2D(split[1].toDouble(), split[2].toDouble())
            }
        this.nodes = nodes
    }
}