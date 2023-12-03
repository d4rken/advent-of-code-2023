import common.BasePaths.BASE_PATH
import java.io.File

fun main(args: Array<String>) {
    val inputRaw = File(BASE_PATH, "day3/input.txt").readLines()
    println("Read ${inputRaw.size} lines")

    val symbolMap = SymbolMap(inputRaw.map { it.toCharArray() })

    val symbols = symbolMap.getSymbols()
    println("${symbols.size} symbols")

    val partNumbers = symbols
        .map { symbolMap.findPartNumbers(it) }
        .flatten()

    println("${partNumbers.size} part numbers")
}

data class SymbolMap(
    val data: List<CharArray>
) {

    data class Pos(
        val x: Int,
        val y: Int,
    )

    private fun Pos.get() = data[y][x]

    private fun Char?.isSymbol(): Boolean {
        if (this == null) return false
        if (this.isDigit()) return false
        return this != '.'
    }

    data class Symbol(
        val type: Char,
        val pos: Pos,
    ) {
        val neighbors: List<Pos>
            get() = listOf(
                Pos(pos.x, pos.y + 1),  // North
                Pos(pos.x, pos.y - 1),  // South
                Pos(pos.x + 1, pos.y),  // East
                Pos(pos.x - 1, pos.y)   // West
            )
    }

    fun getSymbols(): List<Symbol> = data
        .mapIndexed { yIndex, row ->
            row
                .mapIndexed { xIndex, c ->
                    if (c.isSymbol()) Symbol(c, Pos(x = xIndex, y = yIndex)) else null
                }
                .filterNotNull()
        }
        .flatten()

    private fun getCharOrNull(x: Int, y: Int) = try {
        data[y][x]
    } catch (e: Exception) {
        null
    }.also {
//        println("getCharOrNull($x,$y) -> $it")
    }

    data class Part(
        val number: Int,
        val pos: Pos,
    ) {
        val length: Int
            get() = number.toString().length
    }

    private fun isClose(target: Pos, origin: Pos, max: Int = 1): Boolean =
        Math.abs(target.x - origin.x) + Math.abs(target.y - origin.y) <= max

    fun findPartNumbers(symbol: Symbol): List<Part> {
        val numbers = symbol.neighbors.filter { it.get().isDigit() }
        return TODO()
    }

}
