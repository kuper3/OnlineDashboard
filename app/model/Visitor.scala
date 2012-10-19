package model

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Visitor(name:String, host:String, user_agent: String) {
  override def toString = name + "," + host + "," + user_agent + ";"
}

object Visitor {
  val visitorInstance = {
    get[String]("name") ~
        get[String]("host") ~ 
          get[String]("user_agent") map {
        case name ~ host ~ user_agent => Visitor(name,host,user_agent)
      }
  }
  
  def create(name:String, host:String, user_agent: String) {
    DB.withConnection { implicit c =>
      SQL("insert into visitors (name, host, user_agent) values ({name}, {host}, {user_agent})").on(
        'name -> name, 'host -> host, 'user_agent -> user_agent).executeUpdate()
    }
  }
  
  def all: List[Visitor] = DB.withConnection { implicit c =>
    SQL("select * from visitors").as(visitorInstance *)
  }
}