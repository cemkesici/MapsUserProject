package com.cem.mapsuserproject
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cem.mapsuserproject.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager:LocationManager// Lokasyon yönetimi için
    private lateinit var locationListener:LocationListener// Anlık olarak konumu bulmak için
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        locationManager= getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener= LocationListener {
            //lokasyon ya da konum her değiştiğinde çalışır.
            println(it.latitude)
            println(it.longitude)
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //izin verilmemiş
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0)
        }else{
            //İzin verildiyse
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1f,locationListener)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==0){// request kodu kontrol ettik
            if(grantResults.size>0){// sonuç geri gelmiş mi diye kontrol ediyoruz
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){ // izin verildi mi kontrol ediyoruz
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1f,locationListener) // şartlar sağlanıyorsa fonksiyon çalışacak
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}