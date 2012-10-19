package controllers.signup

import model.User
import java.util.GregorianCalendar

object UserManager {

  def SESSION_ID: String = "connected"
  def UNAUTHORIZED_MSG: String = "You are unauthorized!"

  val activeUsers = scala.collection.mutable.Map[User, java.util.Date]();

  val calendar = new java.util.GregorianCalendar;

  // 1 minute
  val cacheTimeout = 1 * 10 * 1000;

  def addUser(u: User) = {
    activeUsers(u) = calendar.getTime

    val t = new TimerActor(1000, a, u)
    a.start
    t.start
  }

  def isLogged(u: User) = {
    activeUsers.get(u) match {
      case Some(usr) => true
      case None      => false
    }
  }
  //FIXME use scheduler
  def remove(u: User): Unit = {
    activeUsers -= u
  }

  import scala.actors._
  import scala.actors.Actor._
  class TimerActor(val timeout: Long, val who: Actor, val reply: User) extends Actor {
    def act {
      loop {
        reactWithin(timeout) {
          case TIMEOUT => who ! reply
        }
      }
    }
  }

  val a = actor {
    loop {
      react {
        case x: User =>  //println(x) /* remove user session */
      }
    }
  }
}