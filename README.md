# Web Quiz Engine

A simple Web Quiz Engine REST API, to exercise Spring Boot basics concepts in Java. The requirements of the project was provided by [JetBrains Academy](https://hyperskill.org/projects/91).

# Technologies

- Java 11
- Spring Boot 2.6.7
- Gradle 7.4.1
- H2 Database

# How it works

<b>Database</b>

All user and quizzes data is stored in an embedded H2 Database.

<b>Authentication</b>

Basic Authentication is used to make all the operations below (Except when registering a new user). You must provide the same email and password used in the register of a new user.

<b>Register a new user</b>

To register a new user, the client needs to send an email and password through a JSON, via POST request in `/api/register:`
```
{ 	
  "email":"abc@abc.com",
  "password":"12345" 
}
```
- The server returns `200` (OK) status code if the registration was successful.
- If the email was already in use by another user, the server returns `400` (BAD REQUESST) status code.
- Email must be in email format, and the password must have at least 5 characters. If not, the server returns `400` (BAD REQUEST) status code.

<b>Create a new quiz</b>

To create a new quiz, the client needs to send the quiz details through a JSON, as shows below, via POST request in `/api/quizzes`
```
{ 
  "title": "The First programming language",
  "text": "What is the first programming language?",
  "options": ["R","C","Java","Cobol"],
  "answer": [0,2]
}
```
- The server returns `200` (OK) status code and the quiz details if the creation was successful.
- All fields are required and the quiz must have at least 2 options. If not, the server will return `400` (BAD REQUEST) status code.

<b>Solve a quiz by id </b>

To solve a quiz, the client needs to send a list of answers, via POST request in `/api/quizzes/{id}/solve`, replacing `{id}` with the id of the quiz.
```
{ 
  "answer": [0,2] 
}
```
- The server returns `200` (OK) status code and the quiz anwser feedback (if you got it right or wrong).
- If a quiz with this id didn't exist, the server will return `404` (NOT FOUND) status code.

<b>Get a quiz by id</b>
 
To get quiz details, the client needs to send a GET request to `/api/quizzes/{id}`, replacing `{id}` with the id of the quiz.

- The server returns `200` (OK) status code and the quiz details if the quiz exists. If not, it returns `404` (NOT FOUND) status code.

<b>Get all quizzes</b> 

To get all quizzes made by users, the client needs to send a GET request to /api/quizzes?page={X}, replacing {X} with a page number.

- The server returns `200` (OK) status code and the quizzes details, with some aditional datas about the page.

<b>Delete a quiz by id</b>

To delete a quiz, the client needs to send a DELETE request to /api/quizzes/{id}, replacing {id} with the id of the quiz.

- The server returns `200` (OK) status code if the quiz was deleted succesfully.
- If the quiz doesn't belong to the user that is deleting or if the quiz don't exist, returns a 404 (NOT FOUND) status code.
