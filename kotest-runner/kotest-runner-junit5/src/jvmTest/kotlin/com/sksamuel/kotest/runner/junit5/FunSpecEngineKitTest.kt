package com.sksamuel.kotest.runner.junit5

import io.kotest.core.spec.style.FunSpec
import io.kotest.shouldBe
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.testkit.engine.EngineTestKit

class FunSpecEngineKitTest : FunSpec({

   test("verify engine stats") {
      EngineTestKit
         .engine("kotest")
         .selectors(selectClass(FunSpecSample::class.java))
         .configurationParameter("allow_internal", "true")
         .execute()
         .allEvents().apply {
            started().shouldHaveNames(
               "Kotest",
               "com.sksamuel.kotest.runner.junit5.FunSpecSample",
               "a failing test",
               "a passing test",
               "an erroring test"
            )
            aborted().shouldBeEmpty()
            skipped().shouldHaveNames("a skipped test")
            failed().shouldHaveNames(
               "a failing test",
               "an erroring test",
               "com.sksamuel.kotest.runner.junit5.FunSpecSample"
            )
            succeeded().shouldHaveNames("a passing test", "Kotest")
            finished().shouldHaveNames(
               "a failing test",
               "a passing test",
               "an erroring test",
               "com.sksamuel.kotest.runner.junit5.FunSpecSample",
               "Kotest"
            )
            dynamicallyRegistered().shouldHaveNames(
               "com.sksamuel.kotest.runner.junit5.FunSpecSample",
               "a failing test",
               "a passing test",
               "an erroring test",
               "a skipped test"
            )
         }
   }
})

internal class FunSpecSample : FunSpec({

   test("a failing test") {
      1 shouldBe 2
   }

   test("a passing test") {
      1 shouldBe 1
   }

   test("an erroring test") {
      throw RuntimeException()
   }

   test("a skipped test").config(enabled = false) {
   }

})
