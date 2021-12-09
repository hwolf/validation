package hwolf.validation

data class Department(
    val name: String,
    val employees : List<Employee>,
    val head: Employee,
    val coHead: Employee?,
    val office: String?
)

data class Employee(
    val name: String
)
