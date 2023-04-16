sealed class Type {
    object STRING : Type()
    object NUMBER : Type()
    object BOOLEAN : Type()
    object UNDEFINED : Type()

    companion object {
        fun values(): Array<Type> {
            return arrayOf(STRING, NUMBER, BOOLEAN)
        }

        fun valueOf(value: String): Type {
            return when (value) {
                "STRING" -> STRING
                "NUMBER" -> NUMBER
                "BOOLEAN" -> BOOLEAN
                "UNDEFINED" -> UNDEFINED
                else -> throw IllegalArgumentException("No object Type.$value")
            }
        }
    }
}
