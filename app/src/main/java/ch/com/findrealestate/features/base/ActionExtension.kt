package ch.com.findrealestate.features.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

inline fun<A:Any,reified SubA:A> Flow<A>.ofType(clz:KClass<SubA>):Flow<SubA> = this.filter { it is SubA }.map { it as SubA }
