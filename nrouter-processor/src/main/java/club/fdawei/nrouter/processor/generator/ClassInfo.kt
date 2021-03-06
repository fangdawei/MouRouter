package club.fdawei.nrouter.processor.generator

/**
 * Created by david on 2019/05/28.
 */
object ClassInfo {

    const val PROVIDER_PACKAGE = "nrouter.generated.providers"
    const val PROVIDER_NAME_PREFIX = "NRouter_"
    const val PROVIDER_NAME_SUFFIX = "_Provider"
    const val INJECTOR_NAME_SUFFIX = "_Injector"

    object Route {
        const val VAL_HANDLER = "handler"
    }

    object Provider {
        const val VAL_SOURCE = "sources"
    }

    object RouteRegistry {
        const val FUN_REGISTER = "register"
    }

    object InjectRegistry {
        const val FUN_REGISTER = "register"
    }

    object SchemeRegistry {
        const val FUN_REGISTER = "register"
    }

    object MultiProvider {
        const val FUN_PROVIDE_NAME = "provide"
        const val FUN_ARG_REGISTRY = "registry"
    }

    object AutowiredProvider {
        const val FUN_GET_AUTOWIRED = "getAutowired"
    }

    object Injector {
        const val FUN_INJECT = "inject"
        const val FUN_ARG_SOURCE = "source"
        const val FUN_ARG_TARGET = "target"
        const val FUN_ARG_PROVIDER = "provider"
        const val FUN_ARG_DATA = "data"
    }

    object ProviderMeta {
        const val FUN_ADD_SOURCE = "addSource"
    }
}