package org.team9432.scoutingapp.annotation

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class AnnotationProcessor(private val codeGenerator: CodeGenerator): SymbolProcessor {
    private val modifierType = ClassName("androidx.compose.ui", "Modifier")

    private val inputTypes = listOf(
        ClassName("org.team9432.scoutingapp.ui", "TextInput"),
        ClassName("org.team9432.scoutingapp.ui", "InlineTextInput"),
        ClassName("org.team9432.scoutingapp.ui", "DropdownInput"),
        ClassName("org.team9432.scoutingapp.ui", "NumberInput"),
        ClassName("org.team9432.scoutingapp.ui", "CycleInput"),
        ClassName("org.team9432.scoutingapp.ui", "SwitchInput"),
    )

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotatedClasses = resolver.getSymbolsWithAnnotation(InputBase::class.qualifiedName!!).filterIsInstance<KSClassDeclaration>()
        annotatedClasses.forEach { process(it) }
        return annotatedClasses.filterNot { it.validate() }.toList()
    }

    private fun process(classDeclaration: KSClassDeclaration) {
        if (!classDeclaration.modifiers.contains(Modifier.DATA)) throw Exception("""[Annotation] Please ensure the class you are annotating (${classDeclaration.simpleName.asString()}) is a data class!""")

        val classType = classDeclaration.asStarProjectedType().toClassName()
        val parameters = classDeclaration.primaryConstructor?.parameters

        val packageName = classDeclaration.packageName.asString()
        val className = classDeclaration.simpleName.asString()

        val newClassName = "${className}Inputs"

        val functions = parameters?.map { generateComposable(it) }!!

        val constructor = FunSpec.constructorBuilder()
            .addParameter("updateData", LambdaTypeName.get(returnType = ClassName("kotlin", "Unit"), parameters = arrayOf(LambdaTypeName.get(returnType = classType, parameters = arrayOf(classType)))))
            .addParameter("initialData", classType)
            .addParameter(ParameterSpec.builder("enabled", Boolean::class).defaultValue("true").build())
            .addParameter(ParameterSpec.builder("defaultModifier", LambdaTypeName.get(returnType = modifierType, receiver = modifierType)).defaultValue("{ this }").build())
            .build()

        val type = TypeSpec.classBuilder(newClassName)
            .primaryConstructor(constructor)
            .addProperty(
                PropertySpec.builder(
                    "updateData",
                    LambdaTypeName.get(returnType = ClassName("kotlin", "Unit"), parameters = arrayOf(LambdaTypeName.get(returnType = classType, parameters = arrayOf(classType))))
                ).initializer("updateData").build()
            )
            .addProperty(PropertySpec.builder("initialData", classType).initializer("initialData").build())
            .addProperty(PropertySpec.builder("enabled", Boolean::class).initializer("enabled").build())
            .addProperty(PropertySpec.builder("defaultModifier", LambdaTypeName.get(returnType = modifierType, receiver = modifierType)).initializer("defaultModifier").build())
            .addFunctions(functions)


        val fileBuilder = FileSpec.builder(packageName, newClassName).addType(type.build())
        inputTypes.forEach { fileBuilder.addImport(it, "") }
        fileBuilder.addImport("androidx.core.text", "isDigitsOnly")
        val file = fileBuilder.build()
        file.writeTo(codeGenerator, Dependencies(true, classDeclaration.containingFile!!))
    }

    private fun generateComposable(it: KSValueParameter): FunSpec {
        val annotation = it.annotations.first()
        val builder = FunSpec.builder(it.name!!.asString().capitalize())
        val parameterName = it.name!!.asString()

        builder.addParameter(ParameterSpec.builder("modifier", modifierType).defaultValue("Modifier").build())
        builder.addParameter(ParameterSpec.builder("title", String::class).defaultValue(CodeBlock.of("%S", it.name!!.asString().capitalize().titleize())).build())
        builder.addAnnotation(ClassName("androidx.compose.runtime", "Composable"))

        when (annotation.shortName.getShortName()) {
            "TextInputField" -> {
                val isNumberOnly = annotation.arguments[0].value as Boolean
                builder.addCode(
                    """
                        TextInput(
                            modifier = modifier.defaultModifier(),
                            title = title,
                            initialValue = initialData.$parameterName,
                            onChange = { newValue -> updateData { it.copy($parameterName = newValue) } },
                            enabled = enabled,
                            predicate = ${if (isNumberOnly) "{ it.isDigitsOnly() && it.isNotBlank() }" else "{ true }"}
                        )   
                    """.trimIndent()
                )
                return builder.build()
            }

            "InlineTextInputField" -> {
                val isNumberOnly = annotation.arguments[0].value as Boolean
                builder.addCode(
                    """
                        InlineTextInput(
                            modifier = modifier.defaultModifier(),
                            title = title,
                            initialValue = initialData.$parameterName,
                            onChange = { newValue -> updateData { it.copy($parameterName = newValue) } },
                            enabled = enabled,
                            predicate = ${if (isNumberOnly) "{ it.isDigitsOnly() && it.isNotBlank() }" else "{ true }"}
                        )   
                    """.trimIndent()
                )
                return builder.build()
            }

            "DropdownInputField" -> {
                val options = (annotation.arguments[0].value as ArrayList<*>).map { it as String }
                builder.addCode(
                    """
                        DropdownInput(
                            modifier = modifier.defaultModifier(),
                            title = title,
                            initialValue = initialData.$parameterName,
                            options = listOf(${options.joinToString { it.surroundWithQuotes() }}),
                            onChange = { newValue -> updateData { it.copy($parameterName = newValue) } },
                            enabled = enabled
                        )
                    """.trimIndent()
                )
                return builder.build()
            }

            "NumberInputField" -> {
                val max = annotation.arguments[0].value as Int
                val min = annotation.arguments[1].value as Int
                builder.addCode(
                    """
                        NumberInput(
                            modifier = modifier.defaultModifier(),
                            title = title,
                            initialValue = initialData.$parameterName,
                            max = $max,
                            min = $min,
                            onChange = { newValue -> updateData { it.copy($parameterName = newValue) } },
                            enabled = enabled
                        )
                    """.trimIndent()
                )
                return builder.build()
            }

            "CycleInputField" -> {
                val options = (annotation.arguments[0].value as ArrayList<*>).map { it as String }
                builder.addCode(
                    """
                        CycleInput(
                            modifier = modifier.defaultModifier(),
                            title = title,
                            initialOption = initialData.$parameterName,
                            options = listOf(${options.joinToString { it.surroundWithQuotes() }}),
                            onChange = { newValue -> updateData { it.copy($parameterName = newValue) } },
                            enabled = enabled
                        )
                    """.trimIndent()
                )
                return builder.build()
            }

            "SwitchInputField" -> {
                builder.addCode(
                    """
                        SwitchInput(
                            modifier = modifier.defaultModifier(),
                            title = title,
                            initialState = initialData.$parameterName,
                            onChange = { newValue -> updateData { it.copy($parameterName = newValue) } },
                            enabled = enabled
                        )
                    """.trimIndent()
                )
                return builder.build()
            }
        }
        throw UnsupportedOperationException("${annotation.shortName.getShortName()} is not a valid input type!")
    }

    private fun String?.capitalize(): String {
        return this?.replaceFirstChar { it.uppercase() }.toString()
    }

    private fun String.titleize(): String {
        val sequence = this.asSequence()
        val newString = mutableListOf<Char>()
        sequence.forEach {
            if (it.isUpperCase()) newString.add(' ')
            newString.add(it)
        }
        return newString.joinToString(separator = "").removePrefix(" ")
    }

    private fun String.surroundWithQuotes(): String {
        return """"$this""""
    }
}

class Provider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) = AnnotationProcessor(
        codeGenerator = environment.codeGenerator
    )
}