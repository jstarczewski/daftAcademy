package pl.daftacademy.androidlevelup.view.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.daftacademy.androidlevelup.databinding.FragmentMoviesBinding
import pl.daftacademy.androidlevelup.view.home.HomeActivity

class MoviesFragment : Fragment() {

    private val adapter = MoviesAdapter()
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as HomeActivity).obtainViewModel()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        binding.viewmodel?.start()
    }
}
