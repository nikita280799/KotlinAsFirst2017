@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr
import lesson3.task1.cos
import lesson3.task1.factorial
import lesson3.task1.powInt
import lesson3.task1.sin
import lesson4.task1.powInt
import java.awt.geom.Point2D.distance
import java.lang.Math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        return if (center.distance(other.center) >= radius + other.radius) {
            center.distance(other.center) - (radius + other.radius)
        } else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException()
    var max = 0.0
    var point1 = points[0]
    var point2 = points[0]
    for (i in 0 until points.size - 1) {
        for (j in i + 1 until points.size) {
            if (points[i].distance(points[j]) >= max) {
                point1 = points[i]
                point2 = points[j]
                max = points[i].distance(points[j])
            }
        }
    }
    return Segment(point1, point2)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = Circle(Point((diameter.begin.x + diameter.end.x) / 2,
        (diameter.begin.y + diameter.end.y) / 2),
        diameter.begin.distance(diameter.end) / 2)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val k1 = Math.sin(angle) / Math.cos(angle)
        val b1 = b / Math.cos(angle)
        val k2 = Math.sin(other.angle) / Math.cos(other.angle)
        val b2 = other.b / Math.cos(other.angle)
        val x = (b1 - b2) / (k2 - k1)
        val y = if (angle < other.angle) k1 * x + b1 else k2 * x + b2
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line = lineByPoints(s.begin, s.end)

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    if ((a.x == b.x) || (a.y == b.y)) {
        return if (a.x == b.x) Line(a, PI / 2)
        else Line(a, 0.0)
    }
    val tan = (a.y - b.y) / (a.x - b.x)
    var c = atan(tan)
    if (c < 0) c += PI
    return Line(a, c)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val point = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    var ang = lineByPoints(a, b).angle
    ang += if (ang in PI / 2..PI) -PI / 2 else PI / 2
    return Line(point, ang)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var circle1 = circles[0]
    var circle2 = circles[0]
    var min = circles[0].distance(circles[1])
    for (i in 0 until circles.size - 1) {
        for (j in i + 1 until circles.size) {
            if (circles[i].distance(circles[j]) <= min) {
                circle1 = circles[i]
                circle2 = circles[j]
                min = circles[i].distance(circles[j])
                if (min == 0.0) return Pair(circle1, circle2)
            }
        }
    }
    return Pair(circle1, circle2)
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val point1 = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    val point2 = Point((a.x + c.x) / 2, (a.y + c.y) / 2)
    val x: Double
    val y: Double
    val k1 = (a.y - b.y) / (a.x - b.x)
    val k2 = (a.y - c.y) / (a.x - c.x)
    val kb1 = -1 / k1
    val kb2 = -1 / k2
    if ((a.x == b.x) || (a.y == b.y) || (a.x == c.x) || (a.y == c.y)) {
        if ((a.x == b.x) || (a.x == c.x)) {
            if (a.x == b.x) {
                y = point1.y
                x = if (a.y != c.y) (y - point2.y + kb2 * point2.x) / kb2 else point2.x
            } else {
                y = point2.y
                x = if (a.y != b.y) (y - point1.y + kb1 * point1.x) / kb1 else point1.x
            }
        } else {
            if (a.y == b.y) {
                x = point1.x
                y = if (a.x != b.x) kb2 * (x - point2.x) + point2.y else point2.y
            } else {
                x = point2.x
                y = if (a.x != c.x) kb1 * (x - point1.x) + point1.y else point1.y
            }
        }
    } else {
        x = (point2.y - point1.y + kb1 * point1.x - kb2 * point2.x) / (kb1 - kb2)
        y = kb1 * (x - point1.x) + point1.y
    }
    val point = Point(x, y)
    return Circle(point, point.distance(a))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()
    if (points.size == 1) return Circle(points[0], 0.0)
    val s = diameter(*points)
    val c = circleByDiameter(s)
    var m = Point(0.0, 0.0)
    var max = 0.0
    val list = mutableListOf<Point>()
    for (i in 0 until points.size) {
        if (!c.contains(points[i])) {
            list.add(points[i])
            if (points[i].distance(c.center) > max) {
                max = points[i].distance(c.center)
                m = points[i]
            }
        }
    }
    return if (max == 0.0) c else circleByThreePoints(s.begin, s.end, m)
}

