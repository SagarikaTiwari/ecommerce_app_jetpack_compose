This is a demo E-commerce App that mainly deals with 2 screens - 1.Product List Screen. 2.Product
Detail Screen.

The features that have been implemented are - 1. Showing Product List. 2. Adding item to wishlist.
3. Adding item to cart. 4. Showing product description.

The package naming has been done on the basis of "package by feature". Every package has a data,
business and presentation layer. The app follows a MVVM + clean architecture and obeys the SOLID
principles. AppApplication is the Main Application Class and the app uses Hilt for Dependency
Injection. App uses Retrofit for the network calls and the api client is in
com.sagarikatiwari.shared.data.repository.api package.

The presentation layer of com.sagarikatiwari.shared contains the Main Activity which is the starting
point of the application. The activity uses the nav graph to navigate between the two fragments - 
A. ProductListFragment
B. ProductDetailsFragment

Lets try to understand the project in detail, feature-wise :

1. Product List - Package - com.sagarikatiwari.product_list

            Presentation Layer - * Contains class ProductListFragment for creating the view and uses recycler view
                                 * Contains class ProductCardListAdapter that generates the recycler view list. 
                                 * There are 2 classes for view states - 
                                        i)  ProductListViewState - Holds State of the List 
                                        ii) ProductCardViewState - Holds State of the Product Card (the list item)
                                 * Contains class ProductListViewModel for - loading the list and handling state, handling fav icon click and updating cart icon.

            Data Layer - * Contains data class ProductEntity that will be used by the Api Class to fetch data from the server. 

            Business Layer - * Contains data class Product that will be used by the business Layer. ProdcutEntity is mapped to Product in the ApiRepository class so that both the data and business layers stay seperate. 


2. Product Detail - Package -  com.sagarikatiwari.product_details 

            Presentation Layer - * Contains class ProdcutDetailsFragment for creating the view.
                                 * Contains ProdcutDetailsViewState for holding the state of the screen. 
                                 * Contains ProductDetailsViewModel for fetching product details via Api and showing. 

            Data Layer - * Contains data class ProductDetailsEntity that will be used by the Api Class to fetch data from the server.

            Business Layer - * Contains data class ProductDetails that will be used by the business Layer. 


4. WishList - Package - com.sagarikatiwari.wishlist

               Data Layer - * Contains class  WishlistDatabaseRepository for fetching data from Database.
                            * Contains package database that uses ROOM for managing the add/remove from wishlist and check if the item is favourite.

               Business Layer - * Contains interface WishListRepository to handle the use cases
                                * Contains class AddOrRemoveFromWishListUseCase to add / remove product from the wishlist repository using execute function.
                                * Conatins class IsProductInWishListUseCase to check if the product is in wishlist using execute function

5. Cart - Package - com.sagarikatiwari.cart 
   
               Data Layer - * Contains class CartRepositorySharedPreferences to add the product ids to the shared prefrences Set. 

               Business Layer - * Contains interface CartRepository to handle add/remove/observe the cart. 

6. Dependency Injection - Package - com.sagarikatiwari.di 

               * Contains class ReppositoryModule to to provide dependency for the Repositories and creating Room and Retrofit instances. 


7. Retrofit Implementation - Package - com.sagarikatiwari.shared.data.repository.api

8. Unit Testing - This app uses JUnit 4 and mockk for creating mocks of Repositories and testing the functionality. 



 
