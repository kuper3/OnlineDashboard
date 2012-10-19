package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import model.User
import views.html.index
import controllers.signup.UserManager


object Login extends Controller {
  
  val signupForm: Form[model.User] = Form(
    mapping(
      "username" -> text(minLength = 4),
      "password" -> text(minLength = 6),      
      "accept" -> checked("You must accept the conditions")
      
    )
    {
      // Binding: Create a User from the mapping result
      (username, password,  _) => model.User(username, password) 
    } 
    {
      // Unbinding: Create the mapping values from an existing User value
      user => Some(user.username, user.password, false)
    }
  )
  
  /**
   * Display an empty form.
   */
  def form = Action {
    Ok(html.signup.form(signupForm));
  }
  
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
        /*views.html.signup.form(errors)*/
      errors => BadRequest("Invalid credentials!"),
      
      user => {
        UserManager.addUser(user)
        User.create(user.username, user.password)
        Redirect(routes.Application.index).withSession(
              UserManager.SESSION_ID -> user.username)
      }
    )
  }
  
}