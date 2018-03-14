package com.ruchij.daos

import net.liftweb.mapper._

class MapperUser extends LongKeyedMapper[MapperUser]
{
  self =>

  override def primaryKeyField: MappedField[Long, MapperUser] with IndexedField[Long] = liftId

  override def getSingleton: KeyedMetaMapper[Long, MapperUser] = MapperUser

  object liftId extends MappedLongIndex(self)
  object id extends MappedString(self, 100)
  object timeStamp extends MappedDateTime(self)
  object username extends MappedString(self, 100)
  object email extends MappedString(self, 100)
}

object MapperUser extends MapperUser with LongKeyedMetaMapper[MapperUser]
{
  def init(): List[String] =
    Schemifier.schemify(true, Schemifier.infoF _, MapperUser)
}
