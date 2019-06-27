package club.fdawei.mourouter.api.action

import club.fdawei.mourouter.api.route.RouteHandler
import kotlin.reflect.KClass

/**
 * Created by david on 2019/06/04.
 */
class RouteActionImpl(
    override val uri: String,
    private val router: ((RouteActionImpl) -> RouteHandler?)
) : RouteAction, RouteActionData, ActionWrapper<RouteAction>() {

    override val host: RouteAction = this
    private val routeHandler: RouteHandler? by lazy { router.invoke(this) }

    override fun go() {
        routeHandler?.go(this)
    }

    override fun get(): Any? {
        return routeHandler?.get(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clz: KClass<T>): T? {
        val instance = get()
        return if (clz.isInstance(instance)) instance as T else null
    }
}