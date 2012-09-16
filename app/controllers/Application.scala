package controllers

import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("You are the boss!"))
  }

  def loggedIn = Action { implicit request =>
    session.get("connected").map { user =>
      Ok("Hello " + user)
    }.getOrElse {
      Unauthorized("Oops, you are not connected")
    }
  }

}
