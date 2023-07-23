package ch.com.findrealestate

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity:ComponentActivity() {
   // let ui test override activity content, no need onCreate here
}
