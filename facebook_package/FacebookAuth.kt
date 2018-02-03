package id.gits.gitsmedsochelper.facebook_package

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import id.gits.gitsmedsochelper.R
import id.gits.gitsmedsochelper.model.FacebookAuthUser
import java.util.*

/**
 * Created by irfanirawansukirman on 01/02/18.
 */

class FacebookAuth(private val facebookListener: FacebookAuthCallback.ResponseCallback,
                   private val fieldString: String,
                   private var context: Context) {

    private lateinit var callbackManager: CallbackManager

    private lateinit var firebaseAuth: FirebaseAuth

    //==============================================================================================

    init {
        //noinspection ConstantConditions
        if (facebookListener == null)
            throw IllegalArgumentException("FacebookResponse listener cannot be null.")

        //noinspection ConstantConditions
        if (fieldString == null) throw IllegalArgumentException("field string cannot be null.")

        initFirebaseAuthInstance()

        initFacebookSdk()

        initCallbackManager()

        getFacebookToken()

    }

    private fun initFirebaseAuthInstance() {

        firebaseAuth = FirebaseAuth.getInstance()

    }

    /*****************************
     *  Initialize facebook sdk  *
     *****************************/
    private fun initFacebookSdk() {

        FacebookSdk.sdkInitialize(context)

    }

    /************************************
     *  Initialize callback manager fb  *
     ************************************/
    private fun initCallbackManager() {

        callbackManager = CallbackManager.Factory.create()

    }

    /************************
     *  Get Facebook Token  *
     ************************/
    private fun getFacebookToken() {

        val facebookCallback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {

                facebookListener.facebookLoginSuccess()

                handleFacebookAccessToken(result!!)

            }

            override fun onError(error: FacebookException?) {

                facebookListener.facebookLoginFailed(error?.message!!)

            }

            override fun onCancel() {

                facebookListener.facebookLoginFailed(context.getString(R.string.facebook_cancel_login_title))

            }
        }

        registerCallbackInLoginManager(callbackManager, facebookCallback)

    }

    /***********************************************
     * Register facebook callback in login manager *
     *                                             *
     * @param callbackManager                      *
     * @param facebookCallback                     *
     *                                             *
     * @output Lorem Ipsum Dolor Sit Amet          *
     ***********************************************/
    private fun registerCallbackInLoginManager(callbackManager: CallbackManager,
                                               facebookCallback: FacebookCallback<LoginResult>) {

        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback)

    }

    private fun handleFacebookAccessToken(loginResult: LoginResult) {

        val credential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener {

                    if (it.isSuccessful) {

                        val user = firebaseAuth.currentUser

                        val facebookAuthUser = FacebookAuthUser(user?.displayName.toString(), user?.email.toString(),
                                user?.phoneNumber.toString(), user?.photoUrl.toString())

                        this.facebookListener.facebookProfileReceived(facebookAuthUser)

                    } else {

                        this.facebookListener.facebookLoginFailed(it.exception?.message!!)

                    }
                }

    }

    fun facebookSignIn(activity: AppCompatActivity) {

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))

    }

    fun facebookSignOut() {
        // Firebase Sign Out
        firebaseAuth.signOut()

        // Facebook Sign Out
        LoginManager.getInstance().logOut()

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        callbackManager.onActivityResult(requestCode, resultCode, data)

    }
}