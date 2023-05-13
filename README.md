# CirclesLoadingView

![circles_loading_view_preview](./demo.gif)

[![](https://jitpack.io/v/quibbler01/WaveLoadingCircleView.svg)](https://jitpack.io/#quibbler01/WaveLoadingCircleView)

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.quibbler01:WaveLoadingCircleView:1.0.2'
	}

Step 3. Add SwitchButton in your layout xml:

    <com.example.circlesloadingview.CirclesLoadingView
        android:id="@+id/circle_loading_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:animDelay="150"
        app:animDistance="30dp"
        app:animDuration="500"
        app:animInterpolator="accelerate"
        app:circleRadius="20dp" />

Step 4. Use it in your way.

        //find this SwitchButton
        val switchButton = binding.switchButton
