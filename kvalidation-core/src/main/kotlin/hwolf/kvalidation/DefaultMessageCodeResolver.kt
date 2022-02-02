/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
