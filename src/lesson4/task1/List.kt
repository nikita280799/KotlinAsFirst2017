@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.isPrime
import lesson3.task1.powInt
import java.io.File.separator
import java.lang.Math.pow
import java.lang.Math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var sum = 0.0
    for (element in v) {
        sum += sqr(element)
    }
    return sqrt(sum)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var s = 0.0
    if (list.isEmpty()) return s
    for (element in list) {
        s += element
    }
    return s / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val s = mean(list)
    for (i in 0 until list.size) {
        list[i] -= s
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var c = 0.0
    for (i in 0 until a.size) {
        c += a[i] * b[i]
    }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var px = 0.0
    for (i in 0 until p.size) {
        px += p[i] * powInt(x, i)
    }
    return px
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var s = 0.0
    for (i in 0 until list.size) {
        s += list[i]
        list[i] = s
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var a = n
    var i = 2
    var list = listOf<Int>()
    while (a != 1) {
        if (isPrime(a)) {
            list += a
            break
        }
        if ((a % i == 0) && (isPrime(i))) {
            while (a % i == 0) {
                list += i
                a /= i
            }
        }
        i++
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var a = n
    var list = listOf<Int>()
    while (a / base > 0) {
        list += a % base
        a /= base
    }
    list += a
    return list.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val s = convert(n, base)
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    val sb = StringBuilder()
    for (i in 0 until s.size) {
        if (s[i] > 9) sb.append(alphabet[s[i] - 10]) else sb.append(s[i])
    }
    return sb.toString()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun powInt(a: Int, b: Int): Int {
    var s = 1
    for (i in 1..b) {
        s *= a
    }
    return s
}


fun decimal(digits: List<Int>, base: Int): Int {
    var s = 0
    for (i in digits.size - 1 downTo 0) {
        s += powInt(base, digits.size - 1 - i) * digits[i]
    }
    return s
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val alphabet = "0123456789abcdefghijklmnopqrstuvwxyz"
    var list = listOf<Int>()
    for (i in 0 until str.length) {
        list += (alphabet.indexOf(str[i], 0))
    }
    return decimal(list, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun romanPart(n: Int, c: Int, s: String): String {
    val sb = StringBuilder()
    var n1 = n
    while (n1 >= c) {
        n1 -= c
        sb.append(s)
    }
    return sb.toString()
}


fun roman(n: Int): String {
    val sb = StringBuilder()
    var n1 = n
    val listOfLetters = listOf("M", "CM", "D", "CD",
            "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    val listOfNumerals = listOf(1000, 900, 500, 400,
            100, 90, 50, 40, 10, 9, 5, 4, 1)
    for (i in 0..12) {
        if (n1 >= listOfNumerals[i]) {
            sb.append(romanPart(n1, listOfNumerals[i], listOfLetters[i]))
            n1 %= listOfNumerals[i]
        }
    }
    return sb.toString()
}


/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun russianPart(n: Int, m: Boolean): List<String> {
    val listHundred = listOf("сто", "двести", "триста",
            "четыреста", "пятьсот", "шестьсот",
            "семьсот", "восемьсот", "девятьсот")
    val listDecades = listOf("двадцать",
            "тридцать", "сорок", "пятьдесят", "шестьдесят",
            "семьдесят", "восемьдесят", "девяносто")
    val listUnit = listOf("","один", "два", "три",
            "четыре", "пять", "шесть",
            "семь", "восемь", "девять")
    val listUniqueNumbers = listOf("десять", "одиннадцать",
            "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать",
            "восемнадцать", "девятнадцать")
    val listUnitOfThousends = listOf("тысяч",
            "одна тысяча", "две тысячи", "три тысячи",
            "четыре тысячи", "пять тысяч", "шесть тысяч",
            "семь тысяч", "восемь тысяч", "девять тысяч")
    val list = mutableListOf<String>()
    var a = n
    if (a > 0) {
        if (a >= 100) {
            list += listHundred[a / 100 - 1]
            a %= 100
        }
        if (a >= 10) {
            if (a in 10..19) {
                list += listUniqueNumbers[a % 10]
                a -= a % 10
            } else {
                list += listDecades[a / 10 - 2]
            }
            a %= 10
        }
        list += if (m) listUnitOfThousends[a] else listUnit[a]
    }
    return list
}

fun russian(n: Int): String {
    val list = russianPart(n / 1000, true) + russianPart(n % 1000, false)
    return list.joinToString(separator = " ").trim()
}
