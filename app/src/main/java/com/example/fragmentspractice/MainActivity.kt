package com.example.fragmentspractice

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import kotlin.random.Random

const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"
const val FRAGMENTS_IN_ASCENDING_ORDER_KEY = "FRAGMENTS_IN_ASCENDING_ORDER_KEY"

class MainActivity : AppCompatActivity(), ButtonFragment.ButtonFragmentCommunicator {

    var fragmentsInAscendingOrder: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        savedInstanceState?.let {
            fragmentsInAscendingOrder = it.getBoolean(FRAGMENTS_IN_ASCENDING_ORDER_KEY)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_1, ButtonFragment())
                add(R.id.fragment_2, ColorfulFragment(), SECOND_FRAGMENT_TAG)
                add(R.id.fragment_3, ColorfulFragment(), THIRD_FRAGMENT_TAG)
            }
        }
    }

    override fun onChangeColorClicked() {
        supportFragmentManager.commit {
            replace(
                R.id.fragment_2,
                ColorfulFragment.newInstance(Random.nextInt(0, 0xFFFFFF)),
                if (fragmentsInAscendingOrder) SECOND_FRAGMENT_TAG else THIRD_FRAGMENT_TAG
            )
            replace(
                R.id.fragment_3,
                ColorfulFragment.newInstance(Random.nextInt(0, 0xFFFFFF)),
                if (fragmentsInAscendingOrder) THIRD_FRAGMENT_TAG else SECOND_FRAGMENT_TAG
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putBoolean(FRAGMENTS_IN_ASCENDING_ORDER_KEY, fragmentsInAscendingOrder)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onSwapFragmentClicked() {
        val fragment2 =
            supportFragmentManager.findFragmentByTag(SECOND_FRAGMENT_TAG) as? ColorfulFragment
        val fragment3 =
            supportFragmentManager.findFragmentByTag(THIRD_FRAGMENT_TAG) as? ColorfulFragment
        if (fragmentsInAscendingOrder) {
            supportFragmentManager.commit {
                replace(
                    R.id.fragment_2,
                    ColorfulFragment.newInstance(fragment3?.color),
                    THIRD_FRAGMENT_TAG
                )
                replace(
                    R.id.fragment_3,
                    ColorfulFragment.newInstance(fragment2?.color),
                    SECOND_FRAGMENT_TAG
                )
            }
        } else {
            supportFragmentManager.commit {
                replace(
                    R.id.fragment_2,
                    ColorfulFragment.newInstance(fragment2?.color),
                    SECOND_FRAGMENT_TAG
                )
                replace(
                    R.id.fragment_3,
                    ColorfulFragment.newInstance(fragment3?.color),
                    THIRD_FRAGMENT_TAG
                )
            }
        }
        fragmentsInAscendingOrder = !fragmentsInAscendingOrder
    }
}