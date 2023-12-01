import common.BasePaths.BASE_PATH
import java.io.File

fun main(args: Array<String>) {
    val inputRaw = File(BASE_PATH, "day1/input.txt").readLines()
    println("Read ${inputRaw.size} lines")


    val resultPart1 = inputRaw
        .map { line -> line.first { it.isDigit() } to line.last { it.isDigit() } }
        .map { "${it.first}${it.second}".toInt() }
        .sum()

    println("Part1 result is $resultPart1")

    val resultPart2 = inputRaw
        .map { line ->
            val aWord = NUMBERS
                .map { it.toNumber() to line.indexOf(it) }
                .filter { it.second != -1 }
                .minByOrNull { it.second }

            val aNumber = line
                .mapIndexed { index, c -> c to index }
                .firstOrNull { it.first.isDigit() }

            val bWord = NUMBERS
                .map { it.toNumber() to line.lastIndexOf(it) }
                .filter { it.second != -1 }
                .maxByOrNull { it.second }

            val bNumber = line
                .mapIndexed { index, c -> c to index }
                .lastOrNull { it.first.isDigit() }

            val a = when {
                aWord != null && aNumber == null -> aWord.first
                aWord == null && aNumber != null -> aNumber.first
                else -> if (aWord!!.second <= aNumber!!.second) aWord.first else aNumber.first
            }

            val b = when {
                bWord != null && bNumber == null -> bWord.first
                aWord == null && bNumber != null -> bNumber.first
                else -> if (bWord!!.second >= bNumber!!.second) bWord.first else bNumber.first
            }

            val comb = "$a$b".toInt()

            //println("$line $aWord $aNumber $bWord $bNumber -> $a $b -> $comb")

            comb
        }
        .sum()

    println("Part2 result is $resultPart2")
}

val NUMBERS = listOf(
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
)

fun String.toNumber() = when (this) {
    "one" -> 1
    "two" -> 2
    "three" -> 3
    "four" -> 4
    "five" -> 5
    "six" -> 6
    "seven" -> 7
    "eight" -> 8
    "nine" -> 9
    else -> throw IllegalArgumentException(this)
}
