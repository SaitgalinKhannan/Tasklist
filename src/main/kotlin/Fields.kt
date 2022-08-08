import kotlinx.datetime.*

fun show(lst: MutableList<MutableList<String>>) {
    val spaces = "                                            "
    var s: String
    var splited: List<String>
    if (lst.isEmpty()) {
        println("No tasks have been input")
    } else {
        println(
            "+----+------------+-------+---+---+--------------------------------------------+\n" +
                    "| N  |    Date    | Time  | P | D |                   Task                     |\n" +
                    "+----+------------+-------+---+---+--------------------------------------------+"
        )
        for (i in lst.indices) {
            s = String.format("%-2s", i + 1)
            splited = lst[i][4].split("\n")
            println(
                "| $s | ${lst[i][0]} | ${lst[i][1]} | ${lst[i][2]} | ${lst[i][3]} |${lst[i][4].substringBefore("\n")}" +
                        "${spaces.substring(0, 44 - lst[i][4].substringBefore("\n").length)}|"
            )

            for (j in splited.subList(1, splited.size)) {
                println(
                    "|    |            |       |   |   |${j.substringBefore("\n")}" +
                            "${spaces.substring(0, 44 - j.substringBefore("\n").length)}|"
                )
            }
            println("+----+------------+-------+---+---+--------------------------------------------+")
        }

    }
}

fun delete(lst: MutableList<MutableList<String>>) {
    var ch: Int
    show(lst)
    if (lst.isNotEmpty()) {
        while (true) {
            println("Input the task number (1-${lst.lastIndex + 1}):")
            try {
                ch = readln().toInt()
            } catch (e: NumberFormatException) {
                println("Invalid task number")
                continue
            }
            if (ch in 1..lst.lastIndex + 1) {
                lst.removeAt(ch - 1)
                println("The task is deleted")
                break
            } else {
                println("Invalid task number")
            }
        }
    }
}

fun edit(lst: MutableList<MutableList<String>>) {
    var chElem: Int
    var chField: String
    val chP: String
    val chD: String
    val chT: String
    show(lst)
    if (lst.isNotEmpty()) {
        loop1@ while (true) {
            println("Input the task number (1-${lst.size}):")
            try {
                chElem = readln().toInt()
            } catch (e: NumberFormatException) {
                println("Invalid task number")
                continue@loop1
            }

            if (chElem !in 1..lst.size) {
                println("Invalid task number")
                continue@loop1
            }

            loop2@ while (true) {
                println("Input a field to edit (priority, date, time, task):")
                chField = readln()
                when (chField) {
                    "priority" -> {
                        chP = addPriority()
                        editing(lst, chElem, 2, chP)
                        println("The task is changed")
                        break@loop1
                    }
                    "date" -> {
                        chD = addData()
                        editing(lst, chElem, 0, chD)
                        println("The task is changed")
                        break@loop1
                    }
                    "time" -> {
                        chT = addTime()
                        editing(lst, chElem, 1, chT)
                        println("The task is changed")
                        break@loop1
                    }
                    "task" -> {
                        editAddElem(lst, chElem)
                        println("The task is changed")
                        break@loop1
                    }
                    else -> {
                        println("Invalid field")
                        continue@loop2
                    }
                }
            }
        }
    }
}

fun editing(lst: MutableList<MutableList<String>>, chElem: Int, num: Int, pdt: String) {

    if (num == 0) {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date
        val numberOfDays = currentDate.daysUntil(lst[chElem - 1][0].toLocalDate())
        lst[chElem - 1][3] = addPrioColor(numberOfDays)
    }
    lst[chElem - 1][num] = pdt
}

fun editAddElem(lst: MutableList<MutableList<String>>, chElem: Int) {
    var str = ""
    var substr: String
    var flag = true
    var len: Int
    var tmpstr = ""

    println("Input a new task (enter a blank line to end):")
    while (flag) {
        //add tasks
        substr = readln()
        if (substr.isEmpty() || substr.isBlank()) {
            flag = false
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
    //changeInList
    if (str.substringAfter("\n").isEmpty() || str.substringAfter("\n").isBlank()) {
        println("The task is blank")
    } else {
        str = str.substringBeforeLast("\n")
        lst[chElem - 1][4] = str
    }
}