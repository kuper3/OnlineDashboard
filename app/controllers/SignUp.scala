package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object SignUp extends Controller {
  
  val loginForm = Form(
      tuple(
          "login" -> text,
          "password" -> nonEmptyText
          )
      )
  def login = Action {
    Ok(views.html.signup.login(loginForm))
  }
  
  def submit = Action { implicit request =>
    loginForm.bindFromRequest.fold(     
      hasErrors => Ok(views.html.index("error")),
      value => {
        Ok(views.html.index("logged in " + value._1))
      }
   )    
  }

}