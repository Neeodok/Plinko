package pilko.bliopo.klop

import android.app.Application
import com.orhanobut.hawk.Hawk


class GameApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()

    }
}
