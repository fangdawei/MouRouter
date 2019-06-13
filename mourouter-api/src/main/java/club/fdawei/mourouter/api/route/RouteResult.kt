package club.fdawei.mourouter.api.route

/**
 * Create by david on 2019/06/01.
 */
class RouteResult(
    val handlerMetaData: HandlerMetaData?,
    val interceptors: List<InterceptorMetaData>
) {
    val sortedInterceptors: List<RouteInterceptor> by lazy {
        interceptors.sortedBy { it.priority }.map { it.interceptor }
    }
}