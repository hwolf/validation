package hwolf.kvalidation

object DefaultMessageCodeResolver : MessageCodeResolver {
    override fun invoke(violation: ConstraintViolation): List<String> =
        buildCodeList(violation.constraint.messageKey, violation.propertyPath.last(), violation.propertyType)

    private fun buildCodeList(messageKey: String, propertyName: PropertyName, propertyType: PropertyType?) =
        buildList {
            if (propertyType != null) {
                if (propertyName.key != null) {
                    add("$messageKey.${propertyName.name}[${propertyName.key}].$propertyType")
                }
                add("$messageKey.${propertyName.name}.$propertyType")
                add("$messageKey.$propertyType")
            }
            if (propertyName.key != null) {
                add("$messageKey.${propertyName.name}[${propertyName.key}]")
            }
            add("$messageKey.${propertyName.name}")
            add(messageKey)
        }
}
