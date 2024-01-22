package pilko.bliopo.klop.game.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import pilko.bliopo.klop.R


class Menu : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnExit).setOnClickListener {
            activity?.finish()
        }
        view.findViewById<Button>(R.id.btnGame).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_menu_to_game)
        }
        view.findViewById<Button>(R.id.btnPol).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_menu_to_policy)
        }
    }
}
