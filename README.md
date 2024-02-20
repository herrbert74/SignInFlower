# SignInFlower
Test Challenge creating a sign in flow

# Remarks from Zsolt Bertalan

## Tech

* I used commonly used libraries Retrofit 2, Coroutines, Dagger 2, AndroidX, Coil, Realm, MockWebServer and Compose.
* I also used less commonly used libraries, like MVIKotlin, Decompose and Essenty. See details in the structure section.
* I used my base library. This contains code that I could not find in other third party libraries, and what I use in 
  different projects regularly:
  * https://bitbucket.org/babestudios/babestudiosbase

## Structure

* I use a monorepo for such a tiny project, however I used a few techniques to show how I can build an app that 
  scales, even if they are an overkill as they are now.
* The three main sections (module groups in a larger project) are **data**, **domain**, and **ui**.
* **Domain** does not depend on anything and contains the api interfaces, and the model classes.
* **Data** implements the domain interfaces (repos) through the network, local and db packages or modules, and does not 
  depend on anything else, apart from platform and third party libraries. Here only the local package is used, 
  because I only needed SharedPreferences.
* **Ui** uses the data implementations through dependency injection and the domain entities. **Root** package 
  provides the root implementations for Decompose Business Logic Components (BLoCs) and navigation.
* **Di** Dependency Injection through Dagger and Hilt

## Libraries used

* **MVIKotlin**, **Decompose** and **Essenty** are libraries from the same developer, who I know personally. Links to 
  the libraries:
    * https://github.com/arkivanov/MVIKotlin
      * An MVI library used on the screen or component level.
    * https://github.com/arkivanov/Decompose
      * A component based library built for Compose with Kotlin Multiplatform in mind, and provides the glue, what 
        normally the ViewModel and Navigation library does, but better. 
    * https://github.com/arkivanov/Essenty
      * Has some lifecycle and ViewModel wrappers and replacements.

## Justification for above libraries

* I've used MVIKotlin professionally for years.
* I adopted MVIKotlin, then Decompose for private projects too, so I'm most proficient with these. I could have used 
  the standard MVVM and Jetpack Navigation, but it would take more time for me at this point.
* These libraries address issues, that the Google libraries failed to address.
* Risks commonly associated with above libraries
  * Abandoned by author: No real risk, as I can copy to my project, others can pick up, or I can switch to similar.
  * Onboarding: For most people it will be new. That's a real problem, but the library is picking up.
  * More boilerplate initially: they need some initial setup, but they scale well for more screens.

## Tests

* I write many type of tests, here I only wrote unit tests.
* Unit tests use no dependency injection, they instead rely on mocked interfaces. I'm looking into 
doing that without mocking in the future.
* End to end tests use the real application dependencies, so they can be brittle.
* I can also write two kind of integration tests: for integration tests that are technically unit 
tests, I use Robolectric. These could test a class that is closely related to some Android classes, 
like LinkMovementMethod, or they test integration with something complex, like a database for 
example. In this case I use in-memory databases. The second type is an integration UI test, which 
similarly use in-memory database.
* Finally, I can write pure/functional UI tests, where the test target is the UI, so the database and the 
network is fully mocked.
* So to reiterate, no integration tests, end to end tests, and pure UI tests in this project.

## Room for improvement

* Animated transitions between screens
* Using PIN to unlock the app
* Validations
* More Unit tests
* Remove some deprecation warnings due to a Decompose update
