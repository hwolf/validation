package hwolf.validation

import java.security.SecureRandom

class XxxTest {

    fun test() {
        val sr = SecureRandom("abcdefghijklmnop".toByteArray(charset("us-ascii")))
        val v = sr.nextInt()
    }
}
