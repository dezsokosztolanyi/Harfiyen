package com.voyvo.dragdrop

val questions = listOf(
    Question(id = 1,
        type = QuestionType.MIX,
        question = "... BALIKÇISI",
        answer = "HALİKARNAS",
        letters = listOf(
            Letter(
                id = 0,
                letter = "H",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("H","B","C")
            ),Letter(
                id = 1,
                letter = "A",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf("A","B","C")
            ),Letter(
                id = 2,
                letter = "L",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 3,
                letter = "İ",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("İ","J","L")
            ),Letter(
                id = 4,
                letter = "K",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 5,
                letter = "A",
                type = Type.SELECTABLE,
                selected = true,
                transformableLetters = listOf()
            ),Letter(
                id = 6,
                letter = "R",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 7,
                letter = "N",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("N","B","C")
            ),Letter(
                id = 8,
                letter = "A",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 9,
                letter = "S",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            )
            ,Letter(
                id = 10,
                letter = "X",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            ),
        )),
    Question(id = 2,
        type = QuestionType.TRANSFORMABLE_SELECTABLE,
        question = "ANNENİN KOCASI",
        answer = "BABA",
        letters = listOf(
            Letter(
                id = 0,
                letter = "A",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("A","B","C")
            ),Letter(
                id = 1,
                letter = "A",
                type = Type.SELECTABLE,
                selected = true,
                transformableLetters = listOf()
            ),Letter(
                id = 2,
                letter = "A",
                type = Type.SELECTABLE,
                selected = true,
                transformableLetters = listOf()
            ),Letter(
                id = 3,
                letter = "A",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("A","B","C")
            )
        )),
    Question(id = 3,
        type = QuestionType.SELECTABLE,
        question = "LANET PARTİ",
        answer = "AKP",
        letters = listOf(
            Letter(
                id = 0,
                letter = "A",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 1,
                letter = "K",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 2,
                letter = "C",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 3,
                letter = "İ",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf())
            ,Letter(
                id = 4,
                letter = "X",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 5,
                letter = "P",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            )
        )),
    Question(id = 4,
        type = QuestionType.DRAGGABLE_SELECTABLE,
        question = "MERHABA",
        answer = "SELAM",
        letters = listOf(
            Letter(
                id = 0,
                letter = "A",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 1,
                letter = "S",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 2,
                letter = "L",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 3,
                letter = "E",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 4,
                letter = "T",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 5,
                letter = "M",
                type = Type.SELECTABLE,
                selected = false,
                transformableLetters = listOf()
            ),

            )),
    Question(id = 5,
        type = QuestionType.DRAGGABLE_TRANSFORMABLE,
        question = "Adın",
        answer = "RASİM",
        letters = listOf(
            Letter(
                id = 0,
                letter = "A",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("A","S","R")
            ),Letter(
                id = 1,
                letter = "S",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 2,
                letter = "İ",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 3,
                letter = "M",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 4,
                letter = "A",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            )

        )),
    Question(id = 6,
        type = QuestionType.DRAGGABLE,
        question = "Soyadın",
        answer = "GÜRGEY",
        letters = listOf(
            Letter(
                id = 0,
                letter = "Y",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 1,
                letter = "G",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 2,
                letter = "Ü",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 3,
                letter = "G",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 4,
                letter = "E",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 5,
                letter = "R",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            )

        )),
    Question(id = 7,
        type = QuestionType.DRAGGABLE,
        question = "Başkent",
        answer = "ANKARA",
        letters = listOf(
            Letter(
                id = 0,
                letter = "N",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 1,
                letter = "A",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 2,
                letter = "R",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 3,
                letter = "A",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 4,
                letter = "K",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            ),Letter(
                id = 5,
                letter = "A",
                type = Type.DRAGGABLE,
                selected = false,
                transformableLetters = listOf()
            )

        )),
    Question(id = 8,
        type = QuestionType.TRANSFORMABLE,
        question = "KUZEYLİ MÜZİK GRUBU",
        answer = "ABBA",
        letters = listOf(
            Letter(
                id = 0,
                letter = "A",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("A","B","C")
            ),Letter(
                id = 1,
                letter = "A",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("A","B","C")
            ),Letter(
                id = 2,
                letter = "A",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("A","B","C")
            ),Letter(
                id = 3,
                letter = "A",
                type = Type.TRANSFORMABLE,
                selected = false,
                transformableLetters = listOf("A","B","C")
            )
        )),






)


data class Question(
    val id : Int,
    val type : QuestionType,
    val question : String,
    val answer : String,
    val letters : List<Letter>
)

data class Letter(
    val id : Int,
    var letter : String,
    val type : Type,
    var selected : Boolean,
    val transformableLetters : List<String>
)

enum class Type {
    DRAGGABLE,TRANSFORMABLE,SELECTABLE
}

enum class QuestionType{
    DRAGGABLE,SELECTABLE,TRANSFORMABLE,DRAGGABLE_TRANSFORMABLE,DRAGGABLE_SELECTABLE,TRANSFORMABLE_SELECTABLE,MIX
}
