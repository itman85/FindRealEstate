package ch.com.findrealestate.features.base

import ch.com.findrealestate.features.home.redux.HomeAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlin.reflect.KClass

inline fun<A:Any,reified SubA:A> Flow<A>.ofType(clz:KClass<SubA>):Flow<SubA> = this.filter { it is SubA }.map { it as SubA }

//fun<A:Any,N:Any> Flow<A>.navigationHandler(navigationTransformer:suspend (action: A)->N?):Flow<N> = this.mapLatest {
//    navigationTransformer(it) }.filter { it != null }
