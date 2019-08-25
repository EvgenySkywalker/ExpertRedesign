package com.application.expertnewdesign.lesson.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.expertnewdesign.R
import com.application.expertnewdesign.lesson.ArticleFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.video_fragment.*



class VideoFragment (val code: String): Fragment(){

    interface SetHeight{
        fun height(_height: Int)
    }

    private var initializedYouTubePlayer: YouTubePlayer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        lifecycle.addObserver(video)
        video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(code, 0f)
                initializedYouTubePlayer = youTubePlayer
                val fragment = activity!!.supportFragmentManager.findFragmentByTag("article") as ArticleFragment
                if(!fragment.done){
                    fragment.height(video.measuredHeight)
                    fragment.done = true
                }
            }
        })
    }

    override fun setMenuVisibility(visible: Boolean) {
        super.setMenuVisibility(visible)
        if (!visible && initializedYouTubePlayer != null)
            initializedYouTubePlayer!!.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        video.release()
    }
}