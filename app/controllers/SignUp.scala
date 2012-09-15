package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import model._

object SignUp extends Controller {

  val loginForm = Form(
    tuple(
      "login" -> text,
      "password" -> nonEmptyText))
  def login = Action {
    Ok(views.html.signup.login(loginForm))
  }

  def submit = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      hasErrors => Ok(views.html.index("error")),
      value => {
        registerVisitor(value._1, request.headers.get(HOST), request.headers.get(USER_AGENT))
        val logged = isUserRegister(value._1, value._2)
        if (!logged) {
          Redirect(routes.Login.form)
        } else {
          User.current.username = value._1
          User.current.isLoggedIn = true
          Ok(views.html.index("Wellcome " + value._1 ))
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

  private def isUserRegister(name: String, password: String): Boolean = {
    User.all.find(user => user.username == name && user.password == password) match {
      case Some(u) => true
      case None => false
    }
  }

}