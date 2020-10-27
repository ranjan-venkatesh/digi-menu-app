package com.example.digimenu

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digimenu.models.payload.MenuProductPriceAllergenItem
import com.example.digimenu.models.payload.PayloadData
import com.example.digimenu.models.payload.PayloadDataSerializer
import com.example.digimenu.models.response.ProductItem
import com.example.digimenu.models.response.Response
import com.example.digimenu.models.response.RowItem
import com.example.exampleapp.R
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var myWebView: WebView
    var payload: String = ""

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        getCurrentMenuData()

    }


    private fun getCurrentMenuData() {
        val call: Call<Response> = MenuAPIClient.getClient.getCurrentMenuData()
        call.enqueue(object : Callback<Response> {

            override fun onResponse(
                call: Call<Response>?,
                response: retrofit2.Response<Response>?
            ) {
                if (response!!.isSuccessful()) {
                    Log.i("Response from API :", response?.body().toString())
                    val deserializedResponse: Response = response!!.body()!!
                    val weeklyMenuItemRows: List<PayloadData?>?
                    weeklyMenuItemRows = deserializedResponse.rows?.map { it?.toPayloadData() }
                    val gson = GsonBuilder().registerTypeAdapter(
                        PayloadData::class.java,
                        PayloadDataSerializer()
                    ).create()
                    Log.i("JSON Payload", gson.toJson(weeklyMenuItemRows))
                    payload = gson.toJson(weeklyMenuItemRows)
                    showUpdatedMenu();

                } else {
                    Toast.makeText(
                        getApplicationContext(),
                        "API Response Failed :" + response.message(),
                        Toast.LENGTH_LONG
                    ).show();
                    Log.e("API Response Failed :", response.message())
                }
            }

            override fun onFailure(call: Call<Response>?, t: Throwable?) {
                t?.message?.let { Log.e("API Response Failed :", it) }
                Toast.makeText(
                    getApplicationContext(),
                    "API Response Failed :" + t?.message,
                    Toast.LENGTH_LONG
                ).show();
            }

        })
    }

    fun showUpdatedMenu() {

        myWebView = findViewById<View>(R.id.webview) as WebView

        myWebView.apply {

            this.settings.loadsImagesAutomatically = true
            this.settings.javaScriptEnabled = true
            myWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            myWebView.webViewClient = WebViewClient()
            val path: String = Uri.parse("file:///android_asset/index.html").toString()
            myWebView.addJavascriptInterface(
                JavascriptInterface(
                    payload,
                    context
                ), "AndroidMainActLoader"
            )
            myWebView.addJavascriptInterface(
                JavascriptInterface(
                    payload,
                    context
                ), "AndroidMainActToaster"
            )
            progressBar.setVisibility(View.INVISIBLE);
            myWebView.loadUrl(path)

        }
    }

    fun RowItem.toPayloadData() =
        PayloadData(
            category = name,
            montag = days?.find { it?.weekday == 0 }?.productIds?.map { it?.toMenuProductPriceAllergenItem() },
            dienstag = days?.find { it?.weekday == 1 }?.productIds?.map { it?.toMenuProductPriceAllergenItem() },
            mittwoch = days?.find { it?.weekday == 2 }?.productIds?.map { it?.toMenuProductPriceAllergenItem() },
            donnerstag = days?.find { it?.weekday == 3 }?.productIds?.map { it?.toMenuProductPriceAllergenItem() },
            freitag = days?.find { it?.weekday == 4 }?.productIds?.map { it?.toMenuProductPriceAllergenItem() },
            samstag = days?.find { it?.weekday == 5 }?.productIds?.map { it?.toMenuProductPriceAllergenItem() }
        )

    fun ProductItem.toMenuProductPriceAllergenItem() =
        MenuProductPriceAllergenItem(
            productName = name,
            allergenLabels = allergens?.map { it?.label.toString() }.toString().replace("[", "")
                .replace("]", ""),
            price = price?.price.toString()
        )

}