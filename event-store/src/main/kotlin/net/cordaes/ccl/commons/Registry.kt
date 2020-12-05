package net.corda.ccl.commons


import java.util.HashMap


/**
 * ** Copied from Azure Workbench - move into a commons jar **
 *
 * Simple registry for basic DI
 */
@Suppress("UNCHECKED_CAST")
class Registry {
    private var registry: MutableMap<Class<*>, Any> = HashMap()


    constructor() {}

    constructor(`object`: Any) {
        registry[`object`.javaClass] = `object`
    }

    constructor(vararg objects: Any) {
        for (o in objects) {
            registry[o.javaClass] = o
        }
    }

    fun store(`object`: Any): Registry {
        registry[`object`.javaClass] = `object`
        return this
    }

    fun <T> get(clazz: Class<T>): T {
        val matches = HashSet<T>()
        if (registry.containsKey(clazz)) {
            matches.add((registry[clazz] as T))
        }

        //val matches = HashSet<T>()
        for ((_, value) in registry) {

            // check interfaces
            for (_interface in value.javaClass.interfaces) {
                if (_interface == clazz) {
                    matches.add(value as T)
                }
            }

            var superClazz = value.javaClass.superclass as Class<Any>
            while (superClazz.name != "java.lang.Object"){
                if (clazz.name == superClazz.name){
                    matches.add(value as T)
                }
                superClazz = superClazz.superclass as Class<Any>
            }
        }

        if (matches.isEmpty()) throw RuntimeException("Class $clazz in not in the registry")
        if (matches.size > 1) throw RuntimeException("Class $clazz in the registry multiple times - ${matches.joinToString()}")
        return matches.first()
    }


    fun <T> geteOrElse(clazz: Class<T>, other: T): T {
        try {
            return get(clazz)
        } catch (ex: RuntimeException) {
            return other
        }
    }

    fun <T> getOrNull(clazz: Class<T>): T? {
        try {
            return get(clazz)
        } catch (ex: RuntimeException) {
            return null
        }
    }

    fun <T> contains(clazz: Class<T>) : Boolean{
        return try {
            get(clazz)
            true
        } catch (ex: RuntimeException) {
            false
        }
    }

    fun <T> missing(clazz: Class<T>) : Boolean{
        return !contains(clazz)
    }

    fun flush() {
        registry = HashMap<Class<*>, Any>()
    }

    /**
     * Make a copy of the original registry with the stored
     * object overridden
     *
     * @param object
     * @return
     */
    fun overide(`object`: Any): Registry {
        val overridden = Registry()
        overridden.registry = HashMap<Class<*>, Any>(this.registry)

        return overridden.store(`object`)
    }

    /**
     * Make a copy of the original registry with the stored
     * objects overridden.
     *
     * @param objects
     * @return
     */
    fun overide(vararg objects: Any): Registry {
        var overridden = Registry()
        overridden.registry = HashMap<Class<*>, Any>(this.registry)

        for (o in objects) {
            overridden = overridden.store(o)
        }
        return overridden
    }

}
