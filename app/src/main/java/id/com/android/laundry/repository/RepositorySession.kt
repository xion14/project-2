package id.com.android.laundry.repository

import com.google.gson.Gson
import id.com.android.laundry.controller.ControllerPreference
import id.com.android.laundry.model.ModelLogin

class RepositorySession(controllerPreference : ControllerPreference) {

    private val SESSION_USER_INFO   = "user_info"
    private val SESSION_USER_TOKEN  = "user_token"
    private val SESSION_USER_STEP = "user_step"

    companion object {
        var controllerSession : ControllerPreference? = null
    }

    init {
        controllerSession = controllerPreference
    }


    var userSession: ModelLogin?
        get() {
            val userInfo = controllerSession?.getString(SESSION_USER_INFO, "")
            return Gson().fromJson<ModelLogin>(userInfo, ModelLogin::class.java)
        }
        set(value) {
            val userInfo = Gson().toJson(value)
            controllerSession?.setPref(SESSION_USER_INFO, userInfo)
        }

    fun setUserStep(step: Int) {
        controllerSession?.setPref(SESSION_USER_STEP, step)
    }




    fun clearSession() {
        controllerSession?.clearPref(SESSION_USER_TOKEN)
        controllerSession?.clearPref(SESSION_USER_INFO)
    }

}
