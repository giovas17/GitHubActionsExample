package com.dwtraining.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dwtraining.archcompmodule2.R
import com.dwtraining.archcompmodule2.databinding.FragmentLoginBinding
import com.dwtraining.base.BaseFragment
import com.dwtraining.dialogs.SimpleProgressDialog
import com.dwtraining.models.User
import com.dwtraining.viewmodels.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Giovani González
 * Created by Giovani on 2020-06-21.
 */
class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var dialog: SimpleProgressDialog? = null
    private var binding: FragmentLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = SimpleProgressDialog()
        context?.theme?.applyStyle(R.style.LoginTheme, true)
    }

    override fun setContentView(container: ViewGroup?): View =
        FragmentLoginBinding.inflate(layoutInflater).apply {
            binding = this
        }.root

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onErrorOccurred = { property, old, new ->
            Log.d("LogProperty", "$new - $old")
            if (property.name == "usernameError") {
                binding?.UsernameEditText?.error = new
            } else {
                binding?.PasswordEditText?.error = new
            }
        }

        binding?.buttonLogin?.setOnClickListener {
            dialog?.show(requireActivity().supportFragmentManager, "ProgressDialog")
            viewModel.user = User(
                username = binding?.UsernameEditText?.text.toString(),
                password = binding?.PasswordEditText?.text.toString()
            )
            viewModel.login(viewModel.user) { credentialsAreOk ->
                if (credentialsAreOk) {
                    lifecycleScope.launch { goToMain() }
                } else {
                    showBadCredentialsDialog()
                    dialog?.dismiss()
                }
            }
        }
        onBackPressed {
            activity?.finish()
        }
    }

    private fun showBadCredentialsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.error_bad_credentials_title))
            .setMessage(getString(R.string.error_bad_credentials_message))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private suspend fun goToMain() {
        delay(2000)
        dialog?.dismiss()
        val bundle = bundleOf(HAS_TOOLBAR_KEY to true)
        findNavController().navigate(R.id.mainFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}