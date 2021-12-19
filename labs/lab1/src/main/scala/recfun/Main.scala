package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    (c, r) match {
      case (column, row) if column < 0 || row < 0 => throw new Error("Column and row can not be less than zero")
      case (column, row) if column > row => throw new Error("Column should be less or equal than row")
      case (column, row) if column == row || column == 0 || row == 0 => 1
      case (column, row) => pascal(column - 1, row - 1) + pascal(column, row - 1)
    }
  
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    val (probablyIncorrect, charBalancer) = chars.foldLeft((false, 0)){ case ((probablyIncorrect, balance), symbol) =>
      if (symbol == ')' && balance == 0) (true, balance - 1)
      else if (symbol == ')') (probablyIncorrect, balance - 1)
      else if (symbol == '(') (probablyIncorrect, balance + 1)
      else (probablyIncorrect, balance)
    }

    !probablyIncorrect && charBalancer == 0
  }
  
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0 || coins.isEmpty) 0
    else if (money == 0) 1
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }
}
