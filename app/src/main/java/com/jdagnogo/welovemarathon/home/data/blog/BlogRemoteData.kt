package com.jdagnogo.welovemarathon.home.data.blog

import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.Blog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

interface BlogRemoteData {
    suspend fun getBlogs(): Resource<List<Blog>>
}

@ExperimentalCoroutinesApi
class BlogFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    BlogRemoteData {
    override suspend fun getBlogs(): Resource<List<Blog>> {
        val blogs = mutableListOf<Blog>()
        return try {
            val snapshot = fireStore
                .collection(COLLECTION_NAME)
                .get()
                .await()

            blogs.addAll(snapshot.toObjects(Blog::class.java))
            blogs.addAll(snapshot.toObjects(Blog::class.java))
            blogs.addAll(snapshot.toObjects(Blog::class.java))

            Resource.Success(blogs)
        } catch (e: HttpException) {
            Resource.GenericError.HttpError(e.message ?: "", null, code = e.response.code())
        } catch (e: IOException) {
            Resource.GenericError.NetworkError(
                message = "Couldn't reach server, check your internet connection.",
                data = listOf()
            )
        }
    }

    companion object {
        private const val COLLECTION_NAME = "Blog"
    }
}
