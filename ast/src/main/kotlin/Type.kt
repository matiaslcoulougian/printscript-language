sealed class Type {
    object STRING : Type()
    object NUMBER : Type()
    object BOOLEAN : Type()
    object UNDEFINED : Type()
    object INPUT : Type()
    companion object {
        fun values(): Array<Type> = arrayOf(STRING, NUMBER, BOOLEAN, UNDEFINED, INPUT)

        fun valueOf(value: String): Type {
            return when (value) {
                "STRING" -> STRING
                "NUMBER" -> NUMBER
                "BOOLEAN" -> BOOLEAN
                "UNDEFINED" -> UNDEFINED
                "INPUT" -> INPUT
                else -> throw IllegalArgumentException("No object Type.$value")
            }
        }
    }
}
