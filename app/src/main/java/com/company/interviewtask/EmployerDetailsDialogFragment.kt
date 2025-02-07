package com.company.interviewtask

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.company.interviewtask.database.DAO
import com.company.interviewtask.databinding.FragmentEmployerDetailsDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_SEARCH_ID = "searchId"

@AndroidEntryPoint
class EmployerDetailsDialogFragment : DialogFragment() {

    companion object {

        val TAG = EmployerDetailsDialogFragment::class.java.simpleName

        fun newInstance(employerId: Long): EmployerDetailsDialogFragment {
            return EmployerDetailsDialogFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_SEARCH_ID, employerId)
                }
            }
        }

    }

    @Inject
    lateinit var dao: DAO

    private var binding: FragmentEmployerDetailsDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentEmployerDetailsDialogBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            val searchId = arguments?.getLong(ARG_SEARCH_ID) ?: return@launch
            dao.getSearch(searchId).let { search ->
                binding?.apply {
                    textviewEmployerdetailsfragmentName.text = search.name
                    textviewEmployerdetailsfragmentPlace.text = search.place
                    textviewEmployerdetailsfragmentDiscount.text =
                        search.discountPercentage.toString()
                    textviewEmployerdetailsfragmentId.text = search.employerId.toString()
                }
            }
        }
    }
}