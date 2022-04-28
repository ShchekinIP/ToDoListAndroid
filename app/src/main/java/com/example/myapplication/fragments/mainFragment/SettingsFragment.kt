package com.example.myapplication.fragments.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.activities.LoginActivity
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.utils.LocaleUtils
import java.util.*


class SettingsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}