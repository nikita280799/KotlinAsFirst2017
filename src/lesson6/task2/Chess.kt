@file:Suppress("UNUSED_PARAMETER")

package lesson6.task2

import java.lang.Math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        if (!Square(column, row).inside()) return ""
        val map = mapOf(1 to "a", 2 to "b", 3 to "c", 4 to "d", 5 to "e", 6 to "f", 7 to "g", 8 to "h")
        val column1 = map[column] ?: ""
        return "$column1$row"
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    val map = mapOf('a' to 1, 'b' to 2, 'c' to 3, 'd' to 4, 'e' to 5, 'f' to 6, 'g' to 7, 'h' to 8)
    val column = map[notation[0]] ?: throw IllegalArgumentException()
    if ((notation[1] !in '1'..'8') || notation.length != 2) throw IllegalArgumentException()
    return Square(column, notation[1].toString().toInt())
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    if (start == end) return 0
    if ((start.column == end.column) || (start.row == end.row)) return 1
    return 2
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val list = mutableListOf<Square>()
    val r = rookMoveNumber(start, end)
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    list.add(start)
    if (r == 2) list.add(Square(start.column, end.row))
    if (r > 0) list.add(end)
    return list
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    if ((start.column + start.row) % 2 != (end.column + end.row) % 2) return -1
    if (start == end) return 0
    if (Math.abs(start.column - end.column) == Math.abs(start.row - end.row)) return 1
    return 2
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val list = mutableListOf<Square>()
    val r = bishopMoveNumber(start, end)
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    if (r == -1) return list
    list.add(start)
    if (r == 2) {
        val x: Int
        val y: Int
        val z: Int
        val t: Int
        if (start.column >= end.column) {
            x = end.column
            y = end.row
            z = start.column
            t = start.row
        } else {
            z = end.column
            t = end.row
            x = start.column
            y = start.row
        }
        var column = x + (z - x) / 2.0 + (t - y) / 2.0
        var row = y + (z - x) / 2.0 + (t - y) / 2.0
        if ((column !in 1..8) || (row !in 1..8) || (column % 1.0 != 0.0) || (row % 1.0 != 0.0)) {
            column = x + (z - x) / 2.0 - (t - y) / 2.0
            row = y - (z - x) / 2.0 + (t - y) / 2.0
        }
        list.add(Square(column.toInt(), row.toInt()))
    }
    if (r > 0) list.add(end)
    return list
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int = kingTrajectory(start, end).size - 1

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    var y = start.column
    var x = start.row
    val list = mutableListOf<Square>()
    list.add(start)
    while (Square(y, x) != end) {
        if ((y == end.column) || (x == end.row)) {
            if (y == end.column) {
                while (x != end.row) {
                    if (x > end.row) x-- else x++
                    list.add(Square(y, x))
                }
            } else {
                while (y != end.column) {
                    if (y > end.column) y-- else y++
                    list.add(Square(y, x))
                }
            }
            break
        }
        if ((y < end.column) && (x < end.row)) {
            y++
            x++
            list.add(Square(y, x))
        }
        if ((y < end.column) && (x > end.row)) {
            y++
            x--
            list.add(Square(y, x))
        }
        if ((y > end.column) && (x < end.row)) {
            y--
            x++
            list.add(Square(y, x))
        }
        if ((y > end.column) && (x > end.row)) {
            y--
            x--
            list.add(Square(y, x))
        }
    }
    return list
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = knightTrajectory(start, end).size - 1

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> {
    val list = mutableListOf<Square>()
    list.add(start)
    var s: Square
    if (start == end) return list
    s = knight1step(start, end)
    list.add(s)
    while (s != end) {
        s = knight1step(s, end)
        list.add(s)
    }
    return list
}

fun knight1step(start: Square, end: Square): Square {
    var dr = end.row - start.row
    var dc = end.column - start.column
    val r = start.row
    val c = start.column
    val rz: Int
    val cz: Int
    val p: Int
    val f: Int
    var t: Pair<Int, Int>
    if (dr > 0) {
        rz = 1
    } else {
        rz = -1
        dr *= -1
    }
    if (dc > 0) {
        cz = 1
    } else {
        cz = -1
        dc *= -1
    }
    if (dr > dc) {
        p = 1
    } else {
        p = -1
        f = dc
        dc = dr
        dr = f
    }
    if ((dr == 1) && (dc == 1) && ((start == Square(1, 1))
            || (start == Square(8, 8))
            || (start == Square(1, 8)) || (start == Square(8, 1)))) {
        t = transformation(1, 1, rz, cz, p)
        return Square(c + t.first, r + t.second)
    }
    if ((dr == 1) && (dc == 1) && ((start == Square(2, 2))
            || (start == Square(7, 7))
            || (start == Square(2, 7)) || (start == Square(7, 2)))) {
        t = transformation(-1, -2, rz, cz, p)
        return Square(c + t.first, r + t.second)
    }
    if ((dr == 1) && (dc == 1) && ((start == Square(1, 4))
            || (start == Square(4, 1))
            || (start == Square(1, 5)) || (start == Square(5, 1))
            || (start == Square(8, 4)) || (start == Square(4, 8))
            || (start == Square(8, 5)) || (start == Square(5, 8)))) {
        t = transformation(2, -1, rz, cz, p)
        if (Square(c + t.first, r + t.second).inside())
            return Square(c + t.first, r + t.second) else {
            t = transformation(-2, -1, rz, cz, p)
            return Square(c + t.first, r + t.second)
        }
    }
    if (((dc == 0) && (dr == 1)) || ((dc == 0) && (dr == 2))) {
        t = transformation(2, 1, rz, cz, p)
        if (Square(c + t.first, r + t.second).inside())
            return Square(c + t.first, r + t.second) else {
            t = transformation(-2, 1, rz, cz, p)
            return Square(c + t.first, r + t.second)
        }
    }
    if ((dc == 1) && (dr == 1)) {
        t = transformation(2, -1, rz, cz, p)
        if (Square(c + t.first, r + t.second).inside())
            return Square(c + t.first, r + t.second) else {
            t = transformation(-1, 2, rz, cz, p)
            return Square(c + t.first, r + t.second)
        }
    }
    if ((dc == 1) && (dr == 3)) {
        t = transformation(2, 1, rz, cz, p)
        if (Square(c + t.first, r + t.second).inside())
            return Square(c + t.first, r + t.second) else {
            t = transformation(-1, 2, rz, cz, p)
            return Square(c + t.first, r + t.second)
        }
    }
    if (((dc == 2) && (dr == 3)) || ((dc == 3) && (dr == 4))) {
        t = transformation(2, 1, rz, cz, p)
        return Square(c + t.first, r + t.second)
    }
    t = transformation(1, 2, rz, cz, p)
    return Square(c + t.first, r + t.second)
}

fun transformation(dc: Int, dr: Int, rz: Int, cz: Int, p: Int): Pair<Int, Int> {
    var r: Int
    var c: Int
    if (p == -1) {
        r = dc
        c = dr
    } else {
        r = dr
        c = dc
    }
    if (rz == -1) r *= -1
    if (cz == -1) c *= -1
    return Pair(c, r)
}
