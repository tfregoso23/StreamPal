package com.example.myapplication;

import java.util.Arrays;
import java.util.List;

/**
 * This sets up the array list of all initial movies for when the app launches
 */
public class MovieDataInit {
    public static List<Movie> getInitMovies(){
        return Arrays.asList(
                new Movie("Whiplash", 2014, "Drama", StreamingPlatform.NETFLIX),
                new Movie("Star Wars: Episode IV – A New Hope", 1977, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode V – The Empire Strikes Back", 1980, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode VI – Return of the Jedi", 1983, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode I – The Phantom Menace", 1999, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode II – Attack of the Clones", 2002, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode VI – Revenge of the Sith", 2005, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode VII – The Force Awakens", 2015, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode VIII – The Last Jedi", 2017, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Star Wars: Episode VI – The Rise Of Skywalker", 2019, "Sci-fi", StreamingPlatform.DISNEY_PLUS),
                new Movie("Harry Potter and the Sorcerer's Stone",2001, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Harry Potter and the Chamber of Secrets",2002, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Harry Potter and the Prisoner of Azkaban",2004, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Harry Potter and the Goblet of Fire",2005, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Harry Potter and the Order of Phoenix",2007, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Harry Potter and the Half Blood Prince",2009, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Harry Potter and the Deathly Hallows Part 1",2010, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Harry Potter and the Deathly Hallows Part 2",2011, "Fantasy", StreamingPlatform.HBO_MAX),
                new Movie("Titanic",1997, "Drama", StreamingPlatform.PARAMOUNT_PLUS),
                new Movie("Five Nights at Freddy's",2023, "Horror", StreamingPlatform.PEACOCK),
                new Movie("Pitch Perfect",2012, "Comedy", StreamingPlatform.PEACOCK),
                new Movie("Scott Pilgrim Vs. the World",2010, "Comedy", StreamingPlatform.NETFLIX),
                new Movie("The Hateful Eight",2015, "Drama", StreamingPlatform.NETFLIX),
                new Movie("Scarface",1983, "Drama", StreamingPlatform.NETFLIX),
                new Movie("A Beautiful Mind",2001, "Drama", StreamingPlatform.NETFLIX),
                new Movie("Interstellar",2014, "Sci-fi", StreamingPlatform.PARAMOUNT_PLUS),
                new Movie("Top Gun Maverick",2022, "Action", StreamingPlatform.PARAMOUNT_PLUS),
                new Movie("Five Feet Apart",2014, "Romance", StreamingPlatform.HULU),
                new Movie("Violent Night",2021, "Action", StreamingPlatform.PRIME_VIDEO),
                new Movie("Cocaine Bear",2023, "Action", StreamingPlatform.PRIME_VIDEO),
                new Movie("Nope",2022, "Horror", StreamingPlatform.PRIME_VIDEO),
                new Movie("Doctor Sleep",2019, "Horror", StreamingPlatform.TUBI),
                new Movie("The Night Before",2015, "Comedy", StreamingPlatform.TUBI)
        );

    };
}
