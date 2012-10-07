package controllers

import model._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.ws._
import play.api.mvc._
import play.api.templates.Html
import views.html.defaultpages.unauthorized


object NetworkConnector extends Controller {
  
  private val host = "http://localhost:8080/"

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
    session.get("connected").map {user =>
    val url = host + "random"
    Async {
      WS.url(url).get().map { response =>
        Ok(views.html.main("Word", user)(Html(response.body)))
      }
    }
    }.getOrElse {
      Unauthorized("You are unauthorized!")
    }
  }

  def add = Action { implicit request =>
    session.get("connected").map {user =>
      Ok(views.html.network.modifyDictionary(wordForm, user))
    }.getOrElse {
      Unauthorized("You are unauthorized!")
    }
  }

  def submit = Action { implicit request =>
    wordForm.bindFromRequest.fold(
      hasErrors => BadRequest,
      word => {
        val url = host + "add?e=%1$s&t=%2$s".format(word.englishWord,word.translation)
        Async {
          WS.url(url).get.map { response =>
            Ok(response.body)
          }
        }        
      }
    )

  }

}