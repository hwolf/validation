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
package io.github.hwolf.kvalidation

data class TestBean(val prop1: Int, val prop2: Int? = null)

data class StringBean(val string: String)

data class IterableBean(val range: Iterable<Int>)

data class ListBean(val list: List<String>)

@Suppress("ArrayInDataClass")
data class ArrayBean(val array: Array<String>)

data class MapBean(val map: Map<String, Int>)
