package com.ruchij.web.responses

import net.liftweb.json.Extraction
import net.liftweb.json.JsonAST.JValue

import scala.util.Properties

case class ServiceInformationResponse(
   serviceName: String,
   sbtVersion: String,
   scalaVersion: String,
   javaVersion: String
)

object ServiceInformationResponse
{
  import com.eed3si9n.BuildInfo._
  import com.ruchij.utils.Serializers._

  def apply(): ServiceInformationResponse =
    ServiceInformationResponse(name, sbtVersion, scalaVersion, Properties.javaVersion)

  implicit def toJValue(serviceInformation: ServiceInformationResponse): JValue =
    Extraction.decompose(serviceInformation)
}

