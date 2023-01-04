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
Samples of incorrect expressions - empty expressions and expressions with no values in brackets (e. g. "2 + ()"), expressions with missed brackets (e. g. "2+2)"), expressions containing letters, expressions containing numbers started with '.' (e. g. "2 + .2"). Expressions with incorect operators sequence are also incorrect (e. g. "2-*2", but "2* -2" is correct), expressions containing division by zero.
If expression is incorrect, the program returns an error message.
 </div>
<br/>
<div>
Samples of correct expressions - "2*((2+2))" is same as "2*(2+2)", "--1" is same as "+1", "2 + -1" is same as "2 - +1" and "2-1" (but "2/ +-1" or "2* +-1" is incorrect), "2+	2" is equal to "2+2", "2++2" is same as "2+2".
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
 <h2>Run:</h2> 
  <div>
    <div>go to the project directory.
      <div>run: mvn clean package</div>
      <div>then: java -jar target/myTestApp-0.0.1-SNAPSHOT.jar</div>
      <div>go to the *project directory*/react/evaluator</div>
      <div>run: npm install</div>
      <div>then: npm start</div>
  </div>
  <div>You also need to provide acces to your MariaDB database. </div>
  <div>You must set your database url, password and username to *project directory*/src/main/resources/application.properties file and to *project directory*/src/test/resources/test.properties </div>
  <h2> adding expressions examples </h2>
 <div>
 <div align = "center">
  <img src="/screens/good_expession_example_1.png" />
  <h3> adding expression </h3>
  <img src="screens/good_expression_result_1.png" />
  <h3> result (is rounded to three decimal places)</h3>
 </div>
  <br/>
 <div align = "center">
  <img src="/screens/good_expression_example_2.png" />
  <h3> adding expression </h3>
  <img src="screens/good_expression_result_2.png" />
  <h3> result (expression is equal to "(2*(10-5)+10)/-20"</h3>
 </div>
  <br/>
  <div align = "center">
  <img src="/screens/good_expression_example_3.png" />
  <h3> adding expression </h3>
  <img src="screens/good_expression_result_3.png" />
  <h3> result </h3>
 </div>
 </div>
 <br/>
 <h2> Examples of incorrect expressions </h2>
 <div>
 <div align = "center">
  <img src="/screens/bad_expression_example_1.png" />
  <h3> adding empty expression </h3>
  <img src="screens/bad_expression_result_1.png" />
  <h3> result </h3>
 </div>
  <br/>
 <div align = "center">
  <img src="/screens/bad_expression_example_2.png" />
  <h3> adding expression with missed bracket </h3>
  <img src="screens/bad_expression_result_2.png" />
  <h3> result </h3>
 </div>
  <br/>
   <div align = "center">
  <img src="/screens/division_by_zero_example.png" />
  <h3> adding expression with division by zero </h3>
  <img src="screens/division_by_zero_result.png" />
  <h3> result </h3>
 </div>
 <br/>
   <div align = "center">
  <img src="/screens/bad_operand_example.png" />
  <h3> adding expression with bad operands </h3>
  <img src="screens/bad_operand_result.png" />
  <h3> result </h3>
 </div>
 </div>
 <br/>
 <h2> Getting expressions from db examples </h2>
  <div align = "center">
  <img src="/screens/get_by_result_example.png" />
  <h3> get expressions containing same result </h3>
  <img src="screens/get_with_higher_results_example.png" />
   <h3> get expressions containing higher results </h3>
   <img src="screens/get_with_lower_results_example.png" />
   <h3> get expressions containing lower results </h3>
 </div>
 <h2> Changing expressions examples </h2>
 <div>
  <div align = "center">
  <img src="/screens/expression_changing_setting_new_values_example_1.png" />
   <h3> setting new values </h3>
    <img src="/screens/changing_result_1.png"/>
   <h3> result </h3>
 </div>
  <br/>
  <div align = "center">
  <img src="/screens/bad_changing.png" />
   <h3> setting new values with division by zero</h3>
    <img src="/screens/bad_changing_res.png "/>
   <h3> result </h3>
 </div>
 </div>
 
 
