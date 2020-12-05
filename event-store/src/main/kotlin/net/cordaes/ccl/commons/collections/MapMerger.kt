package net.corda.ccl.commons.collections

import java.lang.RuntimeException

@Suppress("UNCHECKED_CAST")
fun merge(original: Map<String, Any>, updates: Map<String, Any?>): Map<String, Any> {

    // check for null values in the input(original) - its too easy to bypass the level checking
    if (original.values.count { it == null } > 0){
        throw RuntimeException("cannot have null value in the 'original' map ")
    }

    // find the nested maps
    val nestedInOriginal = original.entries.filter { it.value is Map<*, *> }.map { it.key }.toHashSet()
    val nestedInUpdates = updates.entries.filter { it.value is Map<*, *> }.map { it.key }.toHashSet()
    val nested = nestedInOriginal.intersect(nestedInUpdates)

    // recurse into nested maps
    val working = HashMap(original)
    val unprocessedUpdates = HashMap(updates)
    nested.forEach { working[it] = merge(
            working[it] as Map<String, Any>,
            unprocessedUpdates[it] as Map<String, Any?>
    ) }
    nested.forEach {unprocessedUpdates.remove(it)}

    // now deal with normal updates and removals
    val additions = working.plus(unprocessedUpdates)
    val toRemove = unprocessedUpdates.entries.filter { it.value == null }.map { it.key }
    return additions.minus(toRemove) as Map<String,Any>
}