package id.gits.gitsmedsochelper.google_package

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import id.gits.gitsmedsochelper.R

/**
 * Created by irfanirawansukirman on 31/01/18.
 */

class GoogleAuth(private val context: AppCompatActivity, private val callbacks: GoogleAuthCallback.ResponseCallback) : GoogleApiClient.OnConnectionFailedListener {

    private lateinit var googleApiClient: GoogleApiClient

    private lateinit var firebaseAuth: FirebaseAuth

    private val RC_SIGN_IN = 100

    //==============================================================================================

    init {
        // No inspection ConstantConditions
        if (callbacks == null) {

            throw RuntimeException("GoogleAuthResponse listener cannot be null.")

        }

        // Build Firebase Auth
        initFirebaseAuthInstance()

        // Build Api Client
        buildGoogleApiClient(buildSignInOptions())
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

        callbacks.googleSignInFailed(p0.errorMessage!!)

    }

    private fun buildGoogleApiClient(gso: GoogleSignInOptions) {

        googleApiClient = GoogleApiClient.Builder(context)
                .enableAutoManage(context, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

    }

    private fun initFirebaseAuthInstance() {

        firebaseAuth = FirebaseAuth.getInstance()

    }

    private fun buildSignInOptions(): GoogleSignInOptions {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.resources.getString(R.string.default_web_client_id))
                .requestEmail()

        return gso.build()

    }

    fun googleSignIn(context: AppCompatActivity) {

        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)

        context.startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    fun googleSignOut() {
        // Firebase Sign Out
        firebaseAuth.signOut()

        // Google Sign Out
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { status -> callbacks.googleSignOut(status.isSuccess) }

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result.isSuccess) {
                // Signed in successfully, show authenticated UI.
                val account = result.signInAccount

                getFirebaseAuth(account!!)

            }
        }
    }

    private fun getFirebaseAuth(googleSignInAccount: GoogleSignInAccount) {

        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(context) {

                    if (it.isSuccessful) {

                        val firebaseUser = firebaseAuth.currentUser

                        callbacks.googleSignInSuccess(firebaseUser!!)

                    } else {

                        callbacks.googleSignInFailed(it.exception?.message!!)

                    }
                }
    }
}