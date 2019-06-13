package club.fdawei.mourouter.api.route

import kotlin.reflect.KClass

/**
 * Created by david on 2019/05/27.
 */
class HandlerMetaData(
    val path: String,
    val target: KClass<out Any>,
    val flags: Int,
    private val provider: (HandleInfo) -> RouteHandler
) {
    val handler: RouteHandler by lazy {
        provider.invoke(HandleInfo(target, flags))
    }

    override fun toString(): String {
        return "{" +
                "path='$path', " +
                "target=${target.qualifiedName}, " +
                "flags=$flags, " +
                "handler=${handler::class.qualifiedName}" +
                "}"
    }
}