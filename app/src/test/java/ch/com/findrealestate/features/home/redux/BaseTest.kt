package ch.com.findrealestate.features.home.redux

import com.freeletics.flowredux.GetState

fun <S> getState(state: S): GetState<S> {
    return object : GetState<S> {
        override fun invoke(): S = state
    }
}
