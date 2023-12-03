package org.team9432.scoutingapp.annotation

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class TextInputField(val numberOnly: Boolean = false)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class InlineTextInputField(val numberOnly: Boolean = false)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DropdownInputField(vararg val options: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class NumberInputField(val max: Int = Int.MAX_VALUE, val min: Int = 0)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class CycleInputField(vararg val options: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class SwitchInputField


@Target(AnnotationTarget.CLASS)
annotation class InputBase