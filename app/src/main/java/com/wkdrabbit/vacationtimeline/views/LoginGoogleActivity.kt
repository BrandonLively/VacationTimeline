package com.wkdrabbit.vacationtimeline.views

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.wkdrabbit.vacationtimeline.data.NetworkAdapter
import com.wkdrabbit.vacationtimeline.R
import com.wkdrabbit.vacationtimeline.notifications.NotificationUtils
import com.wkdrabbit.vacationtimeline.viewModels.AttractionViewModel
import com.wkdrabbit.vacationtimeline.viewModels.ItineraryViewModel
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.wkdrabbit.vacationtimeline.models.User
import kotlinx.android.synthetic.main.activity_login_google.*
import java.util.*


const val RC_SIGN_IN = 123

class LoginGoogleActivity : AppCompatActivity() {

    //Test Notification
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false

    private lateinit var itineraryViewModel: ItineraryViewModel
    private lateinit var attractionViewModel: AttractionViewModel
    lateinit var currentUser: User
    var response = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)

        initViewModels()
        googleLoginInit()
        btn_offline.setOnClickListener { offlineSignOn()}
        networkTest()
        //notificationTest()
    }


    fun networkTest(){
        //thread { response =  NetworkAdapter.httpRequest("https://roamly-staging.herokuapp.com/city/image?q=New+York") }
    }

    private fun notificationTest(){
        if (!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this)
        }
    }

    private fun initViewModels(){
        itineraryViewModel = ViewModelProviders.of(this).get(ItineraryViewModel::class.java)
        attractionViewModel = ViewModelProviders.of(this).get(AttractionViewModel::class.java)
    }

    private fun googleLoginInit(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.visibility = View.VISIBLE
        sign_in_button.setSize(SignInButton.SIZE_STANDARD)
        sign_in_button.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun offlineSignOn(){
        val intent = Intent(this, ItineraryListActivity::class.java)
        startActivity(intent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) = try {
        val account = completedTask.getResult(ApiException::class.java)
        val token = ""
        if (account != null) {
            val token = account.idToken
        }

        val headermap = hashMapOf<String,String>()
        headermap["authorization"] = token

        sign_in_button.visibility = View.GONE
        var result = ""
        Thread {
            result = NetworkAdapter.httpRequest(
                "https://roamly-staging.herokuapp.com/api/auth", NetworkAdapter.POST, headermap
            )
        }.run { start() }
       //TODO: Parse out user data from result.
    } catch (e: ApiException) {
        e.printStackTrace()
        sign_in_button.visibility = View.VISIBLE
    }
}
