# EventsSearch
To locally run the app on your own device for testing, make sure to do ONE of the following:
 1. Add `apikey=....` to your `local.properties` file and remember to run `Make module app` to gain access to the `BuildConfig` instance
 2. Or you can replace `${BuildConfig.API_KEY}` with your api key in the `GET` statement of [EventsApi](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/data/remote/EventsApi.kt) seen here:
    ![Screen Shot 2022-12-08 at 4 39 05 AM](https://user-images.githubusercontent.com/16326086/206448798-6778cf72-d2e3-4287-866b-13a028f364a2.png)


### Search Screen Details
[SearchScreen](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/ui/composable/SearchScreen.kt) composable consumes a ui state instance referred to as [searchListUiState](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/ui/composable/SearchScreen.kt#L76) from the `SearchEventsViewModel` that consists of the states at the bottom of this section.

[SearchEventsViewModel](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/ui/viewmodel/SearchEventsViewModel.kt)  contains two functions:
 - **search(..)**: Accepts a non-empty string to interact with the `SearchRepository` to return either the `Success`, `Error` or `Loading` states that the 
     `SearchScreen` composable will handle
 - **reset()**: This is called from the `SearchScreen` composable to notify us that we should go back to the `Uninitialized` state since the textfield has
     an empty string.
     
     - `Success`, `Error`, `Loading` and `Uninitialized` are instances of [SearchListUiState](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/ui/viewmodel/SearchEventsViewModel.kt#L16)


|                       Uninitialized                               |                         Loading                                   |                              Error                                |                             Success                               |                                                              
| ------------------------------------------------------------------|-------------------------------------------------------------------|-------------------------------------------------------------------|-------------------------------------------------------------------|  
| <img src="https://tinyurl.com/2ru6k5kc" width="200" height="400"> | <img src="https://tinyurl.com/2w7vbrn9" width="200" height="400"> | <img src="https://tinyurl.com/3a33mfka" width="200" height="400"> | <img src="https://tinyurl.com/282ku9sx" width="200" height="400"> |



 
### Offline Approach Details
[SearchRepository](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/data/repository/SearchRepositoryImpl.kt) contains two functions:
- **search(..)**: Accepts a non-empty string named 'keyword' and will return a flow of [EventUi](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/data/model/EventUi.kt) objects to then pass on to the viewModel to handle. 

  We do the following:
   - call the *refresh* method (given we have network access provided by [WifiService](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/utils/WifiService.kt)) to make a network call and store into the database.
   - Using the [EventDao](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/data/local/EventDao.kt) that emits a Flow consisting of a list of events
      with a `keyword` attribute that matches the one entered, we then map it to an `EventUi` to be consumed at the UI layer to finish building the outer flow.
   - The `distinctUntilChanged` operator is used to avoid erroneously emitting a previous list of events for a new keyowrd
      
- **refresh(..)**: Accepts a non-empty string named `keyword` and is entirely in charge of making network call.

   We do the following:
   - With the *[EventRemote](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/data/model/EventRemote.kt)* model fetched from the network, we then map that to the *[Event](https://github.com/palexis3/EventsSearch/blob/main/app/src/main/java/com/example/eventsearch/data/model/Event.kt)* model that the database is aware of using [Room Database](https://developer.android.com/training/data-storage/room/defining-data?hl=en) concepts.
     - If you look at the `Event` data clas, there's a composite key consisting of the `keyword` and `id` to ensure events can be tagged to multiple keywords and not be singled out to one. 
   - Any `Event` models that never existed with a unique `keyword` and `id` pair is stored else we ignore inserting it since it exists already.
     
                                                                                                                                                            

|                    Online to Offline                                                                   |                                            Offline to Online                                                       | 
|---------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------| 
|      https://user-images.githubusercontent.com/16326086/206458139-ad0953ef-faa0-4c98-ad9f-2e8cae6949b5.mp4|  https://user-images.githubusercontent.com/16326086/206544712-599e6739-f3ae-4b1e-b790-5aef4403d72e.mp4 |



### Testing
Used [CashApp's Turbine](https://github.com/cashapp/turbine) library to test Flow emissions as can be seen [here](https://github.com/palexis3/EventsSearch/blob/main/app/src/test/java/com/example/eventsearch/ui/viewmodel/SearchEventsViewModelTest.kt)


### App Showcase

#### Online
https://user-images.githubusercontent.com/16326086/206553826-504d9957-8667-44e9-a213-672beef8aa3f.mp4

#### To then offline
https://user-images.githubusercontent.com/16326086/206555385-6f4351a6-5537-4e1e-9089-c791af3f36ac.mp4


