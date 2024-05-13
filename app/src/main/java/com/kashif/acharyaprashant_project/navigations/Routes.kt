package com.kashif.acharyaprashant_project.navigations

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */
sealed class Routes(val routes:String) {

    object  Home:Routes("home")
    object  HomeDetails:Routes("homeDetail")

}