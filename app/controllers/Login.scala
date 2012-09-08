package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import model.User
import views.html.index


object Login extends Controller {
  
  /**
   * Sign Up Form definition.
   *
   * Once defined it handle automatically, ,
   * validation, submission, errors, redisplaying, ...
   */
  val signupForm: Form[model.User] = Form(
    
    // Define a mapping that will handle User values
    mapping(
      "username" -> text(minLength = 4),
      "email" -> email,
      
      // Create a tuple mapping for the password/confirm
      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
        // Add an additional constraint: both passwords must match
        "Passwords don't match", passwords => passwords._1 == passwords._2
      ),     
      
      "accept" -> checked("You must accept the conditions")
      
    )
    // The mapping signature doesn't match the User case class signature,
    // so we have to define custom binding/unbinding functions
    {
      // Binding: Create a User from the mapping result (ignore the second password and the accept field)
      (username, email, passwords,  _) => model.User(username, passwords._1, email) 
    } 
    {
      // Unbinding: Create the mapping values from an existing User value
      user => Some(user.username, user.email, (user.password, ""), false)
    }.verifying(
      // Add an additional constraint: The username must not be taken (you could do an SQL request here)
      "This username is not available",
      user => !Seq("admin", "guest").contains(user.username)
    )
  )
  
  /**
   * Display an empty form.
   */
  def form = Action {
    Ok(html.signup.form(signupForm));
  }
  
  /**
   * Display a form pre-filled with an existing User.
   */
  def editForm = Action {
    val existingUser = model.User(
      "fakeuser", "secret", "fake@gmail.com"
    )
    Ok(html.signup.form(signupForm.fill(existingUser)))
  }
  
  /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => BadRequest(views.html.signup.form(errors)),
      
      // We got a valid User value, display the summary
      user => {
        User.create(user.username, user.password, user.email)
        Ok(views.html.index(User.all.mkString(",")))
      }//Ok(views.html.signup.summary(user))
    )
  }
  
}