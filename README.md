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

        btn_single_googleSignIn.setOnClickListener { signInWithGoogle() }
        
        btn_single_googleSignOut.setOnClickListener { signOutWithGoogle() }
        
    }

    override fun googleSignInSuccess(data: FirebaseUser) {

        hideProgress()

        Toast.makeText(this@SingleActivity, data.email, Toast.LENGTH_SHORT).show()

    }

    override fun googleSignInFailed(errorMessage: String) {

        hideProgress()
        
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

    /*************************
     *  Call google sign-in  *
     *************************/
    private fun signInWithGoogle(){

        showProgress()

        googleSignInHelper.googleSignIn(this@SingleActivity)

    }

    /**************************
     *  Call google sign-out  *
     **************************/
    private fun signOutWithGoogle(){

        showProgress()

        googleSignInHelper.googleSignOut()

    }
}
```

#### Facebook Auth

Open .gradle [Module] then add this line:
```
implementation 'com.facebook.android:facebook-android-sdk:4.7.0'
```

## How to use it?
```
class SingleActivity : BaseActivity(), FacebookAuthCallback.ResponseCallback {

    private lateinit var facebookSignInHelper: FacebookAuth

    //==============================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_activity)

        btn_single_googleSignIn.setOnClickListener { signInWithFacebook() }

        btn_single_googleSignOut.setOnClickListener { signOutWithFacebook() }

    }

    override fun facebookProfileReceived(data: FacebookAuthUser) {

        Toast.makeText(this@SingleActivity, data.email, Toast.LENGTH_SHORT).show()

    }

    override fun facebookLoginSuccess() {

        Toast.makeText(this@SingleActivity, "Yeay login success", Toast.LENGTH_SHORT).show()

    }

    override fun facebookLoginFailed(errorMessage: String) {

        Toast.makeText(this@SingleActivity, errorMessage, Toast.LENGTH_SHORT).show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        facebookSignInHelper.onActivityResult(requestCode, resultCode, data!!)

    }

    /**********************************
     *  Initialize facebook sign-in   *
     **********************************/
    private fun initFacebookHelper() {

        facebookSignInHelper = FacebookAuth(this@SingleActivity,
                "id,name,email,gender,birthday,picture,cover", this@SingleActivity)

    }

    /***************************
     *  Call facebook sign-in  *
     ***************************/
    private fun signInWithFacebook(){

        showProgress()

        facebookSignInHelper.facebookSignIn(this@SingleActivity)

    }

    /****************************
     *  Call facebook sign-out  *
     ****************************/
    private fun signOutWithFacebook(){

        showProgress()

        facebookSignInHelper.facebookSignOut()

    }
}
```

## Author

- Email : dadang.kotz@gmail.com
- Call/WhatsApp : 089531183668
- [LinkedIn][2]
- [Instagram][3]

## Reference

Thank you very much because it helps me as a reference to create the library :)
-[https://github.com/multidots/android-social-signin-helper][4]

[1]: https://github.com/irfanirawangits/gits-medsoc-helper/archive/master.zip
[2]: https://www.linkedin.com/in/irfan-irawan-sukirman-9096bba7/
[3]: https://www.instagram.com/ir.rawasukma/
[4]: https://github.com/multidots