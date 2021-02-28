package com.hadIt.doorstep.ui.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hadIt.doorstep.R;
import com.hadIt.doorstep.ui.dashboard.DashboardViewModel;

public class Settings extends Fragment {

    public TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_settings,container,false);

        return root;
    }
}
