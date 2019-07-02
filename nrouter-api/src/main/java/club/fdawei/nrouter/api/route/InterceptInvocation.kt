package club.fdawei.nrouter.api.route

import club.fdawei.nrouter.api.action.RouteActionBundle

/**
 * Created by david on 2019/05/31.
 */
class InterceptInvocation(
    private val interceptors: List<RouteInterceptor>,
    val data: RouteActionBundle
) {
    var routeHandler: RouteHandler? = null
    val iterator: Iterator<RouteInterceptor> by lazy { interceptors.iterator() }

    fun interrupt() {
        routeHandler = null
    }

    fun next() {
        if (iterator.hasNext()) {
            iterator.next().intercept(this)
        }
    }
}