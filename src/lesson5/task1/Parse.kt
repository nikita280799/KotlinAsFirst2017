@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    val list = mutableListOf(0, 0, 0)
    val listOfMonth = listOf("января", "февраля", "марта", "апреля", "мая",
            "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    if (parts.size != 3) return ""
    try {
        list[0] = parts[0].toInt()
        list[1] = listOfMonth.indexOf(parts[1]) + 1
        list[2] = parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if ((list[0] !in 1..31) || (list[1] == 0) || (list[2] < 0)) return ""
    return String.format("%02d.%02d.%d",
            list[0], list[1], list[2])
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    val listOfMonth = listOf("января", "февраля", "марта", "апреля", "мая",
            "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val day: Int
    val year: Int
    val month: Int
    if (parts.size != 3) return ""
    try {
        day = parts[0].toInt()
        month = parts[1].toInt()
        year = parts[2].toInt()
    } catch (e: NumberFormatException) {
        return ""
    }
    if ((day in 1..31) && (month in 1..12) && (year >= 0)) {
        return String.format("%d %s %d", day, listOfMonth[month - 1], year)
    }
    return ""
}


/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val list = phone.filter {
        (it.toInt() in 48..57) || ((it != ' ')
                && (it != '(') && (it != ')') && (it != '-'))
    }.toList()
    if (list.isEmpty()) return ""
    if ((list[0] != '+') && (list[0].toInt() !in 48..57)) return ""
    for (i in 1 until list.size) {
        if (list[i].toInt() !in 48..57) return ""
    }
    if ((list.size == 1) && (list[0] == '+')) return ""
    return list.joinToString(separator = "")
}


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(" ")
    val list1 = parts.filter { (it != "-") && (it != "%") && (it != "") }.toList()
    val list2 = mutableListOf<Int>()
    for (element in list1) {
        try {
            list2.add(element.toInt())
        } catch (e: NumberFormatException) {
            return -1
        }
    }
    val max = list2.max()
    return max ?: -1
}


/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var list1 = listOf<String>()
    var list2 = listOf<String>()
    var max = -1
    val parts = jumps.split(" ")
    var i = 1
    for (part in parts) {
        if (i % 2 == 1) {
            list1 += part
        } else {
            list2 += part
        }
        i++
    }
    if ((list1.isNotEmpty()) && (list2.isNotEmpty())) {
        for (j in 0 until list1.size) {
            try {
                if ((list1[j].toInt() >= max) && ("+" in list2[j])) {
                    max = list1[j].toInt()
                }
            } catch (e: NumberFormatException) {
                return -1
            }
        }
    }
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    var listOfNumbers = listOf<String>()
    var listOfSigns = listOf<String>()
    val parts = expression.split(" ")
    var i = 1
    var result: Int
    for (part in parts) {
        if (i % 2 == 1) {
            listOfNumbers += part
        } else {
            listOfSigns += part
        }
        i++
    }
    if (("-" in listOfNumbers) || ("+" in listOfNumbers)) {
        throw IllegalArgumentException()
    }
    try {
        result = listOfNumbers[0].toInt()
        for (j in 1 until listOfNumbers.size) {
            if (listOfSigns[j - 1] == "+") {
                result += listOfNumbers[j].toInt()
            } else {
                result -= listOfNumbers[j].toInt()
            }
        }
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException()
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.toLowerCase().split(" ")
    var list = listOf<String>()
    var s1: String
    var s2 = ""
    var a = 0
    var k = -1
    for (part in parts) {
        list += part
    }
    for (i in 0 until list.size) {
        s1 = list[i]
        if (s1 == s2) {
            a = i
            k -= list[i].length
            break
        }
        k += list[i].length
        s2 = list[i]
    }

    if (a == 0) return -1
    return k + a
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    var listOfPrices = listOf<Double>()
    var listOfProducts = listOf<String>()
    val parts = description.split("; ")
    for (part in parts) {
        val partOfParts = part.split(" ")
        if (partOfParts.size != 2) return ""
        listOfProducts += partOfParts[0]
        listOfPrices += partOfParts[1].toDouble()
    }
    val ind = listOfPrices.indexOf(listOfPrices.max())
    return if (ind == -1) "" else listOfProducts[ind]
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val map = mapOf("M" to 1000, "CM" to 900, "D" to 500, "CD" to 400, "C" to 100, "XC" to 90,
            "L" to 50, "XL" to 40, "X" to 10, "IX" to 9, "V" to 5, "IV" to 4, "I" to 1)
    var s = roman
    var s1: String
    var s2: String
    var n1: Int
    var n2: Int
    var result = -1
    if (s != "") {
        result++
    }
    while (s != "") {
        s1 = s.substring(0, 1)
        s2 = if (s.length > 1) s.substring(0, 2) else ""
        n1 = map[s1] ?: 0
        n2 = map[s2] ?: 0
        if (n1 == n2) return -1 else {
            if (n2 != 0) {
                result += n2
                s = s.substring(2)
            } else {
                result += n1
                s = s.substring(1)
            }
        }
    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val list = mutableListOf<Int>()
    var listFirst = listOf<Int>()
    var listSecond = listOf<Int>()
    for (i in 0 until cells) {
        list.add(0)
    }
    var k = 0
    var j: Int
    for (i in 0 until commands.length) {
        if (commands[i] == '[') {
            k++
            listFirst += i
            j = i + 1
            while (k != 0) {
                if (j == commands.length) throw IllegalArgumentException()
                if (commands[j] == ']') {
                    k--
                }
                if (commands[j] == '[') {
                    k++
                }
                j++
            }
            listSecond += j - 1
        }
        if ((commands[i] != '<') && (commands[i] != '>') &&
                (commands[i] != '+') && (commands[i] != '-') &&
                (commands[i] != '[') && (commands[i] != ']') &&
                (commands[i] != ' ')) {
            throw IllegalArgumentException()
        }
    }
    var sensor = cells / 2
    var count = 0
    var i = 0
    var command: Char
    while ((count < limit) && (i < commands.length)) {
        command = commands[i]
        if (command == ' ') {
            i++
        }
        if (command == '>') {
            sensor++
            i++
        }
        if (command == '<') {
            sensor--
            i++
        }
        if (command == '+') {
            list[sensor]++
            i++
        }
        if (command == '-') {
            list[sensor]--
            i++
        }
        if (command == '[') {
            if (list[sensor] == 0) {
                i = listSecond[listFirst.indexOf(i)] + 1
            } else {
                i++
            }
        }
        if (command == ']') {
            if (list[sensor] != 0) {
                i = listFirst[listSecond.indexOf(i)] + 1
            } else {
                i++
            }
        }
        count++
        if (sensor !in 0 until cells) {
            throw IllegalStateException()
        }
    }
    return list
}
