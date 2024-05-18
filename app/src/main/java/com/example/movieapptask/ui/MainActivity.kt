package com.example.movieapptask.ui

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.core.extensions.snackBar
import com.example.domain.repositories.MoviesRepository
import com.example.easy_connectivity.ConnectionState
import com.example.easy_connectivity.EasyConnectivity
import com.example.movieapptask.R
import com.example.movieapptask.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var moviesRepository: MoviesRepository
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var easyConnectivity: EasyConnectivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()


        easyConnectivity =
            EasyConnectivity.getInstance(applicationContext.getSystemService() as ConnectivityManager?)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                easyConnectivity.getNetworkStateFlow().collectLatest {
                    val connectionState = it.connectionState
                    when (connectionState) {
                        ConnectionState.Available -> {
                            Snackbar.make(binding.main, "you are online", Snackbar.LENGTH_SHORT)
                                .show()

                        }

                        ConnectionState.AvailableWithOutInternet -> {
                            Snackbar.make(
                                binding.main,
                                "you are currently in offline",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        ConnectionState.Lost -> {
                            Snackbar.make(
                                binding.main,
                                "you are currently in offline",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        ConnectionState.Unavailable -> {
                            Snackbar.make(
                                binding.main,
                                "you are currently in offline",
                                Snackbar.LENGTH_SHORT
                            ).show()

                        }

                        ConnectionState.Losing -> {
                            Snackbar.make(
                                binding.main,
                                "you are currently in offline",
                                Snackbar.LENGTH_SHORT
                            ).show()

                        }
                    }

                }
            }
        }


    }
}