package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import model.User
import views.html.index


object Login extends Controller {
  
  val signupForm: Form[model.User] = Form(
    mapping(
      "username" -> text(minLength = 4),
      "email" -> email,      
      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
        "Passwords don't match", passwords => passwords._1 == passwords._2
      ),
      
      "accept" -> checked("You must accept the conditions")
      
    )
    {
      // Binding: Create a User from the mapping result (ignore the second password and the accept field)
      (username, email, passwords,  _) => model.User(username, passwords._1, email, false) 
    } 
    {
      // Unbinding: Create the mapping values from an existing User value
      user => Some(user.username, user.email, (user.password, ""), false)
    }
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
      "fakeuser", "secret", "fake@gmail.com", false
    )
    Ok(html.signup.form(signupForm.fill(existingUser)))
  }
  
  /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      errors => BadRequest(views.html.signup.form(errors)),
      
      user => {
        User.create(user.username, user.password, user.email)
        Ok(views.html.index(User.all.mkString(",")))
      }
    )
  }
  
}