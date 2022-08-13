@file:OptIn(ExperimentalStdlibApi::class)
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
val jsonFile = File("tasklist.json")
//Moshi
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val jsonAdapter: JsonAdapter<MutableList<MutableList<String>>> = moshi.adapter<MutableList<MutableList<String>>>()

fun menu(lst: MutableList<MutableList<String>>) {
    while (true) {
        println("Input an action (add, print, edit, delete, end):")

        when (readln().lowercase()) {
            "add" -> add(lst)
            "print" -> show(lst)
            "edit" -> edit(lst)
            "delete" -> delete(lst)
            "end" -> {
                jsonFile.appendText(jsonAdapter.toJson(lst))
                println("Tasklist exiting!")
                break
            }
            else -> println("The input action is invalid")
        }
    }
}

fun main() {
    val str: String
    var lst = mutableListOf<MutableList<String>>()
    //if file not exist get date from json
    if (jsonFile.exists()) {
        str = jsonFile.readText().trimIndent()
        lst = jsonAdapter.fromJson(str)!!
    }
    menu(lst)
}