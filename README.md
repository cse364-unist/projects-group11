# milestone 3 Document

## How to access our application?
```
1. Download the files and run the following commands:

    $ docker build -t image_name /path/to/Dockerfile
    $ docker run -d -p 8080:8080 image_name

2. Then, in the host machine, you can access our application in a web browser by visiting http://localhost:8080/
```

## /recommendations

### How to use
```
- Select the gender of new person through the box next to "Gender".
- Select the age of new person through the box next to "Age".
- Select up to 3 genres which new person want to avoid through the box next to "Genre".
- After that, pressing "Add Person" button to add that person in your group.
- Pressing "Remove" button on the right will remove that person's information.
- After entering the group's information, press "Submit" button to go to the movie recommendation page for the group.
- On the movie recommendation page, we recommend 10 movies with high expected ratings for the group.
```

## /gift

### How to Use
```
- Click "Enter Movie Title" box next to "Movie" to enter the title of the movie.
- Click "Type message..." box next to "Message" to enter the message.
- After that, pressing "Submit" button creates a gifticon containing entered movie title and message.

- Pressing "Share to others!" button on the generated gifticon screen  creates a gifticon link to send to others and copies the link.
- If the recipient of the gifticon gift presses the link and comes in, the same gifticon screen will be reached.
- You can use the gifticon by pressing "Use gifticon!" button.
```

## /comparison

### How to Use
```
- Click "Enter Movie Title" box next to "Movie" to enter the title of the movie.
- When two movies on the database are selected, the "Submit" button is activated.
- Press "Submit" button to go to the movie comparison page.

- It shows the data of the two movies and emphasizes the movies won by the crown.
```