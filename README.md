# ArithmeticEvaluator
<h2> Description </h2>
<div>
This program is dedicated to evaluate arithmetic expressions and obtain the amount of numbers in expression. If the given expression is correct, it will be saved to database. It also gives an opportunity to get expressions containing results equal to (higher / lower than) results recieved from database.
</div>
<br/>
<div>
This is RESTful java Spring Boot application wich implements entity Expression with fields - arithmeticExpression, result and numOfDoubles. If recieved expression is correct, it`ll be saved to database. If expression is empty or incorrect, application will return error message with exception type (message) and exception message (debugMessage).
 </div>
 <br/>
 <div>
Samples of incorrect expressions - empty expressions, expressions with missed brackets (e. g. "2+2)"), expressions containing letters, expressions containing numbers started with '.' (e. g. "2 + .2"). Expressions with incorect operators sequence are also incorrect (e. g. "2-*2", but "2* -2" is correct)
If expression contains division by zero, the program returns an error message.
 </div>
<br/>
<div>
Samples of correct expressions - "2(2+2)" is same as "2*(2+2)", "2*((2+2))" is same as "2*(2+2")", "--1" is same as "+1", "2+ -1" is same as "2- +1" and "2-1", "2+	2" is equal to "2+2", "2++2" is same as "2+2".
</div>
<h2>Used Technologies:</h2>
 <div>
 Back-end: Spring Boot, Spring Web, Spring Data JPA, MariaDB, Lombok.
  </div>
  <div>
 Front-end: ReactJS.
 </div>
 <div>
  Server Build: Maven.
  </div>
  <div>
 Client Build: npm.
 </div>
 <h2> Requirements:</h2>
 <div> Java 17 </div>
 <div> MariaDB </div>
 <div> Maven </div>
