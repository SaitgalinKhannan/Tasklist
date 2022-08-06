import kotlinx.datetime.*

fun addPriority(): String {

    var chP: String
    while (true) {
        println("Input the task priority (C, H, N, L):")
        chP = readln().lowercase()
        if (chP == "c" || chP == "h" || chP == "n" || chP == "l") {
            break
        }
    }
    return chP
}

fun addData(): String {

    var lst: MutableList<String>
    var chD: LocalDate
    while (true) {
        println("Input the date (yyyy-mm-dd):")
        try {
            lst = readln().split("-") as MutableList<String>
            if (lst[1].length == 1) {
                lst[1] = "0" + lst[1]
            }
            if (lst[2].length == 1) {
                lst[2] = "0" + lst[2]
            }
            chD = lst.joinToString("-").toLocalDate()
            break
        } catch (e: Exception) {
            println("The input date is invalid")
        }
    }
    return chD.toString()
}

fun addTime(): String {

    var lst: MutableList<String>
    var chT: LocalTime
    while (true) {
        println("Input the time (hh:mm):")
        try {
            lst = readln().split(":") as MutableList<String>
            if (lst[0].length == 1) {
                lst[0] = "0" + lst[0]
            }
            if (lst[1].length == 1) {
                lst[1] = "0" + lst[1]
            }
            chT = lst.joinToString(":").toLocalTime()
            break
        } catch (e: Exception) {
            println("The input time is invalid")
        }
    }
    return chT.toString()
}

fun menu(lst: MutableList<String>) {

    while (true) {
        println("Input an action (add, print, end):")

        when (readln().lowercase()) {
            "add" -> add(lst)
            "print" -> show(lst)
            "end" -> {
                println("Tasklist exiting!")
                break
            }
            else -> println("The input action is invalid")
        }
    }
}

fun add(lst: MutableList<String>) {

    var str = ""
    var substr: String
    var i = 0
    var chP: String
    var chD: String
    var chT: String
    var flag = false //Data and Time is empty

    while (true) {

        if (!flag) {
            chP = addPriority()
            chD = addData()
            chT = addTime()
            str += chD + " " + chT + " " + chP.uppercase() + "\n" + "   "
            println("Input a new task (enter a blank line to end):")
            flag = true //D and T notEmpty
        }

        substr = readln()

        if (substr.isEmpty() || substr.isBlank()) {
            break
        }

        str = if (i == 0) {
            str + substr.trim() + "\n"

        } else {
            str + "   " + substr.trim() + "\n"
        }
        i++
    }

    if (str.substringAfter("\n").isEmpty() || str.substringAfter("\n").isBlank()) {
        println("The task is blank")
    } else {
        lst.add(str)
    }
}

fun show(lst: MutableList<String>) {
    if (lst.isEmpty()) {
        println("No tasks have been input")
    } else {
        for (i in lst.indices) {
            println("${String.format("%-2s", i + 1)} ${lst[i]}")
        }
    }
}

fun main() {
    val lst = mutableListOf<String>()
    menu(lst)
}