package com.tresata.spray.spnego

import org.scalatest.FunSpec

class TokenSpec extends FunSpec {
  describe("Token") {
    val tokens = Tokens.apply()

    val token = tokens.create("HTTP/someserver.example.com@EXAMPLE.COM")

    it("should not be expired immediately"){
      assert(token.expired === false)
    }

    it("should rountrip serialization"){
      assert(token === tokens.parse(tokens.serialize(token)))
      assert(token.expired === false)
    }

    it("should throw an exception when parsing a tokenString with an incorrect signature"){
      intercept[TokenParseException] {
        tokens.parse(List(token.principal, token.expiration, tokens.sign(token) + "a").mkString("&"))
      }
    }

    it("should throw an exception when serializing an incomplete tokenString"){
      intercept[TokenParseException] {
        tokens.parse("a&1")
      }
    }

    it("should throw an exception when serializing an illegal tokenString"){
      intercept[TokenParseException] {
        tokens.parse("a&b&c")
      }
    }
  }
}
