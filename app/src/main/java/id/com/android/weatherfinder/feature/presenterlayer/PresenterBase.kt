package id.com.android.weatherfinder.feature.presenterlayer

import id.com.android.weatherfinder.feature.viewlayer.ViewBase

abstract class PresenterBase<T : ViewBase> {

    var viewLayer : T ?= null

    fun detachView() {
        viewLayer = null
    }

    open fun attachView(view : T) {
        viewLayer = view
    }

    fun isViewAttach() :Boolean {
        return viewLayer != null
    }

}
