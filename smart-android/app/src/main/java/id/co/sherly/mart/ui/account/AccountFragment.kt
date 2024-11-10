/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.data.model.User
import id.co.sherly.mart.databinding.FragmentAccountBinding
import id.co.sherly.mart.ui.base.view.BaseFragment
import id.co.sherly.mart.ui.onboarding.OnBoardingActivity
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(), AccountContract.View {

    @Inject
    lateinit var presenter: AccountPresenter

    private var user: User? = null

    companion object {
        fun newInstance(): AccountFragment {
            val args = Bundle()

            val fragment = AccountFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            presenter.authCheck()
            binding?.layFaq?.setOnClickListener {

            }

            /** sign out */
            binding?.laySignOut?.setOnClickListener {
                presenter.signOut()
            }
        }
    }

    override fun showUser(user: User?) {
        user?.let {
            this.user = it
            binding?.nameInitialsTv?.visibility = View.VISIBLE
            user.let {
                binding?.nameInitialsTv?.text = it.name?.substring(0, 1)
                binding?.imageViewProfile?.visibility = View.INVISIBLE
                binding?.textName?.text = it.name
            }
        }
    }


    override fun launchOnboardingActivity() {
        startActivity(
            Intent(
                requireActivity(),
                OnBoardingActivity::class.java
            )
        )
        requireActivity().finish()
    }
}