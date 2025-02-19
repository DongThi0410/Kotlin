package com.example.learnkotlin

import java.util.Random

class Hello {
    val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")
    fun swim(speed: String = "fast") {
        println("swimming $speed")
    }

    fun shouldChangeWater(day: String, temperature: Int = 22, dirty: Int = 22): Boolean {
        return when {
            isTooHot(temperature) -> true
            isDirty(dirty) -> true
            isSunday(day) -> true
            else -> false
        }
    }

    fun randomDay(): String {
        val week = arrayOf(
            "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"
        )
        return week[Random().nextInt(week.size)]
    }

    fun fishFood(day: String): String {
        var food = ""
        when (day) {
            "Monday" -> food = "flakes"
            "Tuesday" -> food = "pellets"
            "Wednesday" -> food = "redworms"
            "Thursday" -> food = "granules"
            "Friday" -> food = "mosquitoes"
            "Saturday" -> food = "lettuce"
            "Sunday" -> food = "plankton"
        }
        return food
    }

    fun isTooHot(temperature: Int) = temperature > 30

    fun isDirty(dirty: Int) = dirty > 30

    fun isSunday(day: String) = day == "Sunday"
    fun feedTheFish() {
        val day = randomDay()
        val food = fishFood(day)
        println("Today is $day and the fish eat $food")
        println("Change water: ${shouldChangeWater(day)}")

    }

}

fun main() {
    val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")
    val eager = decorations.filter { it[0] == 'p' }
//    println("eager: $eager")
    val filtered = decorations.asSequence().filter { it[0] == 'p' }
//    println("filtered: $filtered")
    val newList = filtered.toList()
//    println("new list: $newList")
    val lazyMap = decorations.asSequence().map {
        println("access: $it")
        it
    }
//    println("lazy: $lazyMap")
//    println("---")
//    println("first: ${lazyMap.first()}")
//    println("---")
//    println("all: ${lazyMap.toList()}")
//    val lazyMap2 = decorations.asSequence().filter { it[0] == 'p' }.map {
//        println("access: $it")
//        it
//    }
//    println("---")
//    println("filtered: ${lazyMap2.toList()}")
    val haircares = listOf("serum", "perfume")
    val skincare = listOf("suncream", "toner")
    val myList = listOf(haircares, skincare)
    println("Flat: ${myList.flatten()}")
}