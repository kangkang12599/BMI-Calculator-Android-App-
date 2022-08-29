package com.example.bmicalculator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.HttpAuthHandler
import android.widget.*
import androidx.core.view.isEmpty
import androidx.databinding.BindingBuildInfo
import androidx.databinding.DataBindingUtil
import com.example.bmicalculator.databinding.FragmentHealthFormBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HealthFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
data class BMIDetails(
    var gender: String ?= "",
    var ageGroup: String ?= "",
    var height: Double ?= 0.0,
    var weight: Double ?= 0.0
)

class HealthFormFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var BMIDetails: BMIDetails = BMIDetails()
    private lateinit var binding: FragmentHealthFormBinding
    private lateinit var selectedRadioButton: RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentHealthFormBinding>(inflater,
                R.layout.fragment_health_form, container, false)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.age_group_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerAgeGroup.adapter = adapter
        }

        //binding.spinnerAgeGroup.onItemSelectedListener = this
        binding.buttonSubmit.setOnClickListener { submitButtonOnClickListener(it) }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
//        var ageGroup = parent.getItemAtPosition(pos)
//        BMIDetails.ageGroup = ageGroup.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    private fun submitButtonOnClickListener(view: View?){
        binding.apply {
            val selectedRadioButtonId: Int = radioGroupGender.checkedRadioButtonId

            if(
                selectedRadioButtonId == -1 ||
                spinnerAgeGroup.isEmpty() ||
                editTextHeight.text.isEmpty() ||
                editTextHeight.text.isEmpty()
            ){
                Toast.makeText(view?.context, "Please fill in all the field", Toast.LENGTH_SHORT).show()
            }else{
                selectedRadioButton = requireView().findViewById(selectedRadioButtonId)
                BMIDetails?.gender = selectedRadioButton.text.toString()
                BMIDetails?.ageGroup = spinnerAgeGroup.selectedItem.toString()
                BMIDetails?.height = editTextHeight.text.toString().toDouble()
                BMIDetails?.weight = editTextWeight.text.toString().toDouble()
                val bmi = BMIDetails.weight!! / (BMIDetails.height!! / 100)
                Toast.makeText(view?.context, "BMI: " + bmi.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HealthFormFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HealthFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}