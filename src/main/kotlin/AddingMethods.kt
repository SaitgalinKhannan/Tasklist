import kotlinx.datetime.*

fun add(lst: MutableList<MutableList<String>>) {
    var str = ""
    var substr: String
    val sublist = mutableListOf<String>()
    var flag1 = true
    var flag2 = false //Data and Time is empty
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date
    var len: Int
    var tmpstr = ""

    while (flag1) {
        //add Data, Time, Priority
        if (!flag2) {
            sublist.add(addData())
            sublist.add(addTime())
            sublist.add(addPriority())
            val numberOfDays = currentDate.daysUntil(sublist[0].toLocalDate())
            val tmp = addPrioColor(numberOfDays)
            sublist.add(tmp)
            println("Input a new task (enter a blank line to end):")
            flag2 = true //D and T notEmpty
        }
        //add tasks
        substr = readln()
        if (substr.isEmpty() || substr.isBlank()) {
            flag1 = false
        } else if (substr.length > 44) {
            len = substr.length / 44
            loop@ for (i in 0..len) {
                if (i == len) {
                    tmpstr += "${substr.substring(44 * i, substr.length)}\n"
                    break@loop
                }
                tmpstr += "${substr.substring(44 * i, 44 * (i + 1))}\n"
            }
            str = tmpstr
            tmpstr = ""
        } else {
            str = "$str${substr.trim()}\n"
        }
    }
    //add BigTask to list
    if (str.substringAfter("\n").isEmpty() || str.substringAfter("\n").isBlank()) {
        println("The task is blank")
    } else {
        str = str.substringBeforeLast("\n")
        sublist.add(str)
        lst.add(sublist)
    }
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

fun addPriority(): String {
    var chP: String
    while (true) {
        println("Input the task priority (C, H, N, L):")
        chP = readln().lowercase()
        if (chP == "c" || chP == "h" || chP == "n" || chP == "l") {
            break
        }
    }
    return when (chP.lowercase()) {
        "c" -> "\u001B[101m \u001B[0m"
        "h" -> "\u001B[102m \u001B[0m"
        "n" -> "\u001B[103m \u001B[0m"
        "l" -> "\u001B[104m \u001B[0m"
        else -> ""
    }
}

fun addPrioColor(numberOfDays: Int): String {
    return if (numberOfDays == 0) {
        "\u001B[102m \u001B[0m"
    } else if (numberOfDays > 0) {
        "\u001B[103m \u001B[0m"
    } else {
        "\u001B[101m \u001B[0m"
    }
}