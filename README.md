# Simple-Twitter-Client
simple android app consumes Twitter api to show user followers

## Specifications
1. opens a web page for authentication with your twitter account
2. after successful login the app, no need to login again, the app remembers logged in user and show his followers 
3. once the user is logged in, the app displays logged-in user followers in a list. The list contains profile image, full name,
handle, and bio. Each cell height will depend if the follower has bio or not. if the
follower has bio it will show all the bio in the cell, and if not it will show just the
name, and handle 
4. tapping on a follower will open the Follower Information screen
5. Server responses are cached for offline use
6. the followers screen implements pull-to-refresh and endless scrloll
7. tapping on a followers the app displays the details (profile and background images and bio) in a new screen
with hiw last ten tweets
8. localization (support Arabic and English) 


## Libraries :
1. Rx java, it reduces async tasks and make code more concise 
2. Retrofit 2 for network call, it supports rx java observables
3. Signpost : for authentication process it supports authorizing Retrofit calls 
4. Butter knife for injecting views 
5. Dagger 2 for dependcies injection 
6. Gaon Retrofit adapter for handling json 
7. Parceler for faster way to make objects parecelable 

## Architecture & Design :
This application developed in MVP architecture.

* Why MVP :

1. achieve seperation of concerns principle (seperate views from bussiness logic stuff)
2. reduce activities classes sizes, make views handle what to show only and delegate logic stuff to presenters and models
3. more testable and maintanable code, presenters and models can be test by simple fast tests becase all android-related work are done by the views only

* Application Arch :

![alt text](https://github.com/AmrElmasry/Simple-Twitter-Client/blob/master/architecture/Arch.png "Application Architecture")


