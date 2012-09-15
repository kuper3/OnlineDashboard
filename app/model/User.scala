package model

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class User(
    var username:String, 
    password:String, 
    email:String,
    var isLoggedIn:Boolean
)

object User {
  
  val userInsance = {
    get[String]("username") ~
      get[String]("password") ~ 
        get[String]("email") map {
        case username ~ password ~ email => User(username, password, email, false)
      }
  }
  
  def create(userName: String, password:String, email:String) {
    DB.withConnection("postgre") { implicit c =>
      SQL("insert into users (username, password, email) values ({userName}, {password}, {email})").on(
        'userName -> userName, 'password -> password, 'email -> email).executeUpdate()
    }
  }
  
  def all: List[User] = DB.withConnection("postgre") { implicit c =>
    SQL("select * from users").as(userInsance *)
  }
  
  val current: User = User("","","", false)
  
}
