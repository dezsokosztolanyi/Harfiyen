package com.voyvo.dragdrop

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voyvo.dragdrop.data.use_cases.UseCases
import com.voyvo.dragdrop.util.Event
import com.voyvo.harfiyen.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
    private val _currentQuestion : MutableStateFlow<Question> = MutableStateFlow(rquestions.first())
        val currentQuestion: StateFlow<Question> = _currentQuestion

    private val _level : MutableStateFlow<Int> = MutableStateFlow(1)
        val level: StateFlow<Int> = _level

    private val _maxLevel : MutableStateFlow<Int> = MutableStateFlow(1)
        val maxLevel: StateFlow<Int> = _maxLevel

    var letters : List<Letter> by mutableStateOf(listOf())

    var transformableIndices : List<TransformableIndex> by mutableStateOf(listOf())
    var selectableIndices : List<SelectableIndex> by mutableStateOf(listOf())

    private val _gameState : MutableStateFlow<GameState> = MutableStateFlow(GameState.ANSWERING)
        val gameState: StateFlow<GameState> = _gameState

    private val _message : MutableStateFlow<Event<Int>> = MutableStateFlow(Event(R.string.initial))
        val message: StateFlow<Event<Int>> = _message

    private val _notes : MutableStateFlow<Int> = MutableStateFlow(-1)
        val notes: StateFlow<Int> = _notes

    private val _enabled : MutableStateFlow<Boolean> = MutableStateFlow(true)
        val enabled: StateFlow<Boolean> = _enabled

    private val _soundState : MutableStateFlow<Boolean> = MutableStateFlow(true)
        val soundState: StateFlow<Boolean> = _soundState

    private val _nextLevelId : MutableStateFlow<Int> = MutableStateFlow(2)
        val nextLevelId: StateFlow<Int> = _nextLevelId

    private val _gameOver : MutableStateFlow<Boolean> = MutableStateFlow(false)
        val gameOver: StateFlow<Boolean> = _gameOver

    private val _celebrateAnim : MutableStateFlow<Boolean> = MutableStateFlow(false)
        val celebrateAnim: StateFlow<Boolean> = _celebrateAnim

    var answerIsCorrectHandleGameStatus by mutableStateOf(false)
    fun changeCorrectAnswerHandlerStatus(boolean: Boolean){
        answerIsCorrectHandleGameStatus = boolean
    }

    init {
        fetchInitialData()
    }

    fun fetchInitialData(){
        viewModelScope.launch {
            _level.value = useCases.readLevelUseCase.invoke().stateIn(this).value ?: 1
            _maxLevel.value = useCases.readMaxLevelUseCase.invoke().stateIn(this).value ?: 1
            _soundState.value = useCases.readSoundStateUseCase.invoke().stateIn(this).value ?: true
            _currentQuestion.value = rquestions.first {
                level.value == it.id
            }
            initialSettings()
        }
    }


    private fun initialSettings() {
        letters = currentQuestion.value.letters
        setTransformableIndexes()
        setSelectableIndexes()
        _nextLevelId.value = currentQuestion.value.id
    }

    fun changeGameOverStatus(boolean: Boolean){
        _gameOver.value = boolean
    }



    fun changeSoundState(){
        val newSoundState = !soundState.value
        _soundState.value = newSoundState
        viewModelScope.launch {
            useCases.saveSoundStateUseCase.invoke(newSoundState)
        }
    }

    fun postMessage(@StringRes message : Int){
        _message.value = Event(message)
    }


    fun moveLetter(from: ItemPosition, to: ItemPosition) {
        if (gameState.value == GameState.ANSWERING && !answerIsCorrectHandleGameStatus){
            letters = letters.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
            playSound(from.index,to.index)
            checkForWin()
        }
    }

    private fun playSound(from: Int, to: Int) {

        println("SOUND: $from $to")
        if (from > to){
            //ses inceliyor
            if (from >= 11){
                println("DECIDED SOUND: 12")
                resetNoteValueAndSetNewOne(12)
            }
            else if (from < 1){
                println("DECIDED SOUND: 1")
                resetNoteValueAndSetNewOne(1)
            }else{
                println("DECIDED SOUND: $from")
                resetNoteValueAndSetNewOne(from + 1)
            }

        }else{
            //ses kalınlaşıyor
            if (from < 1){
                println("DECIDED SOUND: 1")
                resetNoteValueAndSetNewOne(1)
            }else if(from >= 11){
                println("DECIDED SOUND: 12")
                resetNoteValueAndSetNewOne(12)
            }else{
                println("DECIDED SOUND: $from")
                resetNoteValueAndSetNewOne(from + 1)
            }

        }
    }

    private fun resetNoteValueAndSetNewOne(note : Int){
        if (soundState.value){
            viewModelScope.launch {
                _notes.value = -1
                delay(50)
                _notes.value = note
            }
        }
    }

    private fun checkForWin(){

        when(currentQuestion.value.type){
            QuestionType.DRAGGABLE -> {
                val newWord = letters.map {
                    it.letter
                }.joinToString(separator = "")
                println(newWord)

                if (newWord.contains(currentQuestion.value.answer)){
                    println("correct answer")
                    correctAnswer()
                }else{
                    println("wrong answer")
                }
            }
            QuestionType.SELECTABLE -> {
                var newWord = ""

               val selects = selectableIndices.filter {
                    it.selected
                }.map {
                    it.id
                }

                letters.forEach {
                    if (selects.contains(it.id)){
                        newWord += it.letter
                    }
                }

                println(newWord)

                if (currentQuestion.value.answer == newWord){
                    println("correct answer")
                    correctAnswer()
                }else{
                    println("wrong answer")
                }
            }
            QuestionType.TRANSFORMABLE -> {
                val newWord = letters.map {
                    it.letter
                }.joinToString(separator = "")
                println(newWord)

                if (newWord.contains(currentQuestion.value.answer)){
                    println("correct answer")
                    correctAnswer()
                }else{
                    println("wrong answer")
                }
            }
            QuestionType.DRAGGABLE_SELECTABLE -> {
               var newWord = ""
                letters.forEach { letter ->
                    if (letter.type == Type.SELECTABLE){
                        if (getSelectStatus(letter.id)){
                            newWord += letter.letter
                        }
                    }else{
                        newWord += letter.letter
                    }
                }

                println(newWord)

                if (newWord == currentQuestion.value.answer){
                    println("correct answer")
                    correctAnswer()
                }else{
                    println("wrong answer")
                }
            }
            QuestionType.DRAGGABLE_TRANSFORMABLE -> {
                val newWord = letters.map {
                    it.letter
                }.joinToString(separator = "")
                println(newWord)

                if (newWord.contains(currentQuestion.value.answer)){
                    println("correct answer")
                    correctAnswer()
                }else{
                    println("wrong answer")
                }
            }
            QuestionType.TRANSFORMABLE_SELECTABLE -> {
                var newWord = ""
                letters.forEach { letter ->
                    if (letter.type == Type.SELECTABLE){
                        if (getSelectStatus(letter.id)){
                            newWord += letter.letter
                        }
                    }else{
                        newWord += letter.letter
                    }
                }

                println(newWord)

                if (newWord == currentQuestion.value.answer){
                    println("correct answer")
                    correctAnswer()
                }else{
                    println("wrong answer")
                }
            }
            QuestionType.MIX -> {
                var newWord = ""
                letters.forEach { letter ->
                    if (letter.type == Type.SELECTABLE){
                        if (getSelectStatus(letter.id)){
                            newWord += letter.letter
                        }
                    }else{
                        newWord += letter.letter
                    }
                }

                println(newWord)

                if (newWord == currentQuestion.value.answer){
                    println("correct answer")
                    correctAnswer()
                }else{
                    println("wrong answer")
                }
            }
        }
    }

    private fun correctAnswer(){
        viewModelScope.launch {
            changeCorrectAnswerHandlerStatus(true)
            postMessage(R.string.correct)
            resetNoteValueAndSetNewOne(999)
//            delay(1000)
            _celebrateAnim.value = false
            changeGameState(GameState.ANIMATION)
        }
    }

     fun skipQuestion(){
        postMessage(R.string.skip)
        resetNoteValueAndSetNewOne(999)
        changeGameState(GameState.ANIMATION)
    }

    private fun changeGameState(gameState: GameState){
        viewModelScope.launch {
            _enabled.value = false
            delay(500)
            _gameState.value = gameState
            if (gameState == GameState.ANIMATION){
                println("anim showed")
                delay(1400)
                println("anim showed 1 sec delay")
                getNextQuestion()
                println("anim showednexr q")
                delay(800)
                println("anim showed 2 sec delay")
                _gameState.value = GameState.ANSWERING
                changeCorrectAnswerHandlerStatus(false)
                _enabled.value = true
            }
        }
    }

    fun goSelectedLevel(id : Int){
        _currentQuestion.value = rquestions.first {
            it.id == id
        }
        transformableIndices = listOf()
        selectableIndices = listOf()
        initialSettings()
        saveNewLevel()
    }

    private fun getNextQuestion(){
        if (rquestions.size >= currentQuestion.value.id + 1){
            _currentQuestion.value = rquestions.first {
                it.id == currentQuestion.value.id + 1
            }
            transformableIndices = listOf()
            selectableIndices = listOf()
            initialSettings()
            saveNewLevel()
            checkNewMaxLevel()
        }else{
            //oyun bitti
            changeGameOverStatus(true)
            viewModelScope.launch {
                delay(1000)
                _currentQuestion.value = rquestions.first {
                    1 == it.id
                }
                initialSettings()
                resetNoteValueAndSetNewOne(1000)
            }
        }
    }

    private fun checkNewMaxLevel() {
        viewModelScope.launch {
            val level = currentQuestion.value.id
            if (maxLevel.value < level){
                useCases.saveMaxLevelUseCase.invoke(level)
                _maxLevel.value = level
            }
        }
    }

    private fun saveNewLevel() {
        viewModelScope.launch {
            val level = currentQuestion.value.id
            useCases.saveLevelUseCase.invoke(level)
            _level.value = level
        }
    }



    private fun setTransformableIndexes(){
        currentQuestion.value.letters.filter {
            it.type == Type.TRANSFORMABLE
        }.forEach {
            transformableIndices = transformableIndices + TransformableIndex(it.id,0)
        }
        println(transformableIndices)
    }

    private fun setSelectableIndexes(){
        currentQuestion.value.letters.filter {
            it.type == Type.SELECTABLE
        }.forEach {
            selectableIndices = selectableIndices + SelectableIndex(it.id,it.selected)
        }
        println(selectableIndices)
    }

    fun letterClicked(id: Int){
        val type = letters.first {
            it.id == id
        }.type

        if (type == Type.TRANSFORMABLE){
            transformableLetterClicked(id)
        }else{
            selectableLetterClicked(id)
        }
    }

    private fun selectableLetterClicked(id: Int) {
        resetNoteValueAndSetNewOne(100)
        val selectableIndexItem = selectableIndices.first {
            it.id == id
        }
        println("selectableIndexItem ${selectableIndexItem}")

        val indexOfTheItemWillModify = letters.indexOfFirst {
            it.id == id
        }
        println("indexOfTheItemWillModifierForSelectable ${indexOfTheItemWillModify}")

        val selectableIndexOfTheItemWillModify = selectableIndices.indexOfFirst {
            it.id == id
        }
        println("selectableIndexOfTheItemWillModify ${selectableIndexOfTheItemWillModify}")

        letters[indexOfTheItemWillModify].selected = !letters[indexOfTheItemWillModify].selected
        selectableIndices[selectableIndexOfTheItemWillModify].selected = letters[indexOfTheItemWillModify].selected
        println(letters)

        //select sound
        resetNoteValueAndSetNewOne(playTransformableAndSelectedSounds(transformable = false,isSelected = selectableIndices[selectableIndexOfTheItemWillModify].selected))


        checkForWin()
    }


    fun transformableLetterClicked(id : Int){

        val transformableIndexItem = transformableIndices.first {
            it.id == id
        }
        println("transformableIndexItem ${transformableIndexItem}")

        val indexOfTheItemWillModify = letters.indexOfFirst {
            it.id == id
        }
        println("indexOfTheItemWillModifierForTransformable ${indexOfTheItemWillModify}")


        val transformableIndexOfTheItemWillModifier = transformableIndices.indexOfFirst {
            it.id == id
        }
        println("transformableIndexOfTheItemWillModifier ${transformableIndexOfTheItemWillModifier}")

        if (transformableIndexItem.index + 1 > 2){
            println("transformableIndexItem.index + 1 == 2")
            letters[indexOfTheItemWillModify].letter = letters[indexOfTheItemWillModify].transformableLetters[0]
            transformableIndices[transformableIndexOfTheItemWillModifier].index = 0
            println(letters)
        }else{
            println("transformableIndexItem.index + 1 != 2")
            letters[indexOfTheItemWillModify].letter = letters[indexOfTheItemWillModify].transformableLetters[transformableIndexItem.index + 1]
            transformableIndices[transformableIndexOfTheItemWillModifier].index = transformableIndexItem.index + 1
            println(letters)
        }

        //transformable sound
        resetNoteValueAndSetNewOne(playTransformableAndSelectedSounds(transformable = true, index = transformableIndices[transformableIndexOfTheItemWillModifier].index))

        checkForWin()
    }

    private fun playTransformableAndSelectedSounds(transformable : Boolean, index: Int = 0, isSelected : Boolean = false) : Int{
        val soundCode: Int
        if (transformable){
            //transformable
            soundCode = when(index){
                0 -> {
                    200
                }
                1 -> {
                    201
                }
                2 -> {
                    202
                }
                else -> {
                    200
                }
            }
        }else{
            //selectable
            soundCode = if (isSelected){
                //seçildi
                100
            }else{
                //seçim iptal edildi
                101
            }
        }
        println("PLAYSOUND T- $soundCode")
        return soundCode
    }



    fun getNewLetterAfterClick(id: Int) : String{
        return letters.first {
            it.id == id
        }.letter
    }

    fun getNewIconAfterClick(id : Int) : Int{
        val selectedStatus = selectableIndices.first {
            it.id == id
        }.selected
        return if (selectedStatus) R.drawable.ic_selected else R.drawable.ic_deselected
    }

    fun getSelectStatus(id: Int) : Boolean{
        println("selectStatus $id")
        return selectableIndices.first {
            it.id == id
        }.selected
    }

}

enum class GameState{
    ANSWERING,ANIMATION
}

data class TransformableIndex(
    val id : Int,
    var index : Int
)

data class SelectableIndex(
    val id : Int,
    var selected : Boolean
)

