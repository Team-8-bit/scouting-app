package org.team9432.scoutingapp.annotation

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class TextInputField(val initialValue: String, val numberOnly: Boolean = false)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class InlineTextInputField(val initialValue: String, val numberOnly: Boolean = false)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DropdownInputField(val initialValue: String, vararg val additionalOptions: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class NumberInputField(val initialValue: Int, val max: Int = Int.MAX_VALUE, val min: Int = 0)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class CycleInputField(val initialValue: String, vararg val additionalOptions: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class SwitchInputField(val initialValue: Boolean)


@Target(AnnotationTarget.CLASS)
annotation class DataScreen