package com.fakher.multimodule.mvvm.usecase.main


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fakher.multimodule.mvvm.R
import com.fakher.multimodule.mvvm.database.Task
import com.fakher.multimodule.mvvm.database.User
import com.fakher.multimodule.mvvm.di.injector
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, activity?.injector?.getMainVMFactory())
            .get(MainViewModel::class.java)
    }

    private val tasksListAdapter = TasksAdapter()
    private val args: MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = args.user
        setUserName(user)
        signoutBtn.setOnClickListener { onSignoutClicked() }
        deleteUserBtn.setOnClickListener { onDeleteClicked(user) }

        observeViewModel()
        viewModel.loadTasks(user)
    }

    private fun setUserName(user: User) {
        usernameTV.text = user.username
    }

    private fun observeViewModel() {
        viewModel.signout.observe(this, Observer {
            Toast.makeText(activity, "Signed out!", Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })
        viewModel.userDeleted.observe(this, Observer {
            Toast.makeText(activity, "User deleted!", Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })

        viewModel.userTasks.observe(this, Observer { tasks ->
            showTasksList(tasks)
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            mainLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun showTasksList(tasks: List<Task>) {
        handleListVisibility(tasks.isEmpty())
        if (tasks.isNotEmpty()) {
            tasksListAdapter.onAddTasks(tasks)
            tasksList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = tasksListAdapter
            }
        }
    }

    private fun handleListVisibility(isListEmpty: Boolean) {
        noTaskTV.visibility = if (isListEmpty) View.VISIBLE else View.GONE
        tasksList.visibility = if (isListEmpty) View.GONE else View.VISIBLE
    }

    private fun goToSignUpScreen() {
        val action =
            MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(usernameTV).navigate(action)
    }

    private fun onSignoutClicked() {
        viewModel.signOutUser()
    }

    private fun onDeleteClicked(user: User) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete user")
                .setMessage("Are sure you want to delete this user?")
                .setPositiveButton("Yes") { _, _ -> viewModel.deleteUser(user) }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

}
