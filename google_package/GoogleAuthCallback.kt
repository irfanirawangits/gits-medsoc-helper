package id.gits.gitsmedsochelper.google_package

import com.google.firebase.auth.FirebaseUser

/**
 * Created by irfanirawansukirman on 31/01/18.
 */
interface GoogleAuthCallback {

    interface ResponseCallback {

        fun googleSignInSuccess(data: FirebaseUser)

        fun googleSignInFailed(errorMessage: String)

        fun googleSignOut(isSuccess: Boolean)

    }

}