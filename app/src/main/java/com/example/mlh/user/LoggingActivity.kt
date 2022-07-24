package com.example.mlh.user

import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.example.mlh.R
import android.content.Intent
import com.example.mlh.MainActivity
import dev.shreyaspatil.MaterialDialog.MaterialDialog
import com.shashank.sony.fancytoastlib.FancyToast
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface

class LoggingActivity : AppCompatActivity() {
    var login: LinearLayout? = null
    var lay_signup: LinearLayout? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging)
        auth = FirebaseAuth.getInstance()
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
        if (auth!!.currentUser != null) {
            startActivity(Intent(this@LoggingActivity, MainActivity::class.java))
        }
        login = findViewById<View>(R.id.login) as LinearLayout
        login!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@LoggingActivity, LoginActivity::class.java))
            finish()
        }
        lay_signup = findViewById<View>(R.id.lay_signup) as LinearLayout
        lay_signup!!.setOnClickListener { view: View? ->
            val i = Intent(this@LoggingActivity, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
        if (!isNetworkAvailable) {
            val mDialog = MaterialDialog.Builder(this)
                .setTitle("No Internet")
                .setMessage("Please check your internet connection")
                .setCancelable(false)
                .setPositiveButton(
                    "Dismiss",
                    R.drawable.ic_close
                ) { dialogInterface: DialogInterface, which: Int -> dialogInterface.dismiss() }
                .build()

            // Show Dialog
            mDialog.show()
            FancyToast.makeText(
                this@LoggingActivity,
                "You are not connected to the internet",
                FancyToast.WARNING,
                FancyToast.LENGTH_SHORT,
                false
            ).show()
        } else if (isNetworkAvailable) {
            FancyToast.makeText(
                this@LoggingActivity,
                "Get Started",
                FancyToast.SUCCESS,
                FancyToast.LENGTH_SHORT,
                false
            ).show()
        }
    }

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        true
                    } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                }
            }
            return false
        }
}