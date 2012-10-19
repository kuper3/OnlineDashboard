package controllers

import play.api.mvc._
import controllers.signup.UserManager
import model.Visitor

object Administration extends Controller {

  def showVisitors  = Action { implicit request =>
    session.get(UserManager.SESSION_ID).map{ user =>
      Ok(views.html.index(Visitor.all.toString))
    }.getOrElse{
      Unauthorized(UserManager.UNAUTHORIZED_MSG)
    }
  }
}
