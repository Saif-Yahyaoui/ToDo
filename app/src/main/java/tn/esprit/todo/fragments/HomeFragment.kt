package tn.esprit.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import tn.esprit.todo.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), AddToDoFragment.DialogNextBtnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popUpFragment: AddToDoFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()


    }
    private fun registerEvents(){
        binding.addTaskBtn.setOnClickListener {
            popUpFragment = AddToDoFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(
                childFragmentManager, "AddToDoFragment"
            )
        }
    }

    private fun init(view: View){
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        databaseRef.push().setValue(todo).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,"Todo Saved Successfully" , Toast.LENGTH_SHORT).show()
                todoEt.text = null
            }else{
                Toast.makeText(context,it.exception?.message ,Toast.LENGTH_SHORT).show()
            }
            popUpFragment.dismiss()
        }
    }


}