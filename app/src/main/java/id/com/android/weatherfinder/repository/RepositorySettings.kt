package id.com.android.weatherfinder.repository

import id.com.android.weatherfinder.controller.ControllerPreference

class RepositorySettings(controllerPreferences: ControllerPreference?) {

    companion object {
        var controllerPreference : ControllerPreference?   = null
    }

    init {
        controllerPreference = controllerPreferences
    }

}