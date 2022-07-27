package com.jdagnogo.welovemarathon.about.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class AboutReducer : IReducer<AboutState, AboutPartialState> {
    override fun reduce(state: AboutState, partialState: AboutPartialState): AboutState {
        return when (partialState) {
            is AboutPartialState.Error -> state.copy()
            is AboutPartialState.OnDataSuccess -> state.copy(
                phone = partialState.data.phone,
                mail = partialState.data.mail,
                members = partialState.data.members,
                socialMedias = partialState.data.socialMedias,
                photos = partialState.data.photos,
            )
            AboutPartialState.Loading -> state.copy()
        }
    }
}