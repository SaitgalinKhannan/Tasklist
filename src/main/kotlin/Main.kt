fun menu(lst: MutableList<MutableList<String>>) {
    while (true) {
        println("Input an action (add, print, edit, delete, end):")

        when (readln().lowercase()) {
            "add" -> add(lst)
            "print" -> show(lst)
            "edit" -> edit(lst)
            "delete" -> delete(lst)
            "end" -> {
                println("Tasklist exiting!")
                break
            }
            else -> println("The input action is invalid")
        }
    }
}

fun main() {
    val lst = mutableListOf<MutableList<String>>()
    menu(lst)
    //println("\u001B[101m \u001B[0m")
}