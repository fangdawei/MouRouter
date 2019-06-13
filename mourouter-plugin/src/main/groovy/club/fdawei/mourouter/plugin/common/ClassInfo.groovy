package club.fdawei.mourouter.plugin.common

/**
 * Created by david on 2019/05/29.
 */
final class ClassInfo {

    final static class AbsAppProvider {
        static final String METHOD_INIT_PROVIDERS = 'initProviders'
        static final String METHOD_ADD_PROVIDER = 'addProvider'
    }

    final static class MultiProvider {
        static final String NAME = 'club.fdawei.mourouter.api.provider.MultiProvider'
        static final String METHOD_PROVIDE_NAME = 'provide'
    }

    final static class ModuleProvider {
        static boolean isModuleProvider(String fileName) {
            return fileName ==~ /^.*Module_.+_Provider\.class$/
        }
    }

    final static class AppProvider {
        static final String NAME = 'club.fdawei.mourouter.providers.App_Provider'
    }
}