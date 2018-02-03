# GITS Media Social Helper

Class helper for auth and share content using google, facebook and twitter

## Installing

Download [the latest class][1], extract and then put them in util module.

#### Google Auth

Open .gradle [Project] and add this line:
```
classpath 'com.google.gms:google-services:3.1.1'
```
Open .gradle [Module] then add this line:
```
implementation 'com.google.firebase:firebase-auth:11.2.0'
implementation 'com.google.android.gms:play-services-auth:11.2.0'

apply plugin: 'com.google.gms.google-services' => put this at the bottom in .gradle [Module]
```

## How to use it?
```
class SingleActivity : BaseActivity(), GoogleAuthCallback.ResponseCallback {

    private lateinit var googleSignInHelper: GoogleAuth

    //==============================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)

        initGoogleHelper()

        btn_single_googleAuth.setOnClickListener { signInWithGoogle() }

    }

    override fun googleSignInSuccess(data: FirebaseUser) {

        hideProgress()

        Toast.makeText(this@SingleActivity, data.email, Toast.LENGTH_SHORT).show()

    }

    override fun googleSignInFailed(errorMessage: String) {

        Toast.makeText(this@SingleActivity, errorMessage, Toast.LENGTH_SHORT).show()

    }

    override fun googleSignOut(isSuccess: Boolean) {

        Toast.makeText(this@SingleActivity, isSuccess.toString(), Toast.LENGTH_SHORT).show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        googleSignInHelper.onActivityResult(requestCode, resultCode, data!!)
        
    }

    /********************************
     *  Initialize google sign-in   *
     ********************************/
    private fun initGoogleHelper() {

        googleSignInHelper = GoogleAuth(this, this)

    }

    /*******************************
     *  Call google auth function  *
     *******************************/
    private fun signInWithGoogle(){

        showProgress()

        googleSignInHelper.googleSignIn(this@SingleActivity)

    }
}
```

[1]: https://github.com/irfanirawangits/gits-medsoc-helper/archive/master.zip