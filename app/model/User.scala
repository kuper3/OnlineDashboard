package model

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class User(
    username:String, 
    password:String, 
    email:String
)

object User {
  
  val userInsance = {
    get[String]("username") ~
      get[String]("password") ~ 
        get[String]("email") map {
        case username ~ password ~ email => User(username, password, email)
      }
  }
  
  def save(user: User):Boolean = {
    DB.withConnection{
      implicit c =>
        SQL("select 1" + user.username).execute
    }
  }
  
  def create(userName: String, password:String, email:String) {
    DB.withConnection { implicit c =>
      SQL("insert into users (username, password, email) values ({userName}, {password}, {email})").on(
        'userName -> userName, 'password -> password, 'email -> email).executeUpdate()
    }
  }
  
  def all(): List[User] = DB.withConnection { implicit c =>
    SQL("select * from users").as(userInsance *)
  }
}
