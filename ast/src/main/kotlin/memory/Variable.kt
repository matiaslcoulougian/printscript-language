package memory

import Type

data class Variable(val value: Any?, val isConst: Boolean, val type: Type)
