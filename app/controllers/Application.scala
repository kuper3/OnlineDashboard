package controllers

import anorm._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.db._
import play.api.mvc._
import play.api.Play.current
import model.User

object Application extends Controller { 

  def index = Action {
    DB.getConnection("default", true)
    val r = DB.withConnection{
      implicit c =>
        SQL("select 1").execute()    
    }
    Ok(views.html.index("Your new application is ready." + r.toString))

  }
}
