package com.ruchij.web.routes

import com.ruchij.web.routes.web.responses.ServiceInformation
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{JsonResponse, LiftRules}

object IndexRoute extends RestHelper
{
  def init() =
  {
    LiftRules.statelessDispatch.append(IndexRoute)
  }

  serve {
    case "version" :: Nil JsonGet _ => JsonResponse(ServiceInformation())
  }
}
