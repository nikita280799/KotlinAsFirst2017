@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var count = 0
    var a = n
    while (a != 0) {
        count++
        a /= 10
    }
    return if (count == 0) 1 else count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var n1 = 1
    var n2 = 1
    var s = 1
    for (i in 3..n) {
        s = n1 + n2
        val c = n2
        n2 += n1
        n1 = c
    }
    return s
}


/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var a = m
    var b = n
    val c = a * b
    while ((a != 0) && (b != 0)) {
        if (a > b) a %= b else b %= a
    }
    return c / (a + b)
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var min = 0
    for (i in 2..n) {
        if ((n % i) == 0) {
            min = i
            break
        }
    }
    return min
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var max = 0
    for (i in (n - 1) downTo 1) {
        if ((n % i) == 0) {
            max = i
            break
        }
    }
    return max
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val max = max(m, n)
    for (i in 2..max) {
        if (((m % i) == 0) && ((n % i) == 0)) return false
    }
    return true
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun sqrInt(a: Int): Int = a * a

fun squareBetweenExists(m: Int, n: Int): Boolean {
    for (i in m..n) {
        val c = sqrt(i.toDouble()).toInt()
        if (sqrInt(c) == i) return true
    }
    return false
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun powInt(a: Double, b: Int): Double {
    var s = 1.0
    for (i in 1..b) {
        s *= a
    }
    return s
}

fun sin(x: Double, eps: Double): Double {
    var s = 0.0
    var c = x
    var k = 1
    var p = 1
    var v = x
    v %= (2 * PI)
    while (abs(c) > eps) {
        c = powInt(v, p) / factorial(p)
        if (k % 2 == 1) s += c else s -= c
        p += 2
        k++
    }
    return s
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */


fun cos(x: Double, eps: Double): Double {
    var s = 1.0
    var c = 1.0
    var k = 1
    var p = 2
    var v = x
    v = v / PI % 2 * PI
    while (abs(c) > eps) {
        c = powInt(v, p) / factorial(p)
        if (k % 2 == 0) s += c else s -= c
        p += 2
        k++
    }
    return s
}


/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    val count = digitNumber(n)
    var revertN = 0.0
    var number = n
    for (i in count downTo 1) {
        revertN += number % 10 * powInt(10.0, (i - 1))
        number /= 10
    }
    return revertN.toInt()
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    var count = digitNumber(n) - 1
    val s = n.toString()
    for (i in 0..count) {
        if (s[i] != s[count]) return false
        count -= 1
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val count = digitNumber(n)
    val c = n % 10
    var s = n
    for (i in 1..count) {
        val a = s % 10
        if (a != c) return true
        s /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var k = 0
    var i = 0.0
    var s = 0
    while (n > k) {
        i++
        s = sqr(i).toInt()
        k += digitNumber(s)
    }
    if (n == k) return s % 10
    for (j in 1..k - n) s /= 10
    return s % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var k = 0
    var i = 0
    var s = 0
    var n1 = 1
    var n2 = 1
    while (n > k) {
        i++
        if (i < 3) s = 1 else {
            s = n1 + n2
            val c = n2
            n2 += n1
            n1 = c
        }
        k += digitNumber(s)
    }
    if (n == k) return s % 10
    for (j in 1..k - n) s /= 10
    return s % 10
}
