package tn.esprit.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import tn.esprit.todo.databinding.FragmentAddToDoBinding


class AddToDoFragment : DialogFragment() {
    private lateinit var binding: FragmentAddToDoBinding
    private lateinit var listener : DialogNextBtnClickListener

    fun setListener(listener: DialogNextBtnClickListener){
        this.listener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddToDoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()

    }
    private fun registerEvents(){
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if(todoTask.isNotEmpty()){
                listener.onSaveTask(todoTask , binding.todoEt)
            }else{
                Toast.makeText(context,"Please Type Your Task" ,Toast.LENGTH_SHORT).show()
            }
        }
        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }
    interface DialogNextBtnClickListener{
        fun onSaveTask(todo : String , todoEt : TextInputEditText )
    }
}