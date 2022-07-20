import java.io.File

enum class SearchStrategies {
    ALL, ANY, NONE
}

fun showMenu() {

    println(
        "=== Menu ===\n" + "1. Search information.\n" + "2. Print all data.\n" + "0. Exit."
    )
}

fun showAllData(lst: List<String>) {

    for (item in lst) {
        println(item)
    }
}

fun inputFromFile(args: Array<String>): List<String> {

    val fileName = "C:/Users/Khann/OneDrive/Рабочий стол/KotlinJB/text.txt"
    var lines = emptyList<String>()

    if (args.isNotEmpty()) { //файл передается как аргумент в функцию search.main
        val file = File(args[1])

        if (file.exists()) {
            lines = file.readLines()
        }
    } else { //чтение файла, который находится в какой-то директории
        if (File(fileName).exists()) lines = File(fileName).readLines()
    }

    return lines
}

fun invertedIndex(lst: List<String>): MutableMap<String, MutableList<Int>> {

    val map: MutableMap<String, MutableList<Int>> = mutableMapOf()
    val allElemList = mutableListOf<String>()

    for (item in lst) { // получаем все слова по отдельности
        val list1 = item.split(" ")
        allElemList.addAll(list1)
    }

    for (i in allElemList) {
        val wordIndexes = mutableListOf<Int>()

        for (j in lst.indices) {
            if (i in lst[j]) {
                wordIndexes.add(j)
            }
        }
        map[i.lowercase()] = wordIndexes
    }

    return map
}

fun searchInfo(lst: List<String>, map: Map<String, MutableList<Int>>) {

    println("Select a matching strategy: ALL, ANY, NONE")
    val strategy = SearchStrategies.valueOf(readln())

    println("Enter a name or email to search all matching people.")
    val queries = readln().lowercase().split(" ")

    val result: List<String> = when (strategy) {
        SearchStrategies.ALL -> lst.filter { list -> queries.map { it in list.lowercase() }.all { it } }
        SearchStrategies.ANY -> map.filterKeys { it in queries }.values.flatten().map { lst[it] }
        SearchStrategies.NONE -> lst.filter { list -> queries.map { it !in list.lowercase() }.all { it } }
    }

    val numberResults = result.size

    println("$numberResults persons found:")
    result.forEach { println(it) }
}

fun main(args: Array<String>) {

    val lst = inputFromFile(args)
    val map = invertedIndex(lst)

    while (true) {
        showMenu()
        when (readln().toInt()) {
            1 -> searchInfo(lst, map)
            2 -> showAllData(lst)
            0 -> break
            else -> println("Incorrect option! Try again.")
        }
    }

}

