package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.domain.myapplication.base.viewModel.BaseViewModel
import com.domain.myapplication.models.Video
import com.domain.repositories.authentication.AuthenticationRepository
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerViewModel(application: Application, val authenticationRepository: AuthenticationRepository, val itemsRepository: ItemsRepository) : BaseViewModel(application){

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean>
        get() = _isLoading

    private val _videoIdError: MutableLiveData<Boolean> = MutableLiveData()
    val videoIdError: MutableLiveData<Boolean>
        get() = _videoIdError

    private val _videoId: MutableLiveData<String> = MutableLiveData()
    val videoId: MutableLiveData<String>
        get() = _videoId

    private val _videoError: MutableLiveData<String> = MutableLiveData()
    val videoError: MutableLiveData<String>
        get() = _videoError

    private val _video: MutableLiveData<Video> = MutableLiveData()
    val video: MutableLiveData<Video>
        get() = _video

    fun setVideoId(videoId: String?){
        when{
            videoId?.isNullOrEmpty() == true -> _videoIdError.value = true
            else -> _videoId.value = videoId
        }
    }

    suspend fun getVideo(videoId: String){
        val response = itemsRepository.getItemVideo(videoId)

        withContext(Dispatchers.Main) {
            when(response.data){
                null -> _videoError.value = response.errorMessage
                else -> _video.value = response.data as Video
            }
        }
    }

    fun startPlayback(video: Video){
        val dd = video.high
    }

}