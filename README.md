# milestone 3 Document

## How to access our application?

1. Download the files and run the following commands:

```
    $ docker build -t image_name /path/to/Dockerfile
    $ docker run -d -p 8080:8080 image_name
```
2. Then, in the host machine, you can access our application in a web browser by visiting http://localhost:8080/

![Main page](Recommendation%20page.png)

## /recommendation

### How to Use recommendation?

- Select the gender of new person through the box next to "Gender".
- Select the age of new person through the box next to "Age".
- Select up to 3 genres which new person want to avoid through the box next to "Genre".
- After that, pressing "Add Person" button to add that person in your group.
- Pressing "Remove" button on the right will remove that person's information.
- After entering the group's information, press "Submit" button to go to the movie recommendation page for the group.

- On the movie recommendation page, we recommend 10 movies with high expected ratings for the group.
- Pressing "Back" button on the bottom to return main page.

## /gift

### How to Create a movie gifticon?

- Click "Enter Movie Title" box next to "Movie" to enter the title of the movie.
- In this part, if you enter a keyword for the movie, the movie containing that keyword comes out.
- If the movie of containing that keyword is not exist, then system give you error message.
- Click "Type message..." box next to "Message" to enter the message.
- After that, pressing "Submit" button creates a gifticon containing entered movie title and message.

- Pressing "Share to others!" button on the generated gifticon screen  creates a gifticon link to send to others and copies the link.
- If the recipient of the gifticon gift presses the link and comes in, the same gifticon screen will be reached.
- Pressing "Back" button on the bottom to return main page.

### How to Search a gifticon link?

- Click "Enter Gift Link" box next to "Gift Link" to enter the gifticon link which is shared or copied.
- If the link is invalid, then system give you error message.
- press "Submit" button to go to the movie recommendation page for the group.

- Pressing "Share to Others" button on the shown gifticon screen copies the gifticon link.
- If the recipient of the gifticon gift presses the link and comes in, the same gifticon screen will be reached.
- Pressing "Back" button on the bottom to return main page.


## /comparison

### How to Use comparison?

- Click each "Enter Movie Title" box next to "Movie" to enter the title of the each movie.
  In this part, if you enter a keyword for the movie, the movie containing that keyword comes out.
  If the movie of containing that keyword is not exist, then system give you error message.
- When two movies on the database are selected, the "Submit" button is activated.
- Press "Submit" button to go to the movie comparison page.

- Show the winner with a little description at the top of the movie comparision page.
- And the page shows the data of the two movies with graph.
  The first graph is the distribution of the gender of the person who gave good rating.
  The second graph is the distribution of the age of the person who gave good rating.
- Pressing "Back" button on the bottom to return main page.

