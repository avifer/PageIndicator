package es.avifer.pageindicatorapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import es.avifer.pageindicatorapp.databinding.FragmentParentBinding

class ParentFragment : Fragment() {

    private var binding: FragmentParentBinding? = null

    private val onPageChangeViewPager = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding?.fragmentParentPageIndicator?.changeSelectedIndicator(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.fragmentParentPageIndicator?.setQuantityIndicator(3,0)
        binding?.fragmentParentViewpager?.let {
            with(it) {
                adapter = ScreenSlidePagerAdapter(this@ParentFragment)
                registerOnPageChangeCallback(onPageChangeViewPager)
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

        override fun getItemCount() = 3

        override fun createFragment(position: Int) =
            when (position) {
                0 -> FirstFragment()
                1 -> SecondFragment()
                else -> ThirdFragment()
            }

    }

}