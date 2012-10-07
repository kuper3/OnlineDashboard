package model

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class User(
    val username:String, 
    val password:String
)

object User {
  
  val userInsance = {
    get[String]("username") ~
      get[String]("password") map {
        case username ~ password => User(username, password)
      }
  }
  
  def create(userName: String, password:String) {
    DB.withConnection("postgre") { implicit c =>
      SQL("insert into users (username, password) values ({userName}, {password})").on(
        'userName -> userName, 'password -> password).executeUpdate()
    }
  }  
  
  
  def all: List[User] = DB.withConnection("postgre") { implicit c =>
    SQL("select * from users").as(userInsance *)
  }
  
  def getUser(name: String, password: String) = {
    all.find(user => user.username == name && user.password == password)
  }
  
}
