package controllers

import play.api.mvc._
import controllers.signup.UserManager

object Application extends Controller {

  def index = Action { implicit request =>
    session.get(UserManager.SESSION_ID).map { user =>
      //logger.info("User %1&s connected".format(user))
      Redirect(routes.NetworkConnector.fetch)
    }.getOrElse {
      Ok(views.html.index("Hello!"))
    }

  }

  def login = Action { implicit request =>
    session.get(UserManager.SESSION_ID).map { user =>
      Redirect(routes.Application.index).withSession(session - UserManager.SESSION_ID)
    }.getOrElse {
      Redirect(routes.SignUp.login)
    }
  }

}
