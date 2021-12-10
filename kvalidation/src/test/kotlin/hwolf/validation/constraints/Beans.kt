package hwolf.validation.constraints

data class TestBean(val prop1: Int, val prop2: Int? = null)

data class StringBean(val string: String)

data class IterableBean(val range: Iterable<Int>)

data class ListBean(val list: List<String>)

@Suppress("ArrayInDataClass")
data class ArrayBean(val array: Array<String>)

data class MapBean(val map: Map<String, Int>)
