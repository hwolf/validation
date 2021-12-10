package hwolf.validation

data class TestConstraint(
    val prop1: String = "",
    val prop2: Int? = null
) : Constraint
