package com.aconcepcion.respiriassessment.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.MarginPageTransformer
import com.aconcepcion.respiriassessment.databinding.FragmentHomeBinding
import com.aconcepcion.respiriassessment.today.symptoms.SymptomsContentAdapter
import com.aconcepcion.respiriassessment.utils.setPreviewBothSide
import com.aconcepcion.respiriassessment.utils.toPx

class TodayFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val todayViewModel = ViewModelProvider(this)[TodayViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.vpSymptomsAndTriggers.adapter = SymptomsContentAdapter(requireActivity(), 3)
//        binding.vpSymptomsAndTriggers.setPageTransformer(MarginPageTransformer(16.toPx.toInt()))
        binding.vpSymptomsAndTriggers.setPreviewBothSide()
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}