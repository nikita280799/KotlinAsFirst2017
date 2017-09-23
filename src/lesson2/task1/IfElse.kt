@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Math.abs
import java.lang.Math.sqrt

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    val d = age % 10
    val s = age.toString()
    val a = age % 100
    if (a in 11..19) return s + " лет" else
        return when (d) {
            1 -> s + " год"
            2 -> s + " года"
            3 -> s + " года"
            4 -> s + " года"
            else -> s + " лет"
        }
}


/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val halfS = (t1 * v1 + t2 * v2 + t3 * v3) / 2
    if (t1 * v1 >= halfS) return halfS / v1
    if ((t1 * v1 + t2 * v2) >= halfS) return t1 + (halfS - t1 * v1) / v2 else return t1 + t2 + (halfS - t1 * v1 - t2 * v2) / v3
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    if (((kingX == rookX1) || (kingY == rookY1)) && ((kingY == rookY2) || (kingX == rookX2))) return 3
    if ((kingX == rookX1) || (kingY == rookY1)) return 1
    if ((kingX == rookX2) || (kingY == rookY2)) return 2
    return 0
}


/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    if (((Math.abs(kingX - bishopX)) == (Math.abs(kingY - bishopY))) && ((kingX == rookX) || (kingY == rookY))) return 3
    if ((Math.abs(kingX - bishopX)) == (Math.abs(kingY - bishopY))) return 2
    if ((kingX == rookX) || (kingY == rookY)) return 1
    return 0
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    if ((a == b) && (b == c)) return 0
    if ((a >= b) && (a >= c)) {
        if (a >= (b + c)) return -1
        if (a == sqrt((sqr(b) + sqr(c)))) return 1
        if (a > sqrt((sqr(b) + sqr(c)))) return 2
        if (a < sqrt((sqr(b) + sqr(c)))) return 0
    }
    if ((b >= a) && (b >= c)) {
        if (b >= (a + c)) return -1
        if (b == sqrt((sqr(a) + sqr(c)))) return 1
        if (b > sqrt((sqr(a) + sqr(c)))) return 2
        if (b < sqrt((sqr(a) + sqr(c)))) return 0
    }
    if ((c >= b) && (c >= a)) {
        if (c >= (a + b)) return -1
        if (c == sqrt((sqr(a) + sqr(b)))) return 1
        if (c > sqrt((sqr(a) + sqr(b)))) return 2
        if (c < sqrt((sqr(a) + sqr(b)))) return 0
    }
    return -1

}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    val lengthAB = abs(b - a)
    val lengthCD = abs(d - c)
    if ((lengthAB == 0) || (lengthCD == 0)) {
        if ((lengthAB == 0) && (lengthCD == 0) && (a == c)) return 0
        if ((lengthAB == 0) && (a >= c) && (a <= d)) return 0
        if ((lengthCD == 0) && (c >= a) && (c <= b)) return 0
    } else {
        if ((a == c) && (b == d)) return lengthAB
        if ((b == c) || (a == d)) return 0
        if ((a == c) || (b == d)) {
            return if (b == d) {
                if (a > c) lengthAB else lengthCD
            } else {
                return if (b < d) lengthAB else lengthCD
            }
        }
        if (b > d) {
            if (d > a) {
                return if (a > c) abs(d - a) else lengthCD
            }
        } else {
            if (c < a) return lengthAB
            if ((c > a) && (c < b)) return abs(b - c)
        }
    }
    return -1
}

