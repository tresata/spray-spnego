package com.tresata.spray.spnego

import org.scalatest.FunSpec

class TokenSpec extends FunSpec {
  describe("Token") {
    val token = Token("HTTP/someserver.example.com@EXAMPLE.COM")

    it("should not be expired immediately"){
      assert(token.expired === false)
    }

    it("should rountrip serialization"){
      assert(token === Token.parse(token.serialize))
      assert(token.expired === false)
    }

    it("should throw an exception when parsing a tokenString with an incorrect signature"){
      intercept[TokenParseException] {
        Token.parse(List(token.principal, token.expiration, token.signature + "a").mkString("&"))
      }
    }

    it("should throw an exception when serializing an incomplete tokenString"){
      intercept[TokenParseException] {
        Token.parse("a&1")
      }
    }

    it("should throw an exception when serializing an illegal tokenString"){
      intercept[TokenParseException] {
        Token.parse("a&b&c")
      }
    }
  }
}
