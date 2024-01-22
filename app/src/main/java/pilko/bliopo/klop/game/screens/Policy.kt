package pilko.bliopo.klop.game.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.orhanobut.hawk.Hawk
import pilko.bliopo.klop.GameObj
import pilko.bliopo.klop.R
import pilko.bliopo.klop.databinding.FragmentPolicyBinding


class Policy : Fragment() {
    private lateinit var binding:FragmentPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPolicyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newView = WebView(requireContext())
        newView.settings.javaScriptEnabled = true
        newView.settings.loadWithOverviewMode = true
        newView.webViewClient = WebViewClient()
        newView.loadUrl(Hawk.get(GameObj.hawkPolicyLink, "https://sites.google.com/view/plinkobiggame/"))
        binding.root.addView(newView)
    }
}
