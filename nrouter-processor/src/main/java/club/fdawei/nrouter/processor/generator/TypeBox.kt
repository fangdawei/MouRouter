package club.fdawei.nrouter.processor.generator

import com.squareup.kotlinpoet.ClassName

/**
 * Create by david on 2019/05/27.
 */
object TypeBox {
    const val ACTIVITY_NAME = "android.app.Activity"
    const val SERVICE_NAME = "android.app.Service"
    const val FRAGMENT_NAME = "android.app.Fragment"
    const val V4_FRAGMENT_NAME = "android.support.v4.app.Fragment"
    const val CREATABLE_NAME = "club.fdawei.nrouter.api.component.creatable.Creatable"
    const val ROUTE_HANDLER_NAME = "club.fdawei.nrouter.api.route.RouteHandler"
    const val AUTOWIRED_PROVIDER_NAME = "club.fdawei.nrouter.api.inject.AutowiredProvider"

    val ROUTE_REGISTRY = ClassName.bestGuess(
        "club.fdawei.nrouter.api.registry.RouteRegistry"
    )

    val INJECT_REGISTRY = ClassName.bestGuess(
        "club.fdawei.nrouter.api.registry.InjectRegistry"
    )

    val SCHEME_REGISTRY = ClassName.bestGuess(
        "club.fdawei.nrouter.api.registry.SchemeRegistry"
    )

    val MULTI_PROVIDER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.provider.MultiProvider"
    )

    val ROUTE_NODE_META = ClassName.bestGuess(
        "club.fdawei.nrouter.api.route.RouteNodeMeta"
    )

    val INTERCEPTOR_META = ClassName.bestGuess(
        "club.fdawei.nrouter.api.route.InterceptorMeta"
    )

    val ACTIVITY_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.activity.ActivityRouteHandler"
    )

    val FRAGMENT_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.fragment.FragmentRouteHandler"
    )

    val SERVICE_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.service.ServiceRouteHandler"
    )

    val CREATABLE_ROUTE_HANDLER = ClassName.bestGuess(
        "club.fdawei.nrouter.api.component.creatable.CreatableRouteHandler"
    )

    val INJECTOR = ClassName.bestGuess(
        "club.fdawei.nrouter.api.inject.Injector"
    )

    val AUTOWIRED_PROVIDER = ClassName.bestGuess(AUTOWIRED_PROVIDER_NAME)

    val INJECTOR_META = ClassName.bestGuess(
        "club.fdawei.nrouter.api.inject.InjectorMeta"
    )

    val PROVIDER_META = ClassName.bestGuess(
        "club.fdawei.nrouter.api.inject.ProviderMeta"
    )

    val ACTION_BUNDLE = ClassName.bestGuess("club.fdawei.nrouter.api.action.ActionBundle")

    val SCHEME_META = ClassName.bestGuess(
        "club.fdawei.nrouter.api.scheme.SchemeMeta"
    )

    val TYPE_BUNDLE = ClassName.bestGuess("club.fdawei.nrouter.api.base.TypeBundle")
}