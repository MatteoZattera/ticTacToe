import kotlin.math.abs

class TicTacToe(
    private val SYMBOL_FREE_CELL: Char,
    private val SYMBOL_PLAYER_A: Char,
    private val SYMBOL_PLAYER_B: Char
) {

    private val grid = Array(3) { CharArray(3) { SYMBOL_FREE_CELL } }

    private val playersSymbolsAmountDifference: Int get() {
        val symbolAOccurrences = grid.joinToString("") { it.joinToString("") }.count { it == SYMBOL_PLAYER_A }
        val symbolBOccurrences = grid.joinToString("") { it.joinToString("") }.count { it == SYMBOL_PLAYER_B }
        return abs(symbolAOccurrences - symbolBOccurrences)
    }

    private var currentSymbol = SYMBOL_PLAYER_A
    val gameState get() = analyzeGameState()

    /** Returns a string representing the game state. */
    private fun analyzeGameState(): String {

        val playerAHasWon = playerHasWon(SYMBOL_PLAYER_A)
        val playerBHasWon = playerHasWon(SYMBOL_PLAYER_B)

        if ((playerAHasWon && playerBHasWon) || playersSymbolsAmountDifference > 1) return "Impossible"
        if (playerAHasWon) return "$SYMBOL_PLAYER_A wins"
        if (playerBHasWon) return "$SYMBOL_PLAYER_B wins"
        if (grid.joinToString("") { it.joinToString("") }.contains(SYMBOL_FREE_CELL)) return "Game not finished"
        return "Draw"
    }

    /**
     * Returns true if the grid has three [playerSymbol]s in a row.
     *
     * @throws IllegalArgumentException if [playerSymbol] is not one of the two player's symbols.
     */
    private fun playerHasWon(playerSymbol: Char): Boolean {

        if (playerSymbol !in listOf(SYMBOL_PLAYER_A, SYMBOL_PLAYER_B)) throw IllegalArgumentException("Invalid player symbol.")

        // Check horizontal lines
        for (line in grid) if (line.joinToString("") == "$playerSymbol".repeat(3)) return true

        // Check vertical lines
        for (columnIndex in 0..2)
            if (listOf(grid[0][columnIndex], grid[1][columnIndex], grid[2][columnIndex]).joinToString("") == "$playerSymbol".repeat(3)) return true

        // Check diagonal lines
        if (listOf(grid[0][0], grid[1][1], grid[2][2]).joinToString("") == "$playerSymbol".repeat(3)) return true
        if (listOf(grid[0][2], grid[1][1], grid[2][0]).joinToString("") == "$playerSymbol".repeat(3)) return true

        return false
    }

    /**
     * Performs the next turn and returns true if the [input] was valid, otherwise it returns false
     * and prints to the standard output stream why the [input] is invalid.
     */
    fun nextTurn(input: String): Boolean {

        if (!input.matches(Regex("-?\\d+ -?\\d+"))) "You should enter numbers!".printlnIt().also { return false }

        val positionX = input.split(" ")[1].toIntOrNull() ?: -1
        val positionY = input.split(" ")[0].toIntOrNull() ?: -1
        if (positionX !in 1..3 || positionY !in 1..3) "Coordinates should be from 1 to 3!".printlnIt().also { return false }

        if (grid[positionY - 1][positionX - 1] != SYMBOL_FREE_CELL) "This cell is occupied! Choose another one!".printlnIt().also { return false }

        grid[positionY - 1][positionX - 1] = currentSymbol
        return true
    }

    /** Changes the current player. */
    fun swapPlayer() {
        currentSymbol = if (currentSymbol == SYMBOL_PLAYER_A) SYMBOL_PLAYER_B else SYMBOL_PLAYER_A
    }

    /** Prints to the standard output stream the grid. */
    fun printGrid() {
        grid.joinToString("\n", "---------\n", "\n---------") { it.joinToString(" ", "| ", " |") }.printlnIt()
    }
}
