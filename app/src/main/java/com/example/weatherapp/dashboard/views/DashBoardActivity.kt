package com.example.weatherapp.dashboard.views

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.weatherapp.R
import com.example.weatherapp.dashboard.dataBase.AppDatabase
import com.example.weatherapp.dashboard.dataModel.WeatherDataModel
import com.example.weatherapp.dashboard.viewModel.DashboardViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.utils.AppInitializer
import com.example.weatherapp.utils.CustomAlertDialog
import com.example.weatherapp.utils.WeatherQuotes
import com.example.weatherapp.view.base.BaseActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import java.text.DateFormat
import java.util.*
import javax.inject.Inject


/*
* Launching activity, which is responsible for displaying the weather details based on the
* user search. Searched data will be stored in Database, and fetched accordingly. If no searched
* data is available for a location, the details are fetched from internet and are stored. After
* 24hours, database is being cleared.
* */
class DashBoardActivity : BaseActivity<ActivityMainBinding, DashboardViewModel>(){

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main

    @Inject
    override lateinit var viewModel: DashboardViewModel
        internal set

    private var activityMainBinding: ActivityMainBinding? = null

    //By default, location and positions are set to London
    private var lat = -0.13
    private var long = 51.51
    val map_url = "http://maps.google.com/maps?q=loc:%f,%f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = viewDataBinding
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setDefaultLocation()
        setLiveListeners()
        loadData()
        setListeners()
    }

    fun setDefaultLocation(){
        AppInitializer.location = "London"
    }

    private fun setListeners(){
        et_search?.imeOptions = EditorInfo.IME_ACTION_DONE
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab?.setOnClickListener(locationListener)
        iv_search_loc?.setOnClickListener(buttonClickListener)
    }

    private val locationListener = View.OnClickListener {
        val uri = java.lang.String.format(
            Locale.ENGLISH,
            map_url,
            lat,
            long
        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    private val noLocationListener = View.OnClickListener {
        Snackbar.make(cl_main, getString(R.string.nodata), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private val buttonClickListener = View.OnClickListener {
        if (et_search?.text?.isNullOrBlank() == false) {
            AppInitializer.location = et_search.text.toString()
            loadData()
        }else{
            et_search?.error = getString(R.string.not_a_valid_input)
        }
    }

    private fun loadData(){
        AppInitializer.isFromLocalStorage = false
        if (checkForInternet()) {
            if (tv_temp?.text?.isBlank() == false){
                hideBasicView()
            }
            hideEmptyScreen()
            showHideProgress(true)
            val db = Room.databaseBuilder(
                this,
                AppDatabase::class.java, "database-name").allowMainThreadQueries()
                .build()
            viewModel.fetchDetails(db)
        }else{
            Snackbar.make(cl_main, getString(R.string.nointernet), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            showHideProgress(false)
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = this.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun hideBasicView(){
        iv_weatherIcon?.visibility = View.GONE
        iv_tempMax?.visibility = View.GONE
        iv_tempMin?.visibility = View.GONE
        tv_thoughts?.visibility = View.GONE
        fab?.setOnClickListener(noLocationListener)
    }

    private fun showBasicView(){
        iv_weatherIcon?.visibility = View.VISIBLE
        iv_tempMax?.visibility = View.VISIBLE
        iv_tempMin?.visibility = View.VISIBLE
        fab?.setOnClickListener(locationListener)
    }

    private fun setLiveListeners(){
        viewModel.receivedResponse.observe(this, Observer {
            if(!AppInitializer.isFromLocalStorage) {
                startDeletionService()
            }
            Log.i("res", it?.weather?.get(0)?.description.toString())
            updateUI(it)
            showBasicView()
            showHideProgress(false)
        })
        viewModel.errorModel.observe(this, Observer {
            it?.message?.let { it1 -> CustomAlertDialog.displayAlert(this, it1,getString(R.string.ok)) }
            showHideProgress(false)
            if (tv_temp?.text?.isNullOrBlank() == true){
                hideBasicView()
            }
        })
    }

    fun showHideProgress(toShow : Boolean){
        if(toShow){
            pb_progress?.visibility = View.VISIBLE
            tv_loading_msg?.visibility = View.VISIBLE
        }else{
            tv_loading_msg?.visibility = View.GONE
            pb_progress?.visibility = View.GONE
        }
        hideKeyboard()
    }

    private fun showEmptyScreen(){
        layout_emptystate?.visibility = View.VISIBLE
        tv_todaysWeather?.visibility = View.GONE
        iv_weatherIcon?.visibility = View.GONE
        tv_weatherType?.visibility = View.GONE
        tv_temp?.visibility = View.GONE
        tv_max?.visibility = View.GONE
        iv_tempMax?.visibility = View.GONE
        tv_min?.visibility = View.GONE
        iv_tempMin?.visibility = View.GONE
        view?.visibility = View.GONE
        tv_wind?.visibility = View.GONE
        tv_feels_like?.visibility = View.GONE
        tv_thoughts?.visibility = View.GONE
    }

    private fun hideEmptyScreen(){
        layout_emptystate?.visibility = View.GONE
        tv_todaysWeather?.visibility = View.VISIBLE
        iv_weatherIcon?.visibility = View.VISIBLE
        tv_weatherType?.visibility = View.VISIBLE
        tv_temp?.visibility = View.VISIBLE
        tv_max?.visibility = View.VISIBLE
        iv_tempMax?.visibility = View.VISIBLE
        tv_min?.visibility = View.VISIBLE
        iv_tempMin?.visibility = View.VISIBLE
        view?.visibility = View.VISIBLE
        tv_wind?.visibility = View.VISIBLE
        tv_feels_like?.visibility = View.VISIBLE
        tv_thoughts?.visibility = View.VISIBLE
    }

    private fun updateUI(weatherDataModel: WeatherDataModel){
        val date = Date()
        val stringDate: String = DateFormat.getDateTimeInstance().format(date)
        tv_todaysWeather?.text = "${getString(R.string.weather_det)} ${weatherDataModel.name}, $stringDate"
        iv_weatherIcon?.visibility = View.VISIBLE
        tv_weatherType?.text =  if (weatherDataModel.weather?.isNullOrEmpty() == false)
            weatherDataModel.weather[0].description else ""
        tv_temp?.text = "${weatherDataModel.main?.temp?.minus(273.15)?.let { Math.round(it) }}${getString(R.string.temp_degree)}"
        tv_max?.text = "${weatherDataModel.main?.temp_max?.minus(273.15)?.let { Math.round(it) }}${getString(R.string.temp_degree)} C"
        tv_min?.text = "${weatherDataModel.main?.temp_min?.minus(273.15)?.let { Math.round(it) }}${getString(R.string.temp_degree)} C"
        tv_wind?.text = "${getString(R.string.text_wind)} ${weatherDataModel.wind?.speed} ${getString(R.string.wind_unit)}"
        if (weatherDataModel.main?.feels_like != null) {
            tv_feels_like?.text = "${getString(R.string.text_feels_like)} ${weatherDataModel.main?.feels_like?.minus(273.15)?.let {
                Math.round(it)
            }} ${getString(R.string.temp_degree)} C"
        }else tv_feels_like?.text = "${getString(R.string.text_feels_like)} the weather is chilling"
        lat = weatherDataModel?.coord?.lat ?: lat
        long = weatherDataModel?.coord?.lon ?: long
        tv_thoughts?.text = WeatherQuotes.displayThoughts()
    }

    private fun startDeletionService(){
        startService(Intent(this, DeletionService::class.java))
        val cal = Calendar.getInstance()
        val intent = Intent(this, DeletionService::class.java)
        val pintent = PendingIntent
            .getService(this, 0, intent, 0)

        val alarm =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setRepeating(
            AlarmManager.RTC_WAKEUP, cal.timeInMillis,
            86400000.toLong(), pintent
        )
    }

    private fun stopService(){
        stopService( Intent(this, DeletionService::class.java));
    }
}
