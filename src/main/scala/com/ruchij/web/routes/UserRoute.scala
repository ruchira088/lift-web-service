package com.ruchij.web.routes

import com.ruchij.services.UserService
import com.ruchij.web.requests.UserRequest
import com.ruchij.web.responses.UserResponse
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{JsonResponse, LiftRules}

import scala.concurrent.ExecutionContext

class UserRoute(userService: UserService)(implicit executionContext: ExecutionContext)
  extends RestHelper
{
  serve {
    case "user" :: Nil JsonPost UserRequest(request) -> _ =>
      for {
        user <- userService.newUser(request)
        response = JsonResponse(UserResponse.fromUser(user))
      }
      yield response
  }
}

object UserRoute
{
  def init(userService: UserService)(implicit executionContext: ExecutionContext) =
  {
    LiftRules.statelessDispatch.append(new UserRoute(userService))
  }
}
