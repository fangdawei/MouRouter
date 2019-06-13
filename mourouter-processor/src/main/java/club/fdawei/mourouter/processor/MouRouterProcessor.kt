package club.fdawei.mourouter.processor

import club.fdawei.mourouter.annotation.Autowired
import club.fdawei.mourouter.annotation.Interceptor
import club.fdawei.mourouter.annotation.Provider
import club.fdawei.mourouter.annotation.Route
import club.fdawei.mourouter.processor.common.Context
import club.fdawei.mourouter.processor.generator.InjectorGenerator
import club.fdawei.mourouter.processor.generator.ProviderGenerator
import club.fdawei.mourouter.processor.log.Logger
import club.fdawei.mourouter.processor.util.UtilsProvider
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by david on 2019/05/24.
 */
class MouRouterProcessor : AbstractProcessor() {

    private val context = Context()
    private lateinit var filer: Filer
    private val utilsProvider = UtilsProvider()
    private val providerGenerator = ProviderGenerator(context, utilsProvider)
    private val injectorGenerator = InjectorGenerator(context, utilsProvider)
    private val logger = Logger()

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv!!)
        filer = processingEnv.filer
        logger.messager = processingEnv.messager
        utilsProvider.logger = logger
        utilsProvider.elementUtils = processingEnv.elementUtils
        utilsProvider.typeUtils = processingEnv.typeUtils

        context.moduleName = processingEnv.options["moduleName"]
        context.isAppModule = processingEnv.options["isAppModule"]?.toBoolean() ?: false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            Route::class.java.canonicalName,
            Interceptor::class.java.canonicalName,
            Provider::class.java.canonicalName,
            Autowired::class.java.canonicalName
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        logger.w("process $context")
        if (roundEnv == null) {
            return false
        }
        injectorGenerator.clear()
        providerGenerator.clear()
        collectRouteWith(roundEnv)
        collectInterceptorWith(roundEnv)
        collectProviderWith(roundEnv)
        collectAutowiredWith(roundEnv)
        if (!context.moduleName.isNullOrEmpty()) {
            injectorGenerator.genKtFile(filer)
            providerGenerator.genKtFile(filer)
        }
        return true
    }

    private fun collectRouteWith(roundEnv: RoundEnvironment) {
        val elements = roundEnv.getElementsAnnotatedWith(Route::class.java)
        elements.forEach {
            if (it.kind == ElementKind.CLASS) {
                providerGenerator.addRouteWith(it as TypeElement)
            }
        }
    }

    private fun collectInterceptorWith(roundEnv: RoundEnvironment) {
        val elements = roundEnv.getElementsAnnotatedWith(Interceptor::class.java)
        elements.forEach {
            if (it.kind == ElementKind.CLASS) {
                providerGenerator.addInterceptorWith(it as TypeElement)
            }
        }
    }

    private fun collectProviderWith(roundEnv: RoundEnvironment) {
        val elements = roundEnv.getElementsAnnotatedWith(Provider::class.java)
        elements.forEach {
            if (it.kind == ElementKind.CLASS) {
                providerGenerator.addProviderWith(it as TypeElement)
            }
        }
    }

    private fun collectAutowiredWith(roundEnv: RoundEnvironment) {
        val elements = roundEnv.getElementsAnnotatedWith(Autowired::class.java)
        elements.forEach {
            val enclosingElement = it.enclosingElement
            if (it.kind == ElementKind.FIELD && enclosingElement.kind == ElementKind.CLASS) {
                val type = enclosingElement as TypeElement
                val field = it as VariableElement
                injectorGenerator.addAutowiredWith(type, field)
                providerGenerator.addAutowiredWith(type, field)
            }
        }
    }
}