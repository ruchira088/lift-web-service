package com.ruchij.web.routes

import com.ruchij.web.responses.ServiceInformationResponse
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{JsonResponse, LiftRules}

object IndexRoute extends RestHelper
{
  def init() =
  {
    LiftRules.statelessDispatch.append(IndexRoute)
  }

  serve {
    case "version" :: Nil JsonGet _ => JsonResponse(ServiceInformationResponse())
  }
}
