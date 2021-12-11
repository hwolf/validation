package hwolf.validation

import org.junit.jupiter.api.Test
import java.security.SecureRandom

class XxxTest {

    @Test
    fun test() {
        val sr = SecureRandom("abcdefghijklmnop".toByteArray(charset("us-ascii")))
        val v = sr.nextInt()
    }
}
