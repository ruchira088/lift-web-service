package com.ruchij.web

import net.liftweb.http.{JsonResponse, LiftRules}
import net.liftweb.json.JsonDSL._
import net.liftweb.http.rest.RestHelper

object Routes extends RestHelper
{
  def init() =
    LiftRules.statelessDispatch.append(Routes)

  serve {
    case "version" :: Nil JsonGet _ => JsonResponse("serviceName" -> "LiftWeb service")
  }
}
