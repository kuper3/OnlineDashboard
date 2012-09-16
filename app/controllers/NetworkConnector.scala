package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.libs.ws.WS

object NetworkConnector extends Controller {

  def fetch = Action {
    val url = "http://localhost:8080/random"
    Async {
      WS.url(url).get().map { response =>
        Ok(response.body)
      }
    }
  }

}