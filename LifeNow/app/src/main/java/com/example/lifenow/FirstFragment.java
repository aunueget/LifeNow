package com.example.lifenow;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lifenow.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private final int FINE_PERMISSIONS = 234;/* It could be any number */
    private final int COURSE_PERMISSIONS = 235;/* It could be any number */
    private final int CHANGE_WIFI_PERMISSIONS = 236;/* It could be any number */
    private final int ACCESS_WIFI_PERMISSIONS = 237;/* It could be any number */
    private final int BLUETOOTH_CONNECT_PERMISSIONS = 238;/* It could be any number */
    private final int BLUETOOTH_SCAN_PERMISSIONS = 239;/* It could be any number */

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("FirstFragment-Scan button","Not scanning here anymore");
            }
        });
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(((MainActivity)getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(((MainActivity)getActivity()),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_PERMISSIONS);
        }
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(((MainActivity)getActivity()),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(((MainActivity)getActivity()),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    COURSE_PERMISSIONS);
        }
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(((MainActivity)getActivity()),
                Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(((MainActivity)getActivity()),
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    ACCESS_WIFI_PERMISSIONS);
        }        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(((MainActivity)getActivity()),
                Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(((MainActivity)getActivity()),
                    new String[]{Manifest.permission.CHANGE_WIFI_STATE},
                    CHANGE_WIFI_PERMISSIONS);
        }
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(((MainActivity)getActivity()),
                Manifest.permission.BLUETOOTH_SCAN)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(((MainActivity)getActivity()),
                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                    BLUETOOTH_SCAN_PERMISSIONS);
        }
        if (ContextCompat.checkSelfPermission(((MainActivity)getActivity()),
                Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(((MainActivity)getActivity()),
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                    BLUETOOTH_CONNECT_PERMISSIONS);
        }
        populateView();
        Log.e("Life_Now", "Info: Trying to populate view");
    }
    @Override
    public void onResume(){
        super.onResume();
        populateView();
        Log.e("Life_Now", "Info: Trying to populate view");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void populateView(){
        //todo get stuff from database and put here
        Log.e("Life_Now", "Info: Trying to populate view");
    }
}