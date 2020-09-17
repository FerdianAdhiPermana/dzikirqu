package com.mayburger.dzikirqu.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mayburger.dzikirqu.BR
import com.mayburger.dzikirqu.R
import com.mayburger.dzikirqu.databinding.FragmentHomeBinding
import com.mayburger.dzikirqu.model.BookDataModel
import com.mayburger.dzikirqu.ui.adapters.BookAdapter
import com.mayburger.dzikirqu.ui.base.BaseFragment
import com.mayburger.dzikirqu.ui.main.book.BookListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), BookAdapter.Callback {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var bookAdapter: BookAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.lifecycleOwner = viewLifecycleOwner
        navController = Navigation.findNavController(view)
        rvBooks.adapter = bookAdapter
        bookAdapter.setListener(this)
        buildLocationPermission()
        viewModel.buildPrayerTime()

//        CoroutineScope(IO).launch {
//            val books = viewModel.dataManager.getBooks()
//            books.filter {
//                it.data.status != "3" && it.data.type != "qur"
//            }.map { book ->
//                val hey = viewModel.dataManager.getBookData(book.data.type).filter {
//                    it.data.language == book.data.language
//                }.map {it ->
//                    PrayerDataModel.create(it.data)
//                }
//                Firebase.firestore.collection("books").add(
//                    BookDataModel.create(book.data,hey)
//                )
////                if (hey.isNotEmpty()) {
////                    Firebase.firestore.collection("books").add(
////                        BookDataModel.create(book.data,PrayerDataModel.create(
////                            hey[0].data
////                        ))
////                    )
////                }
//            }
//        }
    }

    private fun writeToFile(data: String, context: Context) {
        val downloads =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        try {
            val outputStreamWriter =
                OutputStreamWriter(FileOutputStream(downloads.path + "/dzan"))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: " + e.toString())
        }
    }

    private val LOCATION_PERMISSION_CODE = 9928

    @SuppressLint("MissingPermission")
    private fun buildLocationPermission() {
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            viewModel._updatePrayerTime.value = true
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_CODE
        )
    }

    fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun showRequestPermissionsInfoAlertDialog(makeSystemRequest: Boolean) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Location Permission") // Your own title
        builder.setMessage("This app require the device's location in order to get accurate prayer time") // Your own message

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            if (makeSystemRequest) {
                requestLocationPermission()
            }
        }

        builder.setCancelable(false)
        builder.show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showRequestPermissionsInfoAlertDialog(true)
            } else {
                viewModel._updatePrayerTime.value = true
            }
        }
    }

    override fun onSelectedItem(book: BookDataModel) {
        val fragment = BookListFragment()
        fragment.arguments = BookListFragment.getBundle(book.type)
        fragment.show(requireActivity().supportFragmentManager, "")
    }
}