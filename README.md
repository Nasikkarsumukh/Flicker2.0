# Flicker2.0
Functional requirements

App should display an empty list / grid with loading indicator on first launch and begin fetching items over the network. - Image (for grid layout) or Image & Title (for list layout) should be displayed in a scrollable view - Image thumbnails should be fetched and cached - Display a “Load More” button at end of each batch of thumbnails - “Load More” button should not be displayed after the last batch of thumbnails - App should persist data and display the same on subsequent launch - App should display appropriate error message in case network is unavailable Non-functional requirements
Minimize the number of thumbnails that need to be fetched (i.e., do not begin fetching until a thumbnail is on-screen) - Organize your code into multiple classes as appropriate. (Do not write all code in UIViewController for iOS and Activity / Fragment for Android) - Make sure the UI remains responsive even when network operations are in progress - Use platform provided methods for constructing URLs and encoding query parameters.
"Load More" button: Scroll the on the view to refresh and load the next page
