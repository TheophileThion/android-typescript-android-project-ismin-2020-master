# Android typescript - Android project - ISMIN 2020
[![Mines St Etienne](../logo.png)](https://www.mines-stetienne.fr/)
## Web API
### Summary
This API provide easy access to the film database, it has 3 endpoints:
- films : retrieve a part or all the films in the Db
- films/{filmTitle} : operations on one particular film
- films/genres : retrieve all genres

url : http://imdb.thionman.cleverapps.io/films

### Endpoints
#### /films
- GET
	- Queries:
		- author: filter author or part of authors' names
		- search: search among authors, casting, title, genres, keywords
		- genre: filter genre or part of genres names
		- sort-by: sort the result by specific column
			- asc: if sort-by in query, value 1 for ascending, value -1 for descending
		- limit: limit the number of films returned
	- Returns:
		- List of Films
- POST
	- Body
	```javascript
   {
        "title": "A",
        "url": "B",
        "summary": "C",
        "aggregated_rating": 1,
        "authors": ["D", "E"],
        "casting": ["F", "G"],
        "genres": ["H", "I"],
        "keywords": ["J", "K"],
        "first_release_date": "year-month-day",
        "runtime": 100,
        "cover": "jpgURL",
        "members": 1000,
        "size": 2000,
        "date_added": "year-month-day"   
	}
	```
	- Returns:
		- Film added
#### /films/{filmTitle}
- GET
	- Returns film json object
- DELETE
	- Removes the film from Db
	- Returns film json object