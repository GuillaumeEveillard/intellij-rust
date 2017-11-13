/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.openapiext

import com.intellij.openapi.application.ApplicationManager
import org.jetbrains.annotations.TestOnly


/**
 * Testcrumbs allow easy navigation between code and tests.
 * That is, to find a relevant test for some piece of logic,
 * and to find the precise condition which is supposed to be
 * tested by a particular test. In a sense, testcrumbs are
 * a hand-made artisanal code-coverage tool.
 *
 * To use breadcrumb, first define a static [Testcrumb] value
 *
 * ```
 * val emptyFrobnicator = Testcrumb("Testcrumb")
 * ```
 *
 * Then, use [hit] function in the "production" code
 *
 * ```
 * if (myFrobnicator.isEmpty()) {
 *     emptyFrobnicator.hit()
 *     handleEmptyFrobnicator()
 * }
 * ```
 *
 * Finally, in the test case use [checkHit] function:
 *
 * ```
 * fun `test don't blow up on empty frobnicator`() = emptyFrobnicator.checkHit {
 *    arrange()
 *    act()
 *    assert()
 * }
 * ```
 *
 * You can `Cltr+B` on `emptyFrobnicator` to navigate
 * between code and tests. If after refactoring the
 * test fails to hit the breadcrumb, you'll be notified
 * that the test might not work as it used to.
 *
 * Note that if breadcrumb is used in "production", but
 * not in "test" code, it's useless, but we can't
 * provide a runtime assertion for this case.
 */
class Testcrumb(val name: String) {
    fun hit() = hitOrSkip(this)

    @TestOnly
    fun checkHit(f: () -> Unit) = withTestcrumbs(f, listOf(this))

    override fun toString(): String = name
}

private val hitOrSkip: (Testcrumb) -> Unit =
    if (ApplicationManager.getApplication().isUnitTestMode) ::hitTestcrumb else ({})

private fun withTestcrumbs(f: () -> Unit, crumb: List<Testcrumb>) {
    hits = HashSet()
    try {
        f()
        val hits = hits!!
        for (c in crumb) {
            check(c in hits) { "Testcrumb `$c` not hit" }
        }
    } finally {
        hits = null
    }
}


private var hits: MutableSet<Testcrumb>? = null

private fun hitTestcrumb(crumb: Testcrumb) {
    hits?.add(crumb)
}


