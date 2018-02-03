package id.gits.gitsmedsochelper.model

import android.net.Uri

/**
 * Created by irfanirawansukirman on 01/02/18.
 */

data class GoogleAuthUser(var name: String, var email: String, var idToken: String, var id: String,
                          var familyName: String, var photoUrl: Uri)