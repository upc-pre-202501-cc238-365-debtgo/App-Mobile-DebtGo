package com.example.debtgo.viewmodel.analysis

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EducationViewModel : ViewModel() {
    private val _courseProgress = mutableStateMapOf<String, Int>()
    val courseProgress: State<Map<String, Int>> = mutableStateOf(_courseProgress)

    fun getLastLessonForCourse(title: String): Int {
        return _courseProgress[title] ?: 1 // Devuelve 1 si no hay progreso
    }

    fun updateCourseProgress(title: String, lessonNumber: Int) {
        _courseProgress[title] = lessonNumber
        // Para que el State observe el cambio en el Map, se crea una nueva instancia
        (courseProgress as MutableState<Map<String, Int>>).value = _courseProgress.toMap()
    }

    fun completeCourse(title: String) {
        _courseProgress[title] = 6 // Marca como completado (6 lecciones)
        // Actualiza el State para que la UI se entere del cambio
        (courseProgress as MutableState<Map<String, Int>>).value = _courseProgress.toMap()
    }
}