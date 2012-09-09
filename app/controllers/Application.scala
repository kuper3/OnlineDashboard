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
    val r = DB.withConnection("postgre"){
      implicit c =>
        SQL("select 1").execute()    
    }
    Ok(views.html.index("Your new application is ready." + r.toString))

  }
}
