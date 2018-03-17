package com.ruchij.web.routes

import com.ruchij.web.requests.UserRequest
import com.ruchij.web.responses.UserResponse
import net.liftweb.http.{JsonResponse, LiftRules}
import net.liftweb.http.rest.RestHelper

object UserRoute extends RestHelper
{
  def init() =
  {
    LiftRules.statelessDispatch.append(UserRoute)
  }

  serve {
    case "user" :: Nil JsonPost UserRequest(request) -> _ => {

      println(request)
      JsonResponse(UserResponse("my-id", "username", "email"))
    }
  }
}
