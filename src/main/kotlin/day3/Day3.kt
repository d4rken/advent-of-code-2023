import common.BasePaths.BASE_PATH
import java.io.File

fun main(args: Array<String>) {
    val inputRaw = File(BASE_PATH, "day3/input.txt").readLines()
    println("Read ${inputRaw.size} lines")

    val schematic = Schematic(inputRaw.map { it.toCharArray() })

    val parts = schematic.getPartNumbers()
    println("${parts.size} part numbers")

    val validParts = parts.filter { part ->
        schematic.isValid(part).also {
//            if (it) println("VALID -> $part")
//            else println("INVALID -> $part")
        }
    }
    println("${validParts.size} VALID part numbers")

    val sum = validParts.sumOf { it.number }
    println("Sum of valid partnumbers is $sum")
}

data class Part(
    val number: Int,
    val x: Int,
    val y: Int,
) {
    val length: Int
        get() = number.toString().length
}

data class Schematic(
    val data: List<CharArray>
) {
    fun getPartNumbers(): List<Part> = data
        .mapIndexed { yIndex, row ->
            val parts = mutableListOf<Part>()
            var numberStartIndex = -1
            var numberRaw: StringBuilder? = null

            row.forEachIndexed { xIndex, c ->
                if (c.isDigit()) {
                    if (numberRaw == null) {
                        numberRaw = StringBuilder()
                        numberStartIndex = xIndex
                    }
                    numberRaw!!.append(c)
                }

                if (!c.isDigit() || xIndex == row.size - 1) {
                    if (numberRaw != null) {
                        val part = Part(
                            number = numberRaw.toString().toInt(),
                            x = numberStartIndex,
                            y = yIndex,
                        )
//                        println("New part $part")
                        parts.add(part)
                    }
                    numberRaw = null
                    numberStartIndex = -1
                    return@forEachIndexed
                }
            }

            parts
        }
        .flatten()

    private fun getCharOrNull(x: Int, y: Int) = try {
        data[y][x]
    } catch (e: Exception) {
        null
    }.also {
//        println("getCharOrNull($x,$y) -> $it")
    }

    private fun Char?.isSymbol(): Boolean {
        if (this == null) return false
        if (this.isDigit()) return false
        if (this == '.') return false
        println("symbol: $this")
        return true
    }

    fun isValid(part: Part): Boolean {
        if (getCharOrNull(x = part.x - 1, y = part.y).isSymbol()) {
            return true
        }
        if (getCharOrNull(x = part.x + part.length, y = part.y).isSymbol()) {
            return true
        }
        if (part.y > 0) {
            val match = (-1..part.length + 1).any {
                getCharOrNull(x = part.x + it, y = part.y - 1).isSymbol()
            }
            if (match) return true
        }
        if (part.y < data.size) {
            val match = (-1..part.length + 1).any {
                getCharOrNull(x = part.x + it, y = part.y + 1).isSymbol()
            }
            if (match) return true
        }
        return false
    }
}

