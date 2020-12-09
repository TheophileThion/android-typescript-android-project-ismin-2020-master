# Android typescript - Android project - ISMIN 2020
[![Mines St Etienne](../logo.png)](https://www.mines-stetienne.fr/)
## Android App
### Activities
- Main contains the add film button and two tabs :
	- Add button launches the create film fragment
	- Home tab (film grid fragment, search bar)
	- Favorites tab (all favorite films, no search bar)
- Film page (displays all the infos about a film)
- About
### Fragments
- Film list
	- Film grid (All films sorted by first_release_date and filtered thanks to the search bar)
	- Favorites (All favorite films in a horizontal recycler view)
- Create Film (The fragment is prefilled to ease the test)
### Personnal Choices
- Favorites <br>
The favorite films are stored in the shared preferences and not in the Db. People may have different favorites.
- About <br>
The credits of the application are to be found in the action context menu and spawn as an activity.