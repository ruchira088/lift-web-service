package com.ruchij.web.routes

import com.ruchij.constants.ConfigValues
import com.ruchij.services.UserService
import com.ruchij.web.requests.UserRequest
import com.ruchij.web.responses.UserResponse
import net.liftweb.http.{JsonResponse, LiftRules}
import net.liftweb.http.rest.RestHelper

import scala.concurrent.{Await, ExecutionContext}

class UserRoute(userService: UserService)(implicit executionContext: ExecutionContext)
  extends RestHelper
{
  serve {
    case "user" :: Nil JsonPost UserRequest(request) -> _ =>
      Await.result(
        for {
          user <- userService.newUser(request)
          response = JsonResponse(UserResponse.fromUser(user))
        }
        yield response,
        ConfigValues.FUTURE_TIMEOUT
      )
  }
}

object UserRoute
{
  def init(userService: UserService)(implicit executionContext: ExecutionContext) =
  {
    LiftRules.statelessDispatch.append(new UserRoute(userService))
  }
}
