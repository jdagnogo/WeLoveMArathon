package com.jdagnogo.welovemarathon.about.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.about.domain.About
import com.jdagnogo.welovemarathon.about.domain.AboutUseCases
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.about.domain.SocialMedia
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.utils.IModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalPagerApi
@HiltViewModel
class AboutViewModel @Inject constructor(
    private val aboutUseCase: AboutUseCases,
    private val reducer: AboutReducer,
) : ViewModel(), IModel<AboutState, AboutUiEvent> {
    private val _state = MutableStateFlow(AboutState())
    override val state: StateFlow<AboutState> get() = _state

    init {
        fetchMembers()
        fetchPhotos()
    }

    override fun dispatchEvent(event: AboutUiEvent) {
        when (event) {
        }
    }

    private fun fetchMembers() {

    }

    private fun fetchPhotos() {

    }
}

@Keep
data class AboutState(
    val members: List<Member> = listOf(
        Member(
            "jeff",
            "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7"
        ),
        Member(
            "toto",
            "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7"
        ),
        Member(
            "titi",
            "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7"
        ),
        Member(
            "tutu",
            "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7"
        ),
        Member(
            "tata",
            "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7"
        ),
        Member(
            "tyty",
            "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7"
        ),
    ),
    val socialMedias: List<SocialMedia> = listOf(
        SocialMedia(
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7",
            link = ""
        ),
        SocialMedia(
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7",
            link = ""
        ),
        SocialMedia(
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7",
            link = ""
        ),
        SocialMedia(
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7",
            link = ""
        ),
        SocialMedia(
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7",
            link = ""
        ),
        SocialMedia(
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/Capture%20d%E2%80%99e%CC%81cran%202022-07-19%20a%CC%80%2010.06.56.png?alt=media&token=b33f5202-c9ff-4d23-b2e5-b7893aabc3b7",
            link = ""
        ),
    ),
    val photos : List<String> = listOf(
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img2.jpg?alt=media&token=827be783-2a6b-4f94-b893-07a68f66a4d8",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img1.jpg?alt=media&token=06c36e60-c193-4f71-af7f-0ec04455bdd7",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fschinias.PNG?alt=media&token=6a7d7020-0a46-4691-8241-ebf8b52b0485",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img1.jpg?alt=media&token=06c36e60-c193-4f71-af7f-0ec04455bdd7",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img2.jpg?alt=media&token=827be783-2a6b-4f94-b893-07a68f66a4d8",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img1.jpg?alt=media&token=06c36e60-c193-4f71-af7f-0ec04455bdd7",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fschinias.PNG?alt=media&token=6a7d7020-0a46-4691-8241-ebf8b52b0485",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img1.jpg?alt=media&token=06c36e60-c193-4f71-af7f-0ec04455bdd7",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img2.jpg?alt=media&token=827be783-2a6b-4f94-b893-07a68f66a4d8",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img1.jpg?alt=media&token=06c36e60-c193-4f71-af7f-0ec04455bdd7",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fschinias.PNG?alt=media&token=6a7d7020-0a46-4691-8241-ebf8b52b0485",
        "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/blogs%2Fwlm_img1.jpg?alt=media&token=06c36e60-c193-4f71-af7f-0ec04455bdd7",

    )
) {
}

@Keep
sealed class AboutPartialState {
    object Loading : AboutPartialState()
    data class Error(val message: String) : AboutPartialState()
    data class OnPhotosSuccess(val item: RecommendedCategoryDetails?) : AboutPartialState()
    data class OnMemberSuccess(val aboutes: List<About>) : AboutPartialState()
}

@Keep
sealed class AboutUiEvent {
    data class OnRecommendedItemSelected(val id: String) : AboutUiEvent()
    object OnResetClicked : AboutUiEvent()
}
