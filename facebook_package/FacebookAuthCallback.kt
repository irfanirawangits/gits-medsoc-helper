package id.gits.gitsmedsochelper.facebook_package

import id.gits.gitsmedsochelper.model.FacebookAuthUser

/**
 * Created by irfanirawansukirman on 01/02/18.
 */
interface FacebookAuthCallback {

    interface ResponseCallback {

        fun facebookProfileReceived(data: FacebookAuthUser)

        fun facebookLoginSuccess()

        fun facebookLoginFailed(errorMessage: String)

    }
}