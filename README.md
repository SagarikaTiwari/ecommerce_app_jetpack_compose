This is a demo E-commerce App that mainly deals with 2 screens - 

1.Product List Screen.
2.Product Detail Screen.


The features that have been implemented are - 
1. Showing Product List.
2. Adding item to wishlist.
3. Showing product detail screen.

The package naming has been done on the basis of "package by layers" as per clean architecture.  
The app follows a MVVM + clean architecture and obeys the SOLID principles. 
The presentation layer contains views, view models and adapters for handling the ui. 
The domain layers contains the business logic and mapper classes for handling the main functionalities. 
The data layer contains ROOM database and retrofit implementation for handling the api responses and Database CRUD operations. 

AppApplication is the Main Application Class and the app uses Hilt for Dependency
Injection. 
App uses Retrofit for the network calls and the api client is in
com.sagarikatiwari.ecommerceapp.data.remote package.

 

Lets try to understand the project in detail, feature-wise :

1. Product List -                * Uses class ProductListFragment for creating the view and uses recycler view
                                 * Uses class ProductCardListAdapter that generates the recycler view list. 
                                 * There are 2 classes for view states - 
                                        i)  ProductListViewState - Holds State of the List 
                                        ii) ProductCardViewState - Holds State of the Product Card (the list item)
                                 * Uses class ProductListViewModel for - loading the list and handling state, handling fav icon click and updating cart icon.
                                 * Uses data class ProductEntity that will be used by the Api Class to fetch data from the server. 
                                 * Uses data class Product that will be used by the business Layer. ProdcutEntity is mapped to Product in the ApiRepository class so that both the data and business layers stay seperate. 


2. Product Detail -              * Uses class ProdcutDetailsFragment for creating the view.
                                 * Uses ProdcutDetailsViewState for holding the state of the screen. 
                                 * Uses ProductDetailsViewModel for fetching product details via Api and showing. 
                                 * Uses data class ProductDetailsEntity that will be used by the Api Class to fetch data from the server.
                                 * Uses data class ProductDetails that will be used by the business Layer. 


4. WishList -                     * Uses class  WishlistDatabaseRepository for fetching data from Database.
                                  * Uses package data.database that uses ROOM for managing the add/remove from wishlist and check if the item is favourite.
                                  * Uses interface WishListRepository to handle the use cases
                                  * Uses class AddOrRemoveFromWishListUseCase to add / remove product from the wishlist repository using execute function.
                                  * Conatins class IsProductInWishListUseCase to check if the product is in wishlist using execute function


6. Dependency Injection -         * Uses class ReppositoryModule to to provide dependency for the Repositories and creating Room and Retrofit instances. 


7. Retrofit Implementation  

8. Unit Testing - This app uses JUnit 5 and mockk for creating mocks of Repositories and testing the functionality. 



 
