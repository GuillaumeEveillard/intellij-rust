/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.refactoring.inline

import com.intellij.lang.Language
import com.intellij.lang.refactoring.InlineActionHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import org.rust.lang.RsLanguage

class RsInlineActionHandler : InlineActionHandler() {
    override fun canInlineElement(element: PsiElement?): Boolean {
        return true
    }

    override fun isEnabledForLanguage(l: Language?): Boolean {
        return l is RsLanguage
    }

    override fun inlineElement(project: Project?, editor: Editor?, element: PsiElement?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
