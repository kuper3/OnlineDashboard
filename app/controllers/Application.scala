package controllers

import play.api.mvc._

object Application extends Controller { 

  def index = Action {
    Ok(views.html.index("You are the boss!"))
  }
}
