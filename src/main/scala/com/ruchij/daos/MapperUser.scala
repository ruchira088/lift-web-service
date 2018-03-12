package com.ruchij.daos

import net.liftweb.mapper._

class MapperUser extends LongKeyedMapper[MapperUser]
{
  self =>

  override def primaryKeyField: MappedField[Long, MapperUser] with IndexedField[Long] = id

  override def getSingleton: KeyedMetaMapper[Long, MapperUser] = MapperUser

  object id extends MappedLongIndex(self)
  object username extends MappedString(self, 100)
  object email extends MappedString(self, 100)
}

object MapperUser extends MapperUser with LongKeyedMetaMapper[MapperUser]
