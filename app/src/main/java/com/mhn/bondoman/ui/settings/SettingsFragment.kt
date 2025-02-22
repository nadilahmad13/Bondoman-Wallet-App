package com.mhn.bondoman.ui.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mhn.bondoman.R
import com.mhn.bondoman.database.KeyStoreManager
import com.mhn.bondoman.databinding.FragmentSettingsBinding
import com.mhn.bondoman.ui.login.LoginActivity

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private val titles: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        titles.apply {
            add("Food")
            add("Drink")
            add("Apparel")
            add("Candi Prambanan")
            add("Sepuh WBD")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSettingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.mailTransactionButton.setOnClickListener {
            sendEmail()
        }
        binding.logoutButton.setOnClickListener {
            logout()
        }
        binding.downloadTransactionButton.setOnClickListener {
            export()
        }
        binding.randomizeButton.setOnClickListener {
            Intent().also {
                it.setAction("com.mhn.bondoman.RANDOMIZE_TRANSACTION")
                it.setPackage(requireContext().packageName)
                it.putExtra("selectedTitle", titles.random())
                requireContext().sendBroadcast(it)
            }
        }

        return binding.root
    }

    @SuppressLint("IntentReset")
    private fun sendEmail() {
        EmailDialog().show(
            childFragmentManager, EmailDialog.TAG
        )
    }

    private fun logout() {
        KeyStoreManager.getInstance(requireContext()).removeToken()
        KeyStoreManager.getInstance(requireContext()).removeEmail()
        KeyStoreManager.getInstance(requireContext()).removeExpiry()
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun export() {
        ExportDialog().show(
            childFragmentManager, ExportDialog.TAG
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}