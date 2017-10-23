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
    var day = 0
    var year = 0
    var month = 0
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
    var list = listOf<Char>()
    for (j in 0 until phone.length) {
        if ((phone[j] != ' ') && (phone[j] != '-') && (phone[j] != '(') && (phone[j] != ')')) {
            list += phone[j]
        }
    }
    for (i in 0 until list.size) {
        if ((i != 0) || ((i == 0) && (list[0] != '+'))) {
            try {
                list[i].toString().toInt()
            } catch (e: NumberFormatException) {
                return ""
            }
        }
    }
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
    val s = StringBuilder()
    val list = mutableListOf<Int>()
    var b = -1
    for (char in jumps) {
        if ((char != '%') && (char != '-')) {
            s.append(char)
        }
    }
    val parts = s.split(" ")
    try {
        for (part in parts) {
            if (part != "") list.add(part.toInt())
        }
    } catch (e: NumberFormatException) {
        return -1
    }
    for (element in list) {
        if (element >= b) {
            b = element
        }
    }
    return b
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
    var result = 0
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
    var s = ""
    var a = 0
    var k = 0
    for (part in parts) {
        list += part
    }
    if (list.size > 1) {
        for (i in 0 until list.size - 1) {
            s = list[i]
            if (s == list[i + 1]) {
                a = i
                break
            }
        }
    }
    if ((list.size < 2) || ((list.size > 2) && (a == 0))) return -1
    for (j in 0 until a) {
        k += list[j].length
    }
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
    var s = ""
    val parts = description.split(";")
    for (part in parts) {
        val partOfParts = part.split(" ")
        var i = 1
        for (part2 in partOfParts) {
            if (part2 != "") {
                if (i % 2 == 1) {
                    listOfProducts += part2
                } else {
                    listOfPrices += part2.toDouble()
                }
                i++
            }
        }
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
    val listOfLetters = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    val listOfNumbers = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    var s = roman
    var result = -1
    if (s != "") {
        result++
    }
    while (s != "") {
        try {
            if (s.length > 1) {
                var s1 = s.substring(0, 2)
                if (s1 in listOfLetters) {
                    result += listOfNumbers[listOfLetters.indexOf(s1)]
                    s = s.substring(2)
                } else {
                    s1 = s.substring(0, 1)
                    result += listOfNumbers[listOfLetters.indexOf(s1)]
                    s = s.substring(1)
                }
            } else {
                result += listOfNumbers[listOfLetters.indexOf(s)]
                break
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            return -1
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
    val list1 = mutableListOf<String>()
    val list2 = mutableListOf<Int>()
    var count1 = 0
    var count2 = 0
    for (i in 0 until cells) {
        list.add(0)
    }
    for (i in 0 until commands.length) {
        if ((commands[i] != '<') && (commands[i] != '>') &&
                (commands[i] != '+') && (commands[i] != '-') &&
                (commands[i] != '[') && (commands[i] != ']') &&
                (commands[i] != ' ')) {
            throw IllegalArgumentException()
        }
        if (commands[i] == '[') {
            count1++
            list1.add(commands[i].toString())
            list2.add(i)
        }
        if (commands[i] == ']') {
            count2++
            list1.add(commands[i].toString())
            list2.add(i)
        }
    }

    if (count1 != count2) {
        throw IllegalArgumentException()
    }
    var listFirst = listOf<Int>()
    var listSecond = listOf<Int>()
    var j = 0
    while (list1.isNotEmpty()) {
        if (list1[j] + list1[j + 1] == "[]") {
            listFirst += list2[j]
            listSecond += list2[j + 1]
            list1.removeAt(j)
            list1.removeAt(j)
            list2.removeAt(j)
            list2.removeAt(j)
            if (j > 0) {
                j--
            }
        } else {
            j++
        }
    }
    var sensor = cells / 2
    var count = 0
    val s = commands
    var i = 0
    var command = ' '
    while ((count < limit) && (i < s.length)) {
        command = s[i]
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
    }
    return list
}
