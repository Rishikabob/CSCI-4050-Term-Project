package com.example.theatertest;

public class Admin extends User {

    /**
     * When an admin fills out a form with new movieTitle details, they will hit submit. That button
     * will trigger the addMovie method, which will use MySQL to add that new row in the Movies table
     * in the database. 
     */
    void addMovie() {
       
    }

    /**
     * An admin will select  movie to delete from the website. After hitting the submit button, the 
     * deleteMovie method will be called, which will delete this Movie's row from the Movie table in 
     * the database.
     */
    void deleteMovie() {

    }

    /**
     * An admin will edit the web form of movie details. After hitting the submit button, the editMovie 
     * method will be called, which will reset the values of the Movie attributes and update its row in 
     * the Movie table in the database.
     */
    void editMovie() {

    }

    /**
     * An admin will select to add a promotion to a user's account. The submit button will trigger the
     * addPromo method, which will add a 'True' value to the Promotion attribute of a User in the Users table
     * in the database.
     */
    void addPromo() {

    }

    /**
     * A admin will select to remove a promotion to a user's account. The submit button will trigger the
     * removePromo method, which will add a 'False' value to the Promotion attribute of a User in the Users table
     * in the database.
     */
    void removePromo()  {

    }

    /**
     * An admin will edit the specified User's promotion status. The submit button will trigger the 
     * editPromo method, which will update the value of the Promotion attribute in the Users table
     * in the database.
     */
    void editPromo() {

    }

    /**
     * ?
     */
    void manageUsers() {

    }

    /**
     * An admin will fill out the form to update the ticket prices. The submit button will trigger the 
     * manageTicketPrices method, which will update the values of the ticket values in the Ticket table in
     * the database.
     */
    void manageTicketPrices() {

    }

}