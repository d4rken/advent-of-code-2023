import common.BasePaths.BASE_PATH
import java.io.File

fun main(args: Array<String>) {
    val inputRaw = File(BASE_PATH, "day2/input.txt").readLines()
    println("Read ${inputRaw.size} lines")

    val regex = Regex("^Game (\\d+): (.+)\$")

    val games = inputRaw
        .mapNotNull { line ->
            val match = regex.matchEntire(line)
            if (match == null) {
                println("MATCH WAS NULL: $line")
                return@mapNotNull null
            }
            Game(
                round = match.groupValues[1].toInt(),
                draws = match.groupValues[2]
                    .split(";")
                    .map { round ->
                        //                        println("Processing $round")
                        fun String.getCount(color: String) = this
                            .split(",")
                            .map { it.trim() }
                            .filter { it.endsWith(color) }
                            .map { it.split(" ")[0].toInt() }
                            .sum()
                        Game.Draw(
                            red = round.getCount("red"),
                            green = round.getCount("green"),
                            blue = round.getCount("blue"),
                        ).also {
//                            println("$round -> red=${it.red} green=${it.green} blue=${it.blue} ")
                        }
                    }
            )
        }

    val possibleGames = games.filter { game ->
        val possible = game.draws.all { it.red <= 12 }
                && game.draws.all { it.green <= 13 }
                && game.draws.all { it.blue <= 14 }
//        println("$possible : $game")
        possible
    }

    println("${possibleGames.size} possible games, ID sum = ${possibleGames.sumOf { it.round }}")

    val minPowers = games.sumOf { game ->
        val minRed = game.draws.maxOf { it.red }
        val minBlue = game.draws.maxOf { it.blue }
        val minGreen = game.draws.maxOf { it.green }
//        println("minRed=$minRed minBlue=$minBlue minGreen=$minGreen from $game")
        minRed * minBlue * minGreen
    }
    print("sum min power: $minPowers")
}

data class Game(
    val round: Int,
    val draws: List<Draw>,
) {
    data class Draw(
        val red: Int,
        val green: Int,
        val blue: Int,
    )
}