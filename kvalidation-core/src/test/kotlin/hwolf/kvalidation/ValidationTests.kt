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

import io.kotest.core.spec.style.FunSpec
import strikt.api.expectThat

class ValidationTests : FunSpec({

    data class Employee(
        val name: String
    )

    data class Department(
        val name: String,
        val employees: List<Employee>,
        val head: Employee,
        val coHead: Employee?,
        val office: String?
    )

    val validator = validator<Department> {
        Department::name {
            isNotEmpty()
        }
        Department::head {
            Employee::name {
                isNotEmpty()
            }
        }
        onlyIf({ name == "X" }) {
            Department::head {
                Employee::name {
                    isIn("Mr. X", "Mr. Y")
                }
            }
        }
        Department::coHead ifPresent {
            Employee::name {
                isNotEmpty()
            }
        }
        Department::employees {
            isNotEmpty()
        }
        Department::employees each {
            Employee::name {
                isNotEmpty()
            }
        }
        Department::office ifPresent {
            isNotEmpty()
        }
        onlyIf({ name != "X" }) {
            Department::office required { }
        }
    }

    val department = Department(
        name = "hwolf.test.Test",
        employees = listOf(Employee("Bob"), Employee("Alice")),
        head = Employee("John"),
        coHead = Employee("Mike"),
        office = "X.123.456")

    test("Department is valid") {
        expectThat(validator.validate(department)).isValid()
    }
    test("Department name is empty") {
        expectThat(validator.validate(department.copy(name = ""))).hasExactlyViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("name")),
                propertyType = PropertyType("String"),
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Department head name is empty") {
        val actual = validator.validate(department.copy(head = Employee("")))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("head"), PropertyName("name")),
                propertyType = PropertyType("String"),
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Department without employees") {
        val actual = validator.validate(department.copy(employees = listOf()))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("employees")),
                propertyType = PropertyType("List"),
                propertyValue = emptyList<Any>(),
                constraint = NotEmpty))
    }
    test("Department without co head") {
        val actual = validator.validate(department.copy(coHead = null))
        expectThat(actual).isValid()
    }
    test("Department co head name is empty") {
        val actual = validator.validate(department.copy(coHead = Employee("")))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("coHead"), PropertyName("name")),
                propertyType = PropertyType("String"),
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Employees without names") {
        val actual = validator.validate(
            department.copy(employees = listOf(
                Employee("Bob"),
                Employee(""),
                Employee("Alice"),
                Employee(""))))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("employees", 1), PropertyName("name")),
                propertyType = PropertyType("String"),
                propertyValue = "",
                constraint = NotEmpty),
            ConstraintViolation(
                propertyPath = listOf(PropertyName("employees", 3), PropertyName("name")),
                propertyType = PropertyType("String"),
                propertyValue = "",
                constraint = NotEmpty))
    }
    test("Department without office") {
        val actual = validator.validate(department.copy(office = null))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("office")),
                propertyType = PropertyType("String"),
                propertyValue = null,
                constraint = Required))
    }
    test("Department with name 'X'") {
        val actual = validator.validate(department.copy(name = "X", head = Employee(name = "Mr. Y")))
        expectThat(actual).isValid()
    }
    test("Department with name 'X' but wrong head") {
        val actual = validator.validate(department.copy(name = "X"))
        expectThat(actual).hasExactlyViolations(
            ConstraintViolation(
                propertyPath = listOf(PropertyName("head"), PropertyName("name")),
                propertyType = PropertyType("String"),
                propertyValue = "John",
                constraint = In(allowedValues = listOf("Mr. X", "Mr. Y"))))
    }
})

