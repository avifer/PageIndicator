package es.avifer.pageindicatorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import es.avifer.pageindicatorapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction()
            .add(R.id.activity_main__fragment, ParentFragment())
            .addToBackStack("")
            .commitAllowingStateLoss()

    }

}
