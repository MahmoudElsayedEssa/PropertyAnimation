/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.propertyanimation

import android.animation.*
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView


class MainActivity : AppCompatActivity() {

    private lateinit var star: ImageView
    private lateinit var rotateButton: Button
    private lateinit var translateButton: Button
    private lateinit var scaleButton: Button
    private lateinit var fadeButton: Button
    private lateinit var colorizeButton: Button
    private lateinit var showerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        star = findViewById(R.id.star)
        rotateButton = findViewById(R.id.rotateButton)
        translateButton = findViewById(R.id.translateButton)
        scaleButton = findViewById(R.id.scaleButton)
        fadeButton = findViewById(R.id.fadeButton)
        colorizeButton = findViewById(R.id.colorizeButton)
        showerButton = findViewById(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

//        colorizeButton.setOnClickListener {
//            colorizer()
//        }

        showerButton.setOnClickListener {
            shower()
        }
    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)

        animator.apply {
            duration = 1500
            animator.disableViewDuringAnimation(rotateButton)
            start()
        }


    }

    private fun translater() {

        goRight()


    }

    private fun goRight() {
        val animator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 300f)
        animator.apply {
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            duration = 1000
            disableViewDuringAnimation(translateButton)
            start()
        }
    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 5f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 5f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY)
        animator.apply {
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            disableViewDuringAnimation(scaleButton)
            duration = 1000

            start()
        }

    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0.2f)
        animator.apply {
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            disableViewDuringAnimation(fadeButton)
            duration = 1000

            start()
        }
    }

    /*
        private fun colorizer() {
            val animator = ObjectAnimator.ofArgb(
                star.parent,
                "backgroundColor", Color.BLACK, Color.RED
            ).apply {
                duration = 500
                repeatCount = 1
                repeatMode = ObjectAnimator.REVERSE
                disableViewDuringAnimation(bgColor)
                start()
            }

        }
    */
    private fun shower() {
        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = star.width.toFloat()
        var starH: Float = star.height.toFloat()

        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newStar)

        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        newStar.translationX = Math.random().toFloat() *
                containerW - starW / 2


        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y,
            -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION,
            (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()


        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()

        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        set.start()

    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

}
