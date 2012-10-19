package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import model._
import controllers.signup.UserManager
import play.api.libs.ws.WS


object SignUp extends Controller {

  val loginForm = Form(
    tuple(
      "login" -> text,
      "password" -> nonEmptyText))


  def login = Action { implicit request =>
    session.get(UserManager.SESSION_ID).map {user =>
      Redirect(routes.Application.index).withSession(session - UserManager.SESSION_ID)
    }.getOrElse {
      Ok(views.html.signup.login(loginForm)) 
    }
  }

  def submit = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      hasErrors => Ok(views.html.index("error")),
      value => {
        registerVisitor(value._1, request.headers.get(HOST), request.headers.get(USER_AGENT))

        // FIXME improve!!!
        User.getUser(value._1, value._2) match {
          case Some(user) => {
            UserManager.addUser(user)
            Redirect(routes.Application.index).withSession(
              UserManager.SESSION_ID -> user.username)
          }
          case None => Unauthorized("Invalid credentials!")
            /*Redirect(routes.Login.form)*/
        }
      })
  }

  private def registerVisitor(name: String, host: Option[String], user_agent: Option[String]) = {
    var visitorHost = ""
    host match {
      case Some(h) => visitorHost = h
      case None =>
    }

    var visitorAgent = ""
    user_agent match {
      case Some(ua) => visitorAgent = ua;
      case None =>
    }

    Visitor.create(name, visitorHost, visitorAgent)
  }

}