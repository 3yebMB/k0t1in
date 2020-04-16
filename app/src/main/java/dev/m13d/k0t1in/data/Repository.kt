package dev.m13d.k0t1in.data

import dev.m13d.k0t1in.data.model.Note

object Repository {
    private val notes: List<Note> = listOf(
            Note("Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            0xfff06292.toInt()),
            Note("Моя вторая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            0xff9575cd.toInt()),
            Note("Моя третья заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            0xff64b5f6.toInt()),
            Note("Моя четвертая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            0xff4db6ac.toInt()),
            Note("Моя пятая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            0xffb2ff59.toInt()),
            Note("Моя шестая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            0xffffeb3b.toInt()),
            Note("Моя седьмая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            0xffff6e40.toInt())
    )

    fun getNote(): List<Note> {
        return notes
    }
}