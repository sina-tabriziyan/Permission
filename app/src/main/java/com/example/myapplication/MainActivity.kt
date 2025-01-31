package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Setup ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Request permission when button is clicked

        PermissionTools.register(this)
        binding.btnAudioRequestPermission.setOnClickListener {
            PermissionTools.request(this@MainActivity,PermissionConstants.CAMERA_PERMISSIONS){ granted->
                if (granted) accessAudioLibrary()
            }
        }


        binding.btnImageRequestPermission.setOnClickListener{
            PermissionTools.request(this@MainActivity,PermissionConstants.IMAGE_PERMISSIONS){granted->
                if (granted) accessImageLibrary()
            }
        }

        binding.btnVideoRequestPermission.setOnClickListener{
            PermissionTools.request(this@MainActivity,PermissionConstants.CONTACT_PERMISSIONS){granted->
                if (granted) accessImageLibrary()
            }
        }











    }

    private fun accessImageLibrary() {
        Toast.makeText(this, "Audio Image Granted!", Toast.LENGTH_LONG).show()
    }

    private fun accessAudioLibrary() {
        Toast.makeText(this, "Audio Access Granted!", Toast.LENGTH_LONG).show()
    }
}
