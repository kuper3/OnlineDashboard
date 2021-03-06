package controllers

import model._
//import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.ws._
import play.api.mvc._
import play.api.templates.Html
//import views.html.defaultpages.unauthorized
import play.api.libs.concurrent.Promise
import controllers.signup.UserManager


object NetworkConnector extends Controller {
  
  /**
   * prod : "http://safe-earth-1849.herokuapp.com/"
   * dev:   "http://localhost:8080/"
   */
  private val host = "http://safe-earth-1849.herokuapp.com/"

  val wordForm :Form[Word] = Form(
    mapping(
      "englishWorld" -> nonEmptyText,
      "translation" -> nonEmptyText)
    {
      (englishWorld,translation) => Word(englishWorld,translation)
    }
    {
      word => Some(word.englishWord, word.translation)
    }
  )

  def fetch = Action { implicit request =>
    session.get(UserManager.SESSION_ID).map { user =>
      val url = host + "random"
      //    Async {
      //      WS.url(url).get().map { response =>
      //        Ok(views.html.main("Word", user)(Html(response.body)))
      //      }
      //    }
      val result: Promise[play.api.libs.ws.Response] = {
        WS.url(url).get
      }
      Ok(views.html.main("Word", user)(Html(result.value.get.body)))
    }.getOrElse {
      Unauthorized(UserManager.UNAUTHORIZED_MSG)
    }
  }

  def add = Action { implicit request =>
    session.get(UserManager.SESSION_ID).map {user =>
      Ok(views.html.network.modifyDictionary(wordForm, user))
    }.getOrElse {
      Unauthorized(UserManager.UNAUTHORIZED_MSG)
    }
  }

  def submit = Action { implicit request =>
    wordForm.bindFromRequest.fold(
      hasErrors => BadRequest,
      word => {
        val url = host + "add?e=%1$s&t=%2$s".format(word.englishWord, word.translation)
        val result: Promise[play.api.libs.ws.Response] = {
          WS.url(url).get
        }
        // ignore result
        //result.await(30000)
        //Ok(result.value.get.body)
        Redirect(routes.Application.index)
        /*Async {
          WS.url(url).get.map { response =>
            Ok(response.body)
          }
        } */
      })
  }

}