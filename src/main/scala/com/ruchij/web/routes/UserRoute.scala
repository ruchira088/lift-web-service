package com.ruchij.web.routes

import net.liftweb.http.{JsonResponse, LiftRules}
import net.liftweb.http.rest.RestHelper

object UserRoute extends RestHelper
{
  def init() =
  {
    LiftRules.statelessDispatch.append(UserRoute)
  }

  serve {
    case "user" :: Nil JsonGet _ => ???
  }
}
