package model

case class Word(englishWord: String, translation: String) {
  override def toString = toJSON
  
  private def toJSON = "[englishWord = %1&s, translation = %2&s]".format(englishWord,translation)

}