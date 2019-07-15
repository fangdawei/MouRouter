package club.fdawei.nrouter.api.component.creatable

import club.fdawei.nrouter.api.util.throwException
import kotlin.reflect.KClass

/**
 * Create by david on 2019/06/02.
 */
object CreatableFactory {
    fun create(kClass: KClass<out Creatable>): Creatable? {
        return try {
            kClass.java.newInstance()
        } catch (e: Exception) {
            throwException("create instance of ${kClass.qualifiedName} error, ${e.message}")
            null
        }
    }
}