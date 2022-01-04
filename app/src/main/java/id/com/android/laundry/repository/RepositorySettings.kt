package id.com.android.laundry.repository

import id.com.android.laundry.controller.ControllerPreference

class RepositorySettings(controllerPreferences: ControllerPreference?) {

    companion object {
        var controllerPreference : ControllerPreference?   = null
    }

    init {
        controllerPreference = controllerPreferences
    }

}