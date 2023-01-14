/** Prints to the standard output stream `this` [T] value. */
fun <T> T.printlnIt() = println(this)

fun main() {

    with(TicTacToe(' ', 'X', 'O')) {

        printGrid()

        while (gameState == "Game not finished") {
            do { val successfulTurn = nextTurn(readln()) } while (!successfulTurn)
            printGrid()
            swapPlayer()
        }

        gameState.printlnIt()
    }
}
