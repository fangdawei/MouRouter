package club.fdawei.nrouter.api.inject

import android.app.Activity
import club.fdawei.nrouter.api.base.Keeper
import club.fdawei.nrouter.api.base.LRULinkedHashMap
import club.fdawei.nrouter.api.component.activity.ActivityAutowiredProvider
import club.fdawei.nrouter.api.component.fragment.FragmentAutowiredProvider
import club.fdawei.nrouter.api.registry.InjectRegistry
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

/**
 * Created by david on 2019/06/05.
 */
class InjectTable : InjectRegistry {

    private val injectors = mutableMapOf<KClass<*>, Keeper<out Injector>>()
    private val providers = mutableMapOf<KClass<*>, Keeper<out AutowiredProvider>>()
    private val providersCache = LRULinkedHashMap<KClass<*>, Keeper<out AutowiredProvider>>(256)

    private val injectorsLock = ReentrantReadWriteLock()
    private val providersLock = ReentrantReadWriteLock()
    private val providersCacheLock = ReentrantReadWriteLock()

    init {
        Keeper.of { AutowiredProvider() }.let { keeper ->
            providers[Any::class] = keeper
        }
        Keeper.of { ActivityAutowiredProvider() }.let { keeper ->
            providers[Activity::class] = keeper
        }
        Keeper.of { FragmentAutowiredProvider() }.let { keeper ->
            @Suppress("DEPRECATION")
            providers[android.app.Fragment::class] = keeper
            providers[android.support.v4.app.Fragment::class] = keeper
        }
    }

    override fun register(meta: InjectorMeta) {
        injectorsLock.write {
            injectors[meta.target] = Keeper.of(meta.injectorBundle.creator)
        }
    }

    override fun register(meta: ProviderMeta) {
        providersLock.write {
            val keeper = Keeper.of(meta.typeBundle.creator)
            meta.sources.forEach {
                providers[it] = keeper
            }
        }
        providersCacheLock.write {
            if (providersCache.isNotEmpty()) {
                providersCache.clear()
            }
        }
    }

    fun getInjector(kClass: KClass<*>): Injector? {
        return injectorsLock.read {
            injectors[kClass]?.instance
        }
    }

    fun getProvider(kClass: KClass<*>): AutowiredProvider? {
        providersCacheLock.read {
            providersCache[kClass]?.also { keeper ->
                return keeper.instance
            }
        }
        providersLock.read {
            var types = mutableListOf<KClass<*>>(kClass)
            while (types.isNotEmpty()) {
                types.forEach { type ->
                    providers[type]?.also { keeper ->
                        providersCacheLock.write {
                            providersCache[kClass] = keeper
                        }
                        return keeper.instance
                    }
                }
                val parents = mutableListOf<KClass<*>>()
                types.forEach { type ->
                    type.superclasses.forEach { superType ->
                        if (!type.java.isInterface && !superType.java.isInterface) {
                            parents.add(0, superType)
                        } else if (!type.java.isInterface) {
                            parents.add(superType)
                        } else if (type.java.isInterface && superType != Any::class) {
                            parents.add(superType)
                        }
                    }
                }
                types = parents
            }
        }
        return null
    }

    fun print(): String {
        val builder = StringBuilder()
        providers.forEach { (clz, keeper) ->
            builder.append("\n${clz.qualifiedName} -> ${keeper.instance::class.qualifiedName}")
        }
        return builder.toString()
    }
}