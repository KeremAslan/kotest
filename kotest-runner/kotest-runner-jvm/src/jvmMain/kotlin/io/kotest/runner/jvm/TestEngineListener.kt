package io.kotest.runner.jvm

import io.kotest.core.TestCase
import io.kotest.core.TestResult
import io.kotest.core.specs.Spec
import io.kotest.core.specs.SpecContainer

/**
 * Implementations of this interface will be notified of events
 * that occur as part of the [TestEngine] lifecycle.
 *
 * This is an internal listener liable to be changed.
 */
interface TestEngineListener {

   /**
    * Is invoked when the [TestEngine] is starting execution.
    *
    * @param containers the [Spec] classes that will be used by the [TestEngine].
    */
   fun engineStarted(containers: List<SpecContainer>) {}

   /**
    * Is invoked when the [TestEngine] has finished execution.
    *
    * If an unrecoverable error was detected during execution then it will be passed
    * as the parameter to the engine.
    */
   fun engineFinished(t: Throwable?) {}

   /**
    * Is invoked once per [Spec] when the [TestEngine] is preparing
    * to submit the spec for execution to a [SpecExecutor].
    */
   fun beginSpec(spec: Spec) {}

   /**
    * Is invoked once per [Spec] to indicate that all [TestCase] instances
    * of the spec have returned and the [SpecExecutor] has completed.
    */
   fun endSpec(spec: Spec, t: Throwable?) {}

   fun specExecutionError(containers: SpecContainer, t: Throwable) {}

   /**
    * Invoked each time a [TestCase] has been entered from a parent test.
    *
    * If a parent test has been configured with multiple invocations, then this
    * function will be executed once per parent invocation.
    */
   fun enterTestCase(testCase: TestCase) {}

   /**
    * Executed each time a [TestCase] has completed.
    *
    * This function will always be executed even if the test case is skipped.
    * The result passed in here will be after test case interception.
    *
    * If a parent test case has been configured with multiple invocations, then this
    * function will be executed once per parent invocation.
    */
   fun exitTestCase(testCase: TestCase, result: TestResult) {}

   /**
    * Invoked if a [TestCase] will be executed (is active).
    */
   fun beforeTestCaseExecution(testCase: TestCase) {}

   /**
    * Invoked each time a [TestCase] is about to be executed with a unique
    * ordinal for each execution.
    *
    * If a test case is configured with invocations = k, then this function
    * will be invoked k times.
    *
    * @param k indicates the execution number - if a test is configured with threads > 1, then it is
    * not guaranteed (and very unlikely) that k will be ordered.
    */
   fun invokingTestCase(testCase: TestCase, k: Int) {}

   /**
    * Invoked when all the invocations of a [TestCase] have completed.
    * This function will only be invoked if a test case was active.
    * The result passed in here is the result directly from the test run, before any interception.
    */
   fun afterTestCaseExecution(testCase: TestCase, result: TestResult) {}
}