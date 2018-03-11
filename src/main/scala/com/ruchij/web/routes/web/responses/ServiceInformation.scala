package com.ruchij.web.routes.web.responses

import net.liftweb.json.Extraction
import net.liftweb.json.JsonAST.JValue

import scala.util.Properties

case class ServiceInformation(
   serviceName: String,
   sbtVersion: String,
   scalaVersion: String,
   javaVersion: String
)

object ServiceInformation
{
  import com.eed3si9n.BuildInfo._

  import com.ruchij.web.routes.web.responses.web.utils.Serializers._

  def apply(): ServiceInformation =
    ServiceInformation(name, sbtVersion, scalaVersion, Properties.javaVersion)

  implicit def toJson(serviceInformation: ServiceInformation): JValue =
    Extraction.decompose(serviceInformation)
}

