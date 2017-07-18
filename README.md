# android-viewpager-indicator
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--viewpager--indicator-green.svg?style=true)](https://android-arsenal.com/details/1/4182)

![alt tag](https://raw.githubusercontent.com/ravindu1024/android-viewpager-indicator/master/images/screen1.png)
![alt tag](https://raw.githubusercontent.com/ravindu1024/android-viewpager-indicator/master/images/screen2.png)
![alt tag](https://raw.githubusercontent.com/ravindu1024/android-viewpager-indicator/master/images/screen3.png)

A simple customizable indicator for ViewPagers

Installation:

1) Add the following to the main project gradle file:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2) Add this to the module gradle file:
```gradle
	dependencies {
	        compile 'com.github.ravindu1024:android-viewpager-indicator:1.0.0'
	}

```

Usage:

1) add using xml:
```xml
    <com.ravindu1024.indicatorlib.ViewPagerIndicator
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_horizontal|center_vertical"
        android:id="@+id/pager_indicator"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"/>
```
        
2) Then in code:
```java
    ViewPagerIndicator indicator = (ViewPagerIndicator) findViewById(R.id.pager_indicator);
    indicator.setPager(pager);  //pager - the target ViewPager
```
    
NOTE: Changes to the ViewPager pages at runtime will be automatically handled when you call notifyDataSetChanged().


