# easygradient-kotlin

[![](https://jitpack.io/v/gaobq/easygradient-kotlin.svg)](https://jitpack.io/#gaobq/easygradient-kotlin)

this is a gradient-util write by kotlin(you can also use in you android project which write by java)，
wrap Android's LinearGradient,SweepGradient,RadialGradient.

if you want use android's Gradient,you can use this library's CustomView and CustomLayout as your customview BaseView;
```
<com.heybik.easygradient.sample.GoogleGradientView
        android:id="@+id/google_gradient_view"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_width="match_parent"
        android:layout_height="20dp"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        app:endY = "0"
        app:gradientColors="@array/google_gradient_colors"
        app:gradientPositions="@array/google_gradient_percents"/>
```
*GoogleGradientView is extends GradientBaseView, if you run this project in your android device you'll see a view 
which gradient with 4 colors horizontaly *

custom attrs:
```
app:endY = "0"
app:gradientColors="@array/google_gradient_colors"
app:gradientPositions="@array/google_gradient_percents"
```
more attrs's description in GradientUtil.kt
