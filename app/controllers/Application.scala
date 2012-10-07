package controllers

import play.api.mvc._
import views.html.index

object Application extends Controller {

  def index = Action { implicit request =>
    session.get("connected").map { user =>
      Redirect(routes.NetworkConnector.fetch)
    }.getOrElse {
      Ok(views.html.index("Hello!"))
    }

  }

  def login = Action { implicit request =>
    session.get("connected").map { user =>
      Redirect(routes.Application.index).withSession(session - "connected")
    }.getOrElse {
      Redirect(routes.SignUp.login)
    }
  }

}
