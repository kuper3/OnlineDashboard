package controllers.signup

import model.User
import java.util.GregorianCalendar

object UserManager {
  
  val activeUsers = scala.collection.mutable.Map[User, java.util.Date]();
  
  val calendar = new java.util.GregorianCalendar;
  
  // 5 minute
  val cacheTimeout = 5 * 60 * 1000;
  
  def addUser(u: User) = {
    activeUsers(u) = calendar.getTime
  }
  
  def isLogged(u: User) = {
    activeUsers.get(u) match {
      case Some(usr) => true
      case None => false
    }
  }
  //FIXME use scheduler
  def remove(u: User) :Unit = {
    activeUsers -= u
  }

}